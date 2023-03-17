package com.felh.openai;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.felh.openai.audio.AudioResponse;
import com.felh.openai.audio.CreateAudioTranscriptionRequest;
import com.felh.openai.audio.CreateAudioTranslationRequest;
import com.felh.openai.completion.Completion;
import com.felh.openai.completion.CreateCompletionRequest;
import com.felh.openai.completion.chat.ChatCompletion;
import com.felh.openai.completion.chat.CreateChatCompletionRequest;
import com.felh.openai.edit.Edit;
import com.felh.openai.edit.CreateEditRequest;
import com.felh.openai.embedding.CreateEmbeddingRequest;
import com.felh.openai.embedding.CreateEmbeddingResponse;
import com.felh.openai.finetune.CreateFineTuneRequest;
import com.felh.openai.finetune.FineTune;
import com.felh.openai.finetune.FineTuneEvent;
import com.felh.openai.image.CreateImageRequest;
import com.felh.openai.image.ImageResponse;
import com.felh.openai.image.edit.CreateImageEditRequest;
import com.felh.openai.image.variation.CreateImageVariationRequest;
import com.felh.openai.model.Model;
import com.felh.openai.moderation.CreateModerationRequest;
import com.felh.openai.moderation.CreateModerationResponse;
import io.reactivex.rxjava3.core.Single;
import okhttp3.*;
import retrofit2.HttpException;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory;
import retrofit2.converter.jackson.JacksonConverterFactory;

import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * OpenAi Service Class
 */
public class OpenAiService {

    private static final String BASE_URL = "https://api.openai.com";
    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final ObjectMapper errorMapper = defaultObjectMapper();

    private final OpenAiApi api;

    /**
     * Creates a new OpenAiService that wraps OpenAiApi
     *
     * @param token OpenAi token string "sk-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
     */
    public OpenAiService(final String token) {
        this(token, DEFAULT_TIMEOUT);
    }

    /**
     * Creates a new OpenAiService that wraps OpenAiApi
     *
     * @param token   OpenAi token string "sk-XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXX"
     * @param timeout http read timeout, Duration.ZERO means no timeout
     */
    public OpenAiService(final String token, final Duration timeout) {
        this(buildApi(token, timeout));
    }

    /**
     * Creates a new OpenAiService that wraps OpenAiApi.
     * Use this if you need more customization.
     *
     * @param api OpenAiApi instance to use for all methods
     */
    public OpenAiService(final OpenAiApi api) {
        this.api = api;
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

                OpenAiError error = errorMapper.readValue(errorBody, OpenAiError.class);
                throw new OpenAiHttpException(error, e, e.code());
            } catch (IOException ex) {
                // couldn't parse OpenAI error
                throw e;
            }
        }
    }

    public static OpenAiApi buildApi(String token, Duration timeout) {
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(token, timeout);
        Retrofit retrofit = defaultRetrofit(client, mapper);
        return retrofit.create(OpenAiApi.class);
    }

    public static ObjectMapper defaultObjectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        mapper.setPropertyNamingStrategy(PropertyNamingStrategies.SNAKE_CASE);
        return mapper;
    }

    public static OkHttpClient defaultClient(String token, Duration timeout) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token))
                .connectionPool(new ConnectionPool(5, 1, TimeUnit.SECONDS))
                .readTimeout(timeout.toMillis(), TimeUnit.MILLISECONDS)
                .build();
    }

    public static Retrofit defaultRetrofit(OkHttpClient client, ObjectMapper mapper) {
        return new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .client(client)
                .addConverterFactory(JacksonConverterFactory.create(mapper))
                .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
                .build();
    }

    public List<Model> listModels() {
        return execute(api.listModels()).getData();
    }

    public Model getModel(String modelId) {
        return execute(api.getModel(modelId));
    }

    /**
     * text-davinci-003, text-davinci-002, text-curie-001, text-babbage-001,
     * text-ada-001, davinci, curie, babbage, ada
     *
     * @param request
     * @return
     */
    public Completion createCompletion(CreateCompletionRequest request) {
        return execute(api.createCompletion(request));
    }

    /**
     * gpt-4, gpt-4-0314, gpt-4-32k, gpt-4-32k-0314, gpt-3.5-turbo, gpt-3.5-turbo-0301
     *
     * @param request
     * @return
     */
    public ChatCompletion createChatCompletion(CreateChatCompletionRequest request) {
        return execute(api.createChatCompletion(request));
    }

    /**
     * text-davinci-edit-001, code-davinci-edit-001
     *
     * @param request
     * @return
     */
    public Edit createEdit(CreateEditRequest request) {
        return execute(api.createEdit(request));
    }

    public ImageResponse createImage(CreateImageRequest request) {
        return execute(api.createImage(request));
    }

    public ImageResponse createImageEdit(CreateImageEditRequest request) {
        File image = new File(request.getImage());
        File mask = null;
        if (request.getMask() != null) {
            mask = new File(request.getMask());
        }
        return createImageEdit(request, image, mask);
    }

    private ImageResponse createImageEdit(CreateImageEditRequest request, File image, File mask) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("image"), image);
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("prompt", request.getPrompt())
                .addFormDataPart("image", "image", imageBody);
        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }
        if (request.getSize() != null) {
            builder.addFormDataPart("size", request.getSize());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat());
        }
        if (mask != null) {
            RequestBody maskBody = RequestBody.create(MediaType.parse("image"), mask);
            builder.addFormDataPart("mask", "mask", maskBody);
        }
        return execute(api.createImageEdit(builder.build()));
    }

    public ImageResponse createImageVariation(CreateImageVariationRequest request) {
        RequestBody imageBody = RequestBody.create(MediaType.parse("image"), new File(request.getImage()));
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("image", "image", imageBody);

        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }
        if (request.getSize() != null) {
            builder.addFormDataPart("size", request.getSize());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat());
        }
        return execute(api.createImageVariation(builder.build()));
    }

    /**
     * text-embedding-ada-002, text-search-ada-doc-001
     *
     * @param request
     * @return
     */
    public CreateEmbeddingResponse createEmbeddings(CreateEmbeddingRequest request) {
        return execute(api.createEmbedding(request));
    }

    /**
     * whisper-1
     * <p>
     * TODO only support mp3 so far
     *
     * @param request
     * @return
     */
    public AudioResponse createAudioTranscription(CreateAudioTranscriptionRequest request) {
        RequestBody audioBody = RequestBody.create(MediaType.parse("mp3"), new File(request.getFile()));
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("model", request.getModel())
                .addFormDataPart("file", "mp3", audioBody);
        if (request.getPrompt() != null) {
            builder.addFormDataPart("prompt", request.getModel());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat());
        }
        if (request.getTemperature() != null) {
            builder.addFormDataPart("temperature", request.getTemperature().toString());
        }
        if (request.getPrompt() != null) {
            builder.addFormDataPart("language", request.getLanguage());
        }
        return execute(api.createAudioTranscription(builder.build()));
    }

    /**
     * whisper-1
     * <p>
     * TODO only support mp3 so far
     *
     * @param request
     * @return
     */
    public AudioResponse createAudioTranslation(CreateAudioTranslationRequest request) {
        RequestBody audioBody = RequestBody.create(MediaType.parse("mp3"), new File(request.getFile()));
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("model", request.getModel())
                .addFormDataPart("file", "mp3", audioBody);
        if (request.getPrompt() != null) {
            builder.addFormDataPart("prompt", request.getModel());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat());
        }
        if (request.getTemperature() != null) {
            builder.addFormDataPart("temperature", request.getTemperature().toString());
        }
        return execute(api.createAudioTranslation(builder.build()));
    }

    public List<com.felh.openai.file.File> listFiles() {
        return execute(api.listFiles()).getData();
    }

    public com.felh.openai.file.File uploadFile(String filepath, String purpose) {
        File file = new File(filepath);
        RequestBody purposeBody = RequestBody.create(okhttp3.MultipartBody.FORM, purpose);
        RequestBody fileBody = RequestBody.create(MediaType.parse("text"), file);
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", filepath, fileBody);
        return execute(api.uploadFile(body, purposeBody));
    }

    public DeleteResponse deleteFile(String fileId) {
        return execute(api.deleteFile(fileId));
    }

    public com.felh.openai.file.File retrieveFile(String fileId) {
        return execute(api.retrieveFile(fileId));
    }

    /**
     * TODO This is not for free accounts
     *
     * @param fileId
     * @return
     */
    public String retrieveFileContent(String fileId) {
        return execute(api.retrieveFileContent(fileId));
    }

    public FineTune createFineTune(CreateFineTuneRequest request) {
        return execute(api.createFineTune(request));
    }

    public List<FineTune> listFineTunes() {
        return execute(api.listFineTunes()).getData();
    }

    public FineTune retrieveFineTune(String fineTuneId) {
        return execute(api.retrieveFineTune(fineTuneId));
    }

    public FineTune cancelFineTune(String fineTuneId) {
        return execute(api.cancelFineTune(fineTuneId));
    }

    public List<FineTuneEvent> listFineTuneEvents(String fineTuneId) {
        return execute(api.listFineTuneEvents(fineTuneId)).getData();
    }

    public DeleteResponse deleteFineTune(String model, String fineTuneId) {
        return execute(api.deleteFineTune(String.format("%s:%s", model, fineTuneId)));
    }

    /**
     * text-moderation-stable, text-moderation-latest
     *
     * @param request
     * @return
     */
    public CreateModerationResponse createModeration(CreateModerationRequest request) {
        return execute(api.createModeration(request));
    }

}
