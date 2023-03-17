package com.felh.openai;

import com.felh.openai.audio.AudioResponse;
import com.felh.openai.completion.Completion;
import com.felh.openai.completion.CreateCompletionRequest;
import com.felh.openai.completion.chat.ChatCompletion;
import com.felh.openai.completion.chat.CreateChatCompletionRequest;
import com.felh.openai.edit.CreateEditRequest;
import com.felh.openai.edit.Edit;
import com.felh.openai.embedding.CreateEmbeddingRequest;
import com.felh.openai.embedding.CreateEmbeddingResponse;
import com.felh.openai.file.File;
import com.felh.openai.finetune.CreateFineTuneRequest;
import com.felh.openai.finetune.FineTune;
import com.felh.openai.finetune.FineTuneEvent;
import com.felh.openai.image.CreateImageRequest;
import com.felh.openai.image.CreateImageResponse;
import com.felh.openai.model.Model;
import com.felh.openai.moderation.CreateModerationRequest;
import com.felh.openai.moderation.CreateModerationResponse;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

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

    /**
     * Create moderation
     * POST
     * https://api.openai.com/v1/moderations
     * Classifies if text violates OpenAI's Content Policy
     *
     * @param request
     * @return
     */
    @POST("v1/moderations")
    Single<CreateModerationResponse> createModeration(@Body CreateModerationRequest request);

    /**
     * List files
     * GET
     * https://api.openai.com/v1/files
     * Returns a list of files that belong to the user's organization.
     *
     * @return
     */
    @GET("/v1/files")
    Single<OpenAiListResponse<File>> listFiles();

    /**
     * Upload file
     * POST
     * <p>
     * https://api.openai.com/v1/files
     * <p>
     * Upload a file that contains document(s) to be used across various endpoints/features. Currently,
     * the size of all the files uploaded by one organization can be up to 1 GB.
     * Please contact us if you need to increase the storage limit.
     *
     * @param file
     * @param purpose
     * @return
     */
    @Multipart
    @POST("/v1/files")
    Single<File> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * Delete file
     * DELETE
     * https://api.openai.com/v1/files/{file_id}
     * Delete a file.
     *
     * @param fileId
     * @return
     */
    @DELETE("/v1/files/{file_id}")
    Single<DeleteResponse> deleteFile(@Path("file_id") String fileId);

    /**
     * Retrieve file
     * GET
     * https://api.openai.com/v1/files/{file_id}
     * Returns information about a specific file.
     *
     * @param fileId
     * @return
     */
    @GET("/v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    /**
     * Retrieve file content
     * GET
     * https://api.openai.com/v1/files/{file_id}/content
     * Returns the contents of the specified file
     *
     * @param fileId
     * @return
     */
    @GET("/v1/files/{file_id}/content")
    Single<String> retrieveFileContent(@Path("file_id") String fileId);

    /**
     * Create fine-tune
     * POST
     * https://api.openai.com/v1/fine-tunes
     * Creates a job that fine-tunes a specified model from a given dataset.
     * Response includes details of the enqueued job including job status and the name of the fine-tuned models once complete.
     *
     * @param request
     * @return
     */
    @POST("/v1/fine-tunes")
    Single<FineTune> createFineTune(@Body CreateFineTuneRequest request);

    /**
     * List fine-tunes
     * GET
     * https://api.openai.com/v1/fine-tunes
     * List your organization's fine-tuning jobs
     *
     * @return
     */
    @GET("/v1/fine-tunes")
    Single<OpenAiListResponse<FineTune>> listFineTunes();

    /**
     * Retrieve fine-tune
     * GET
     * https://api.openai.com/v1/fine-tunes/{fine_tune_id}
     * Gets info about the fine-tune job.
     *
     * @param fineTuneId
     * @return
     */
    @GET("/v1/fine-tunes/{fine_tune_id}")
    Single<FineTune> retrieveFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * Cancel fine-tune
     * POST
     * https://api.openai.com/v1/fine-tunes/{fine_tune_id}/cancel
     * Immediately cancel a fine-tune job.
     *
     * @param fineTuneId
     * @return
     */
    @POST("/v1/fine-tunes/{fine_tune_id}/cancel")
    Single<FineTune> cancelFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * List fine-tune events
     * GET
     * https://api.openai.com/v1/fine-tunes/{fine_tune_id}/events
     * Get fine-grained status updates for a fine-tune job.
     *
     * @param fineTuneId
     * @return
     */
    @GET("/v1/fine-tunes/{fine_tune_id}/events")
    Single<OpenAiListResponse<FineTuneEvent>> listFineTuneEvents(@Path("fine_tune_id") String fineTuneId);

    /**
     * Delete fine-tune model
     * DELETE
     * https://api.openai.com/v1/models/{model}
     * Delete a fine-tuned model. You must have the Owner role in your organization.
     *
     * @param fineTuneId
     * @return
     */
    @DELETE("/v1/models/{fine_tune_id}")
    Single<DeleteResponse> deleteFineTune(@Path("fine_tune_id") String fineTuneId);


}
