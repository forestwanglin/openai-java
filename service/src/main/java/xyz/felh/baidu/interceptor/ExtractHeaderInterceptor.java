package xyz.felh.baidu.interceptor;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Interceptor;
import okhttp3.Response;
import xyz.felh.baidu.bean.ResponseHeaders;
import xyz.felh.utils.Preconditions;

import java.io.IOException;
import java.util.Objects;
import java.util.function.Consumer;

import static xyz.felh.baidu.constant.BaiduAiConstants.*;
import static xyz.felh.openai.constant.OpenAiConstants.HEADER_X_RATELIMIT_REMAINING_REQUESTS;


/**
 * interceptor to get headers from response
 */
@Slf4j
public class ExtractHeaderInterceptor implements Interceptor {

    private final Consumer<ResponseHeaders> headersConsumer;

    public ExtractHeaderInterceptor(Consumer<ResponseHeaders> headersConsumer) {
        this.headersConsumer = headersConsumer;
    }

    @NonNull
    @Override
    public Response intercept(@NonNull Interceptor.Chain chain) throws IOException {
        Response response = chain.proceed(chain.request());
        if (Preconditions.isNotBlank(headersConsumer)) {
            ResponseHeaders.ResponseHeadersBuilder builder = ResponseHeaders.builder();
            builder.appId(response.headers().get(HEADER_X_APP_ID));
            if (Preconditions.isNotBlank(response.headers().get(HEADER_X_RATELIMIT_LIMIT_REQUESTS))) {
                builder.limitRequests(Integer.parseInt(Objects.requireNonNull(response.headers().get(HEADER_X_RATELIMIT_LIMIT_REQUESTS))));
            }
            if (Preconditions.isNotBlank(response.headers().get(HEADER_X_RATELIMIT_LIMIT_TOKENS))) {
                builder.limitTokens(Integer.parseInt(Objects.requireNonNull(response.headers().get(HEADER_X_RATELIMIT_LIMIT_TOKENS))));
            }
            if (Preconditions.isNotBlank(response.headers().get(HEADER_X_RATELIMIT_REMAINING_REQUESTS))) {
                builder.remainingRequests(Integer.parseInt(Objects.requireNonNull(response.headers().get(HEADER_X_RATELIMIT_REMAINING_REQUESTS))));
            }
            if (Preconditions.isNotBlank(response.headers().get(HEADER_X_RATELIMIT_REMAINING_TOKENS))) {
                builder.remainingTokens(Integer.parseInt(Objects.requireNonNull(response.headers().get(HEADER_X_RATELIMIT_REMAINING_TOKENS))));
            }
            headersConsumer.accept(builder.build());
        }
        return response;
    }

}
