package xyz.felh.openai;

import xyz.felh.openai.audio.AudioResponse;
import xyz.felh.openai.completion.Completion;
import xyz.felh.openai.completion.CreateCompletionRequest;
import xyz.felh.openai.completion.chat.ChatCompletion;
import xyz.felh.openai.completion.chat.CreateChatCompletionRequest;
import xyz.felh.openai.edit.CreateEditRequest;
import xyz.felh.openai.edit.Edit;
import xyz.felh.openai.embedding.CreateEmbeddingRequest;
import xyz.felh.openai.embedding.CreateEmbeddingResponse;
import xyz.felh.openai.file.File;
import xyz.felh.openai.file.RetrieveFileContentResponse;
import xyz.felh.openai.finetune.CreateFineTuneRequest;
import xyz.felh.openai.finetune.FineTune;
import xyz.felh.openai.finetune.FineTuneEvent;
import xyz.felh.openai.image.CreateImageRequest;
import xyz.felh.openai.image.ImageResponse;
import xyz.felh.openai.model.Model;
import xyz.felh.openai.moderation.CreateModerationRequest;
import xyz.felh.openai.moderation.CreateModerationResponse;
import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.http.*;

/**
 * Retrofit2 API interface
 */
public interface OpenAiApi {

    /**
     * List models
     *
     * @return Lists the currently available models, and provides basic
     * information about each one such as the owner and availability.
     */
    @GET("/v1/models")
    Single<OpenAiApiListResponse<Model>> listModels();

    /**
     * Retrieve model
     *
     * @param modelId
     * @return Retrieves a model instance, providing basic information
     * about the model such as the owner and permission.
     */
    @GET("/v1/models/{model_id}")
    Single<Model> getModel(@Path("model_id") String modelId);

    /**
     * Create completion
     * Creates a completion for the provided prompt and parameters
     *
     * @param request
     * @return Completion detail information
     */
    @POST("/v1/completions")
    Single<Completion> createCompletion(@Body CreateCompletionRequest request);

    /**
     * Create chat completionBeta
     * Creates a completion for the chat message
     *
     * @param request
     * @return Chat Completion information
     */
    @POST("/v1/chat/completions")
    Single<ChatCompletion> createChatCompletion(@Body CreateChatCompletionRequest request);

    /**
     * Create edit
     * Creates a new edit for the provided input, instruction, and parameters.
     *
     * @param request
     * @return Edit information
     */
    @POST("/v1/edits")
    Single<Edit> createEdit(@Body CreateEditRequest request);

    /**
     * Create image Beta
     * Creates an image given a prompt.
     *
     * @param request
     * @return Image Information Wrapper
     */
    @POST("/v1/images/generations")
    Single<ImageResponse> createImage(@Body CreateImageRequest request);

    /**
     * Create image editBeta
     * Creates an edited or extended image given an original image and a prompt.
     *
     * @param request
     * @return Image Information Wrapper
     */
    @POST("/v1/images/edits")
    Single<ImageResponse> createImageEdit(@Body RequestBody request);

    /**
     * Create image variationBeta
     * Creates a variation of a given image.
     *
     * @param request
     * @return Image Information Wrapper
     */
    @POST("/v1/images/variations")
    Single<ImageResponse> createImageVariation(@Body RequestBody request);

    /**
     * Create embeddings
     * Creates an embedding vector representing the input text.
     *
     * @param request
     * @return Embeddings
     */
    @POST("/v1/embeddings")
    Single<CreateEmbeddingResponse> createEmbedding(@Body CreateEmbeddingRequest request);

    /**
     * Create transcriptionBeta
     * Transcribes audio into the input language.
     *
     * @param request
     * @return Text of audio
     */
    @POST("/v1/audio/transcriptions")
    Single<AudioResponse> createAudioTranscription(@Body RequestBody request);

    /**
     * Create translationBeta
     * Translates audio into English.
     *
     * @param request
     * @return Text of audio
     */
    @POST("/v1/audio/translations")
    Single<AudioResponse> createAudioTranslation(@Body RequestBody request);

    /**
     * Create moderation
     * Classifies if text violates OpenAI's Content Policy
     *
     * @param request
     * @return Moderation
     */
    @POST("/v1/moderations")
    Single<CreateModerationResponse> createModeration(@Body CreateModerationRequest request);

    /**
     * List files
     * Returns a list of files that belong to the user's organization.
     *
     * @return File list
     */
    @GET("/v1/files")
    Single<OpenAiApiListResponse<File>> listFiles();

    /**
     * Upload file
     * Upload a file that contains document(s) to be used across various endpoints/features. Currently,
     * the size of all the files uploaded by one organization can be up to 1 GB.
     * Please contact us if you need to increase the storage limit.
     *
     * @param file
     * @param purpose
     * @return File information
     */
    @Multipart
    @POST("/v1/files")
    Single<File> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * Delete file
     * Delete a file.
     *
     * @param fileId
     * @return Delete file and status
     */
    @DELETE("/v1/files/{file_id}")
    Single<DeleteResponse> deleteFile(@Path("file_id") String fileId);

    /**
     * Retrieve file
     *
     * @param fileId
     * @return information about a specific file.
     */
    @GET("/v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    /**
     * Retrieve file content
     *
     * @param fileId
     * @return the contents of the specified file
     */
    @GET("/v1/files/{file_id}/content")
    Single<RetrieveFileContentResponse> retrieveFileContent(@Path("file_id") String fileId);

    /**
     * Create fine-tune
     * Creates a job that fine-tunes a specified model from a given dataset.
     * Response includes details of the enqueued job including job status and the name of the fine-tuned models once complete.
     *
     * @param request
     * @return Fine-tune information
     */
    @POST("/v1/fine-tunes")
    Single<FineTune> createFineTune(@Body CreateFineTuneRequest request);

    /**
     * List fine-tunes
     * List your organization's fine-tuning jobs
     *
     * @return Fine-tune list
     */
    @GET("/v1/fine-tunes")
    Single<OpenAiApiListResponse<FineTune>> listFineTunes();

    /**
     * Retrieve fine-tune
     * Gets info about the fine-tune job.
     *
     * @param fineTuneId
     * @return Fine-tune information
     */
    @GET("/v1/fine-tunes/{fine_tune_id}")
    Single<FineTune> retrieveFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * Cancel fine-tune
     * Immediately cancel a fine-tune job.
     *
     * @param fineTuneId
     * @return Fine-tune information
     */
    @POST("/v1/fine-tunes/{fine_tune_id}/cancel")
    Single<FineTune> cancelFineTune(@Path("fine_tune_id") String fineTuneId);

    /**
     * List fine-tune events
     * Get fine-grained status updates for a fine-tune job.
     *
     * @param fineTuneId
     * @return Fine-tune event information
     */
    @GET("/v1/fine-tunes/{fine_tune_id}/events")
    Single<OpenAiApiListResponse<FineTuneEvent>> listFineTuneEvents(@Path("fine_tune_id") String fineTuneId);

    /**
     * Delete fine-tune model
     * Delete a fine-tuned model. You must have the Owner role in your organization.
     *
     * @param modelAndFineTuneId
     * @return Delete status
     */
    @DELETE("/v1/models/{model_and_fine_tune_id}")
    Single<DeleteResponse> deleteFineTune(@Path("model_and_fine_tune_id") String modelAndFineTuneId);

}
