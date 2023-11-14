package xyz.felh.openai;

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
import retrofit2.http.*;
import xyz.felh.openai.assistant.*;
import xyz.felh.openai.assistant.file.AssistantFile;
import xyz.felh.openai.assistant.file.CreateAssistantFileRequest;
import xyz.felh.openai.audio.AudioResponse;
import xyz.felh.openai.audio.CreateAudioTranscriptionRequest;
import xyz.felh.openai.audio.CreateAudioTranslationRequest;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.completion.Completion;
import xyz.felh.openai.completion.CreateCompletionRequest;
import xyz.felh.openai.embedding.CreateEmbeddingRequest;
import xyz.felh.openai.embedding.CreateEmbeddingResponse;
import xyz.felh.openai.fineTuning.CreateFineTuningJobRequest;
import xyz.felh.openai.fineTuning.FineTuningJob;
import xyz.felh.openai.fineTuning.FineTuningJobEvent;
import xyz.felh.openai.image.CreateImageRequest;
import xyz.felh.openai.image.ImageResponse;
import xyz.felh.openai.image.edit.CreateEditRequest;
import xyz.felh.openai.image.variation.CreateVariationRequest;
import xyz.felh.openai.interceptor.AuthenticationInterceptor;
import xyz.felh.openai.model.Model;
import xyz.felh.openai.moderation.CreateModerationRequest;
import xyz.felh.openai.moderation.CreateModerationResponse;
import xyz.felh.openai.thread.CreateThreadRequest;
import xyz.felh.openai.thread.ModifyThreadRequest;
import xyz.felh.openai.thread.Thread;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static xyz.felh.openai.constant.OpenAiConstants.BASE_URL;


/**
 * OpenAi Service Class
 */
@Slf4j
public class OpenAiService {

    private static final Duration DEFAULT_TIMEOUT = Duration.ofSeconds(30);
    private static final ObjectMapper errorMapper = defaultObjectMapper();

    private final OpenAiApi api;

    private final OkHttpClient client;

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
        this(buildApi(token, timeout), defaultClient(token, timeout));
    }

    /**
     * Creates a new OpenAiService that wraps OpenAiApi.
     * Use this if you need more customization.
     *
     * @param api OpenAiApi instance to use for all methods
     */
    public OpenAiService(final OpenAiApi api, final OkHttpClient client) {
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
        return defaultClient(token, null, timeout);
    }

    public static OkHttpClient defaultClient(String token, String orgId, Duration timeout) {
        return new OkHttpClient.Builder()
                .addInterceptor(new AuthenticationInterceptor(token, orgId))
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
     * @param request create completion request
     * @return completion
     */
    public Completion createCompletion(CreateCompletionRequest request) {
        return execute(api.createCompletion(request));
    }

    /**
     * gpt-4, gpt-4-0314, gpt-4-32k, gpt-4-32k-0314, gpt-3.5-turbo, gpt-3.5-turbo-0301
     * gpt-3.5-turbo-1006, gpt-4-1106-preview
     *
     * @param request create chat completion request
     * @return chat completion
     */
    public ChatCompletion createChatCompletion(CreateChatCompletionRequest request) {
        request.setStream(false);
        return execute(api.createChatCompletion(request));
    }

    /**
     * create chat completion by stream
     *
     * @param requestId request ID, every observer is unique
     * @param request   detail of request
     * @param listener  StreamChatCompletionListener
     */
    public void createSteamChatCompletion(String requestId,
                                          CreateChatCompletionRequest request,
                                          @NonNull StreamChatCompletionListener listener) {
        request.setStream(true);
        Request okHttpRequest;
        try {
            okHttpRequest = new Request.Builder().url(BASE_URL + "/v1/chat/completions")
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
                if (data.equals("[DONE]")) {
                    listener.onEventDone(requestId);
                } else {
                    try {
                        ChatCompletion chatCompletion = defaultObjectMapper().readValue(data, ChatCompletion.class);
                        listener.onEvent(requestId, chatCompletion);
                    } catch (JsonProcessingException e) {
                        throw new RuntimeException(e);
                    }
                }
            }

            @Override
            public void onClosed(@NonNull EventSource eventSource) {
                listener.onClosed(requestId);
            }

            @Override
            public void onFailure(@NonNull EventSource eventSource, @Nullable Throwable t, @Nullable Response response) {
                listener.onFailure(requestId, t, response);
            }
        };
        EventSource eventSource = factory.newEventSource(okHttpRequest, eventSourceListener);
        listener.setEventSource(eventSource);
    }

    public ImageResponse createImage(CreateImageRequest request) {
        return execute(api.createImage(request));
    }

    public ImageResponse createImageEdit(CreateEditRequest request) {
        byte[] imageBytes;
        if (request.getImage() != null && request.getImage().length > 0) {
            imageBytes = request.getImage();
        } else {
            File image = new File(request.getImagePath());
            try {
                imageBytes = Files.readAllBytes(image.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        byte[] maskBytes = null;
        if (request.getMask() != null && request.getMask().length > 0) {
            maskBytes = request.getMask();
        } else if (request.getMaskPath() != null && request.getMaskPath().length() > 0) {
            File mask = new File(request.getMaskPath());
            try {
                maskBytes = Files.readAllBytes(mask.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return createImageEdit(request, imageBytes, maskBytes);
    }

    private ImageResponse createImageEdit(CreateEditRequest request, byte[] image, byte[] mask) {
        RequestBody imageBody = RequestBody.create(image, MediaType.parse("image"));
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("prompt", request.getPrompt())
                .addFormDataPart("image", "image", imageBody);
        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }
        if (request.getSize() != null) {
            builder.addFormDataPart("size", request.getSize().value());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat().value());
        }
        if (mask != null) {
            RequestBody maskBody = RequestBody.create(mask, MediaType.parse("image"));
            builder.addFormDataPart("mask", "mask", maskBody);
        }
        return execute(api.createImageEdit(builder.build()));
    }

    public ImageResponse createImageVariation(CreateVariationRequest request) {
        RequestBody imageBody;
        if (request.getImage() != null && request.getImage().length > 0) {
            imageBody = RequestBody.create(request.getImage(), MediaType.parse("image"));
        } else {
            File mask = new File(request.getImagePath());
            try {
                imageBody = RequestBody.create(Files.readAllBytes(mask.toPath()), MediaType.parse("image"));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        MultipartBody.Builder builder = new MultipartBody.Builder()
                .setType(MediaType.get("multipart/form-data"))
                .addFormDataPart("image", "image", imageBody);

        if (request.getN() != null) {
            builder.addFormDataPart("n", request.getN().toString());
        }
        if (request.getSize() != null) {
            builder.addFormDataPart("size", request.getSize().value());
        }
        if (request.getResponseFormat() != null) {
            builder.addFormDataPart("response_format", request.getResponseFormat().value());
        }
        return execute(api.createImageVariation(builder.build()));
    }

    /**
     * text-embedding-ada-002, text-search-ada-doc-001
     *
     * @param request create Embedding request
     * @return create embedding response
     */
    public CreateEmbeddingResponse createEmbeddings(CreateEmbeddingRequest request) {
        return execute(api.createEmbedding(request));
    }

    /**
     * whisper-1
     * <p>
     * TODO only support mp3 so far
     *
     * @param request create audio transcription request
     * @return audio response
     */
    public AudioResponse createAudioTranscription(CreateAudioTranscriptionRequest request) {
        byte[] fileBytes;
        if (request.getFile() != null && request.getFile().length > 0) {
            fileBytes = request.getFile();
        } else {
            File file = new File(request.getFilePath());
            try {
                fileBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        RequestBody audioBody = RequestBody.create(fileBytes, MediaType.parse("mp3"));
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
     * @param request create audio traslation request
     * @return audio
     */
    public AudioResponse createAudioTranslation(CreateAudioTranslationRequest request) {
        byte[] fileBytes;
        if (request.getFile() != null && request.getFile().length > 0) {
            fileBytes = request.getFile();
        } else {
            File file = new File(request.getFilePath());
            try {
                fileBytes = Files.readAllBytes(file.toPath());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        RequestBody audioBody = RequestBody.create(fileBytes, MediaType.parse("mp3"));
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

    public List<xyz.felh.openai.file.File> listFiles() {
        return execute(api.listFiles()).getData();
    }

    public xyz.felh.openai.file.File uploadFile(File file, String purpose) {
        RequestBody purposeBody = RequestBody.create(purpose, okhttp3.MultipartBody.FORM);
        RequestBody fileBody = RequestBody.create(file, MediaType.parse("text"));
        MultipartBody.Part body = MultipartBody.Part.createFormData("file", file.getName(), fileBody);
        return execute(api.uploadFile(body, purposeBody));
    }

    public xyz.felh.openai.file.File uploadFile(String filepath, String purpose) {
        File file = new File(filepath);
        return uploadFile(file, purpose);
    }

    public DeleteResponse deleteFile(String fileId) {
        return execute(api.deleteFile(fileId));
    }

    public xyz.felh.openai.file.File retrieveFile(String fileId) {
        return execute(api.retrieveFile(fileId));
    }

    /**
     * @param fileId file id
     * @return file content
     */
    public String retrieveFileContent(String fileId) {
        return execute(api.retrieveFileContent(fileId));
    }

    /**
     * text-moderation-stable, text-moderation-latest
     *
     * @param request create moderation request
     * @return moderation response
     */
    public CreateModerationResponse createModeration(CreateModerationRequest request) {
        return execute(api.createModeration(request));
    }

    /**
     * Creates a job that fine-tunes a specified model from a given dataset.
     *
     * @param request CreateFineTuningJobRequest
     * @return A fine-tuning.job object.
     */
    public FineTuningJob createFineTuningJob(CreateFineTuningJobRequest request) {
        return execute(api.createFineTuningJob(request));
    }

    /**
     * Get info about a fine-tuning job.
     *
     * @param fineTuningJobId fineTuningJobId
     * @return The fine-tuning object with the given ID.
     */
    public FineTuningJob retrieveFineTuningJob(String fineTuningJobId) {
        return execute(api.retrieveFineTuningJob(fineTuningJobId));
    }

    /**
     * Immediately cancel a fine-tune job.
     *
     * @param fineTuningJobId fineTuningJobId
     * @return The cancelled fine-tuning object.
     */
    public FineTuningJob cancelFineTuningJob(String fineTuningJobId) {
        return execute(api.cancelFineTuningJob(fineTuningJobId));
    }

    /**
     * Get status updates for a fine-tuning job.
     *
     * @param fineTuningJobId fineTuningJobId
     * @param after           Identifier for the last event from the previous pagination request.
     * @param limit           Number of events to retrieve.
     * @return A list of fine-tuning event objects.
     */
    public List<FineTuningJobEvent> listFineTuningEvents(String fineTuningJobId,
                                                         String after,
                                                         Integer limit) {
        return execute(api.listFineTuningEvents(fineTuningJobId, after, limit)).getData();
    }

    /***************** Assistant BETA ****************/

    /**
     * {@literal POST https://api.openai.com/v1/assistants}
     * <p>
     * Create an assistant with a model and instructions.
     *
     * @param request Request body
     * @return An {@link Assistant} object.
     */
    public Assistant createAssistant(CreateAssistantRequest request) {
        return execute(api.createAssistant(request));
    }

    /**
     * Retrieves an assistant.
     *
     * @param assistantId The ID of the assistant to retrieve.
     * @return The {@link Assistant} object matching the specified ID.
     */
    public Assistant retrieveAssistant(String assistantId) {
        return execute(api.retrieveAssistant(assistantId));
    }

    /**
     * {@literal POST https://api.openai.com/v1/assistants/{assistant_id}}
     * <p>
     * Modifies an assistant.
     *
     * @param assistantId The ID of the assistant to modify.
     * @param request     Request body
     * @return The modified {@link Assistant} object.
     */
    public Assistant modifyAssistant(String assistantId, ModifyAssistantRequest request) {
        return execute(api.modifyAssistant(assistantId, request));
    }

    /**
     * {@literal DELETE https://api.openai.com/v1/assistants/{assistant_id}}
     * <p>
     * Delete an assistant.
     *
     * @param assistantId The ID of the assistant to delete.
     * @return Deletion status
     */
    public DeleteResponse deleteAssistant(String assistantId) {
        return execute(api.deleteAssistant(assistantId));
    }

    /**
     * {@literal  GET https://api.openai.com/v1/assistants}
     * <p>
     * Returns a list of assistants.
     *
     * @param order  Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after  A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @param limit  A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @return A list of {@link Assistant} objects.
     */
    public OpenAiApiListResponse<Assistant> listAssistants(Integer limit, String order, String after, String before) {
        return execute(api.listAssistants(limit, order, after, before));
    }

    public OpenAiApiListResponse<Assistant> listAssistants() {
        return listAssistants(null, null, null, null);
    }


    // Assistant Files

    /**
     * {@literal POST https://api.openai.com/v1/assistants/{assistant_id}/files}
     * <p>
     * Create assistant file
     *
     * @param assistantId The ID of the assistant for which to create a File.
     * @param request     Request body
     * @return An {@link AssistantFile} object.
     */
    public AssistantFile createAssistantFile(String assistantId, CreateAssistantFileRequest request) {
        return execute(api.createAssistantFile(assistantId, request));
    }

    /**
     * {@literal GET https://api.openai.com/v1/assistants/{assistant_id}/files/{file_id}}
     * <p>
     * Retrieve assistant file
     *
     * @param assistantId The ID of the assistant who the file belongs to.
     * @param fileId      The ID of the file we're getting.
     * @return The {@link AssistantFile} object matching the specified ID.
     */
    public AssistantFile retrieveAssistantFile(String assistantId, String fileId) {
        return execute(api.retrieveAssistantFile(assistantId, fileId));
    }

    /**
     * {@literal DELETE https://api.openai.com/v1/assistants/{assistant_id}/files/{file_id}}
     * <p>
     * Delete an assistant file.
     *
     * @param assistantId The ID of the assistant who the file belongs to.
     * @param fileId      The ID of the file to delete.
     * @return Deletion status
     */
    public DeleteResponse deleteAssistantFile(String assistantId, String fileId) {
        return execute(api.deleteAssistantFile(assistantId, fileId));
    }

    /**
     * {@literal  GET https://api.openai.com/v1/assistants/{assistant_id}/files}
     * <p>
     * Returns a list of assistant files.
     *
     * @param assistantId The ID of the assistant the file belongs to.
     * @param order       Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after       A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before      A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @param limit       A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @return A list of {@link AssistantFile} objects.
     */
    public OpenAiApiListResponse<AssistantFile> listAssistantFiles(String assistantId,
                                                                   Integer limit,
                                                                   String order,
                                                                   String after,
                                                                   String before) {
        return execute(api.listAssistantFiles(assistantId, limit, order, after, before));
    }

    public OpenAiApiListResponse<AssistantFile> listAssistantFiles(String assistantId) {
        return listAssistantFiles(assistantId, null, null, null, null);
    }

    /**
     * {@literal POST https://api.openai.com/v1/threads}
     * <p>
     * Create thread
     *
     * @param request Request body
     * @return An {@link Thread} object.
     */
    public Thread createThread(CreateThreadRequest request) {
        return execute(api.createThread(request));
    }

    /**
     * {@literal GET https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Retrieve thread
     *
     * @param threadId The ID of the thread to retrieve.
     * @return The {@link Thread} object matching the specified ID.
     */
    public Thread retrieveThread(String threadId) {
        return execute(api.retrieveThread(threadId));
    }

    /**
     * {@literal GET https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Modify thread
     *
     * @param threadId The ID of the thread to modify. Only the metadata can be modified.
     * @param request  Request body
     * @return The modified {@link java.lang.Thread} object matching the specified ID.
     */
    public Thread modifyThread(String threadId, ModifyThreadRequest request) {
        return execute(api.modifyThread(threadId, request));
    }

    /**
     * {@literal DELETE https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Delete thread
     *
     * @param threadId The ID of the thread to delete.
     * @return Deletion status
     */
    public DeleteResponse deleteThread(String threadId) {
        return execute(api.deleteThread(threadId));
    }

}
