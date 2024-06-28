package xyz.felh.baidu;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import io.reactivex.rxjava3.core.Single;
import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.*;
import okhttp3.sse.EventSource;
import okhttp3.sse.EventSourceListener;
import okhttp3.sse.EventSources;
import org.jetbrains.annotations.Nullable;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;
import retrofit2.converter.scalars.ScalarsConverterFactory;
import xyz.felh.StreamListener;
import xyz.felh.baidu.chat.ChatCompletion;
import xyz.felh.baidu.chat.ModelType;
import xyz.felh.baidu.chat.CreateChatCompletionRequest;
import xyz.felh.baidu.constant.BaiduAiConstants;
import xyz.felh.baidu.interceptor.AuthenticationInterceptor;

import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.TimeUnit;

import static xyz.felh.baidu.constant.BaiduAiConstants.BASE_URL;

@Slf4j
public class BaiduAiService {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final ObjectMapper errorMapper = defaultObjectMapper();
    private final BaiduAiApi api;
    private final OkHttpClient client;

    /**
     * Creates a new BaiduQianfanService that wraps BaiduQianfanApi
     */
    public BaiduAiService(String ak, String sk) {
        this(ak, sk, DEFAULT_TIMEOUT);
    }

    /**
     * Creates a new OpenAiService that wraps OpenAiApi
     *
     * @param timeout http read timeout, Duration.ZERO means no timeout
     */
    public BaiduAiService(String ak, String sk, final Duration timeout) {
        this(buildApi(ak, sk, timeout), defaultClient(ak, sk, timeout));
    }

    /**
     * Creates a new OpenAiService that wraps OpenAiApi.
     * Use this if you need more customization.
     *
     * @param api OpenAiApi instance to use for all methods
     */
    public BaiduAiService(final BaiduAiApi api, final OkHttpClient client) {
        this.api = api;
        this.client = client;
    }

    /**
     * Calls the Open AI api, returns the response, and parses error messages if the request fails
     */
    public static <T> T execute(Single<T> apiCall) {
        try {
            return apiCall.blockingGet();
        } catch (HttpException e) {
            try {
                if (e.response() == null || e.response().errorBody() == null) {
                    throw e;
                }
                String errorBody = e.response().errorBody().string();

                BaiduError error = errorMapper.readValue(errorBody, BaiduError.class);
                throw new BaiduHttpException(error, e);
            } catch (IOException ex) {
                // couldn't parse OpenAI error
                throw e;
            }
        }
    }

    public static BaiduAiApi buildApi(String ak, String sk, Duration timeout) {
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(ak, sk, timeout);
        Retrofit retrofit = defaultRetrofit(client, mapper);
        return retrofit.create(BaiduAiApi.class);
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    public static OkHttpClient defaultClient(String ak, String sk, Duration timeout) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(ak, sk))
                .connectionPool(new ConnectionPool(10, 4, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(ScalarsConverterFactory.create())
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public ChatCompletion createChat(ModelType model, CreateChatCompletionRequest request) {
        return execute(api.createChat(model.getPath(), request));
    }

    public void createStreamChat(String requestId,
                                 ModelType model,
                                 CreateChatCompletionRequest request,
                                 @NonNull StreamListener<ChatCompletion> listener) {
        request.setStream(true);
        Request okHttpRequest;
        try {
            okHttpRequest = new Request.Builder()
                    .url(BaiduAiConstants.BASE_URL + "/rpc/2.0/ai_custom/v1/wenxinworkshop/chat/" + model.getPath())
                    .header("content-type", "text/event-stream")
                    .header("Accept", "text/event-stream")
                    .post(RequestBody.create(defaultObjectMapper().writeValueAsString(request),
                            MediaType.parse("application/json")))
                    .build();
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
        EventSource.Factory factory = EventSources.createFactory(client);
        EventSourceListener eventSourceListener = new EventSourceListener() {
            @Override
            public void onOpen(@NonNull EventSource eventSource, @NonNull Response response) {
                listener.onOpen(requestId, response);
            }

            @Override
            public void onEvent(@NonNull EventSource eventSource, @Nullable String id, @Nullable String type, @NonNull String data) {
                try {
                    ChatCompletion chatCompletion = defaultObjectMapper().readValue(data, ChatCompletion.class);
                    listener.onEvent(requestId, chatCompletion);
                    if (chatCompletion.getIsEnd()) {
                        listener.onEventDone(requestId);
                    }
                } catch (JsonProcessingException e) {
                    throw new RuntimeException(e);
                }
            }

            @Override
            public void onClosed(@NonNull EventSource eventSource) {
                listener.onClosed(requestId);
            }

            @Override
            public void onFailure(@NonNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                log.error("streamChat error {}", response, t);
                listener.onFailure(requestId, t, response);
            }
        };
        EventSource eventSource = factory.newEventSource(okHttpRequest, eventSourceListener);
        listener.setEventSource(eventSource);
    }

}
