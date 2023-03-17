package com.felh.openai;

import com.felh.openai.audio.AudioResponse;
import com.felh.openai.completion.Completion;
import com.felh.openai.completion.CreateCompletionRequest;
import com.felh.openai.completion.chat.ChatCompletion;
import com.felh.openai.completion.chat.CreateChatCompletionRequest;
import com.felh.openai.edit.Edit;
import com.felh.openai.edit.CreateEditRequest;
import com.felh.openai.embedding.CreateEmbeddingRequest;
import com.felh.openai.embedding.CreateEmbeddingResponse;
import com.felh.openai.image.CreateImageRequest;
import com.felh.openai.image.CreateImageResponse;
import com.felh.openai.image.edit.CreateImageEditRequest;
import com.felh.openai.image.variation.CreateImageVariationRequest;
import com.felh.openai.model.Model;
import io.reactivex.rxjava3.core.Single;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface OpenAiApi {

    /**
     * List models
     * GET
     * https://api.openai.com/v1/models
     * Lists the currently available models, and provides basic information about each one such as the owner and availability.
     *
     * @return
     */
    @GET("v1/models")
    Single<OpenAiListResponse<Model>> listModels();

    /**
     * Retrieve model
     * GET
     * https://api.openai.com/v1/models/{model}
     * Retrieves a model instance, providing basic information about the model such as the owner and permission.
     *
     * @param modelId
     * @return
     */
    @GET("v1/models/{model_id}")
    Single<Model> getModel(@Path("model_id") String modelId);

    /**
     * Create completion
     * POST
     * https://api.openai.com/v1/completions
     * Creates a completion for the provided prompt and parameters
     *
     * @param request
     * @return
     */
    @POST("v1/completions")
    Single<Completion> createCompletion(@Body CreateCompletionRequest request);

    /**
     * Create chat completionBeta
     * POST
     * https://api.openai.com/v1/chat/completions
     * Creates a completion for the chat message
     *
     * @param request
     * @return
     */
    @POST("v1/chat/completions")
    Single<ChatCompletion> createChatCompletion(@Body CreateChatCompletionRequest request);

    /**
     * Create edit
     * POST
     * https://api.openai.com/v1/edits
     * Creates a new edit for the provided input, instruction, and parameters.
     *
     * @param request
     * @return
     */
    @POST("v1/edits")
    Single<Edit> createEdit(@Body CreateEditRequest request);

    /**
     * Create image Beta
     * POST
     * <p>
     * https://api.openai.com/v1/images/generations
     * <p>
     * Creates an image given a prompt.
     *
     * @param request
     * @return
     */
    @POST("v1/images/generations")
    Single<CreateImageResponse> createImage(@Body CreateImageRequest request);

    /**
     * Create image editBeta
     * POST
     * https://api.openai.com/v1/images/edits
     * Creates an edited or extended image given an original image and a prompt.
     *
     * @param request
     * @return
     */
    @POST("v1/images/edits")
    Single<CreateImageResponse> createImageEdit(@Body RequestBody request);

    /**
     * Create image variationBeta
     * POST
     * https://api.openai.com/v1/images/variations
     * Creates a variation of a given image.
     *
     * @param request
     * @return
     */
    @POST("v1/images/variations")
    Single<CreateImageResponse> createImageVariation(@Body RequestBody request);

    /**
     * Create embeddings
     * POST
     * https://api.openai.com/v1/embeddings
     * Creates an embedding vector representing the input text.
     *
     * @param request
     * @return
     */
    @POST("v1/embeddings")
    Single<CreateEmbeddingResponse> createEmbedding(@Body CreateEmbeddingRequest request);

    /**
     * Create transcriptionBeta
     * POST
     * https://api.openai.com/v1/audio/transcriptions
     * Transcribes audio into the input language.
     *
     * @param request
     * @return
     */
    @POST("v1/audio/transcriptions")
    Single<AudioResponse> createAudioTranscription(@Body RequestBody request);

    /**
     * Create translationBeta
     * POST
     * https://api.openai.com/v1/audio/translations
     * Translates audio into into English.
     *
     * @param request
     * @return
     */
    @POST("v1/audio/translations")
    Single<AudioResponse> createAudioTranslation(@Body RequestBody request);
}
