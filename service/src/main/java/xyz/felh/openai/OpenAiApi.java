package xyz.felh.openai;

import io.reactivex.rxjava3.core.Single;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.http.*;
import xyz.felh.openai.assistant.Assistant;
import xyz.felh.openai.assistant.CreateAssistantRequest;
import xyz.felh.openai.assistant.ModifyAssistantRequest;
import xyz.felh.openai.assistant.file.AssistantFile;
import xyz.felh.openai.assistant.file.CreateAssistantFileRequest;
import xyz.felh.openai.audio.AudioResponse;
import xyz.felh.openai.audio.CreateSpeechRequest;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.embedding.CreateEmbeddingRequest;
import xyz.felh.openai.embedding.CreateEmbeddingResponse;
import xyz.felh.openai.file.File;
import xyz.felh.openai.fineTuning.CreateFineTuningJobRequest;
import xyz.felh.openai.fineTuning.FineTuningJob;
import xyz.felh.openai.fineTuning.FineTuningJobEvent;
import xyz.felh.openai.image.CreateImageRequest;
import xyz.felh.openai.image.ImageResponse;
import xyz.felh.openai.model.Model;
import xyz.felh.openai.moderation.CreateModerationRequest;
import xyz.felh.openai.moderation.CreateModerationResponse;
import xyz.felh.openai.thread.CreateThreadRequest;
import xyz.felh.openai.thread.ModifyThreadRequest;
import xyz.felh.openai.thread.Thread;
import xyz.felh.openai.thread.message.CreateMessageRequest;
import xyz.felh.openai.thread.message.Message;
import xyz.felh.openai.thread.message.ModifyMessageRequest;
import xyz.felh.openai.thread.message.file.MessageFile;
import xyz.felh.openai.thread.run.*;
import xyz.felh.openai.thread.run.step.RunStep;

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
     * @param modelId model ID
     * @return Retrieves a model instance, providing basic information
     * about the model such as the owner and permission.
     */
    @GET("/v1/models/{model_id}")
    Single<Model> getModel(@Path("model_id") String modelId);

    /**
     * Create chat completionBeta
     * Creates a completion for the chat message
     *
     * @param request Create chat completion request
     * @return Chat Completion information
     */
    @POST("/v1/chat/completions")
    Single<ChatCompletion> createChatCompletion(@Body CreateChatCompletionRequest request);

    /**
     * Create image Beta
     * Creates an image given a prompt.
     *
     * @param request create image request
     * @return Image Information Wrapper
     */
    @POST("/v1/images/generations")
    Single<ImageResponse> createImage(@Body CreateImageRequest request);

    /**
     * Create image editBeta
     * Creates an edited or extended image given an original image and a prompt.
     *
     * @param request request body
     * @return Image Information Wrapper
     */
    @POST("/v1/images/edits")
    Single<ImageResponse> createImageEdit(@Body RequestBody request);

    /**
     * Create image variationBeta
     * Creates a variation of a given image.
     *
     * @param request request body
     * @return Image Information Wrapper
     */
    @POST("/v1/images/variations")
    Single<ImageResponse> createImageVariation(@Body RequestBody request);

    /**
     * Create embeddings
     * Creates an embedding vector representing the input text.
     *
     * @param request create embedding request
     * @return Embeddings
     */
    @POST("/v1/embeddings")
    Single<CreateEmbeddingResponse> createEmbedding(@Body CreateEmbeddingRequest request);

    /**
     * Generates audio from the input text.
     *
     * @param request create speech request
     * @return audio file
     */
    @POST("/v1/audio/speech")
    Single<ResponseBody> createSpeech(@Body CreateSpeechRequest request);

    /**
     * Create transcriptionBeta
     * Transcribes audio into the input language.
     *
     * @param request body
     * @return Text of audio
     */
    @POST("/v1/audio/transcriptions")
    Single<AudioResponse> createAudioTranscription(@Body RequestBody request);

    /**
     * Create translationBeta
     * Translates audio into English.
     *
     * @param request body
     * @return Text of audio
     */
    @POST("/v1/audio/translations")
    Single<AudioResponse> createAudioTranslation(@Body RequestBody request);

    /**
     * Create moderation
     * Classifies if text violates OpenAI's Content Policy
     *
     * @param request create moderation request
     * @return Moderation
     */
    @POST("/v1/moderations")
    Single<CreateModerationResponse> createModeration(@Body CreateModerationRequest request);

    /**
     * List files
     * <p>
     * RReturns a list of files that belong to the user's organization.
     *
     * @return A list of {@link File} objects.
     */
    @GET("/v1/files")
    Single<OpenAiApiListResponse<File>> listFiles();

    /**
     * Upload file
     * <p>
     * Upload a file that can be used across various endpoints. The size of all the files uploaded by one organization can be up to 100 GB.
     * <p>
     * The size of individual files can be a maximum of 512 MB or 2 million tokens for Assistants. See the <a href="https://platform.openai.com/docs/assistants/tools">Assistants Tools guide</a> to learn more about the types of files supported. The Fine-tuning API only supports .jsonl files.
     * <p>
     * Please <a href="https://help.openai.com/en/">contact us</a> if you need to increase these storage limits.
     *
     * @param file    The File object (not file name) to be uploaded.
     * @param purpose The intended purpose of the uploaded file. Use "fine-tune" for <a href="https://platform.openai.com/docs/api-reference/fine-tuning">Fine-tuning</a> and "assistants" for <a href="https://platform.openai.com/docs/api-reference/assistants">Assistants</a> and <a href="https://platform.openai.com/docs/api-reference/messages">Messages</a>. This allows us to validate the format of the uploaded file is correct for fine-tuning.
     * @return The uploaded {@link File} object.
     */
    @Multipart
    @POST("/v1/files")
    Single<File> uploadFile(@Part MultipartBody.Part file, @Part("purpose") RequestBody purpose);

    /**
     * Delete file
     * <p>
     * Delete a file.
     *
     * @param fileId fileId
     * @return Deletion status
     */
    @DELETE("/v1/files/{file_id}")
    Single<DeleteResponse> deleteFile(@Path("file_id") String fileId);

    /**
     * Retrieve file
     * <p>
     * Returns information about a specific file.
     *
     * @param fileId fileId
     * @return The {@link File} object matching the specified ID.
     */
    @GET("/v1/files/{file_id}")
    Single<File> retrieveFile(@Path("file_id") String fileId);

    /**
     * Retrieve file content
     * <p>
     * Returns the contents of the specified file.
     *
     * @param fileId The ID of the file to use for this request.
     * @return The file content.
     */
    @GET("/v1/files/{file_id}/content")
    Single<String> retrieveFileContent(@Path("file_id") String fileId);

    // fine-tuning job

    /**
     * Create fine-tuning job
     * <p>
     * Creates a job that fine-tunes a specified model from a given dataset.
     * <p>
     * Response includes details of the enqueued job including job status and the name of the fine-tuned models once complete.
     *
     * @param request create fine tuning job request
     * @return A {@link FineTuningJob} object.
     */
    @POST("/v1/fine_tuning/jobs")
    Single<FineTuningJob> createFineTuningJob(@Body CreateFineTuningJobRequest request);

    /**
     * Cancel fine-tuning
     * <p>
     * Immediately cancel a fine-tune job.
     *
     * @param fineTuningJobId The ID of the fine-tuning job to cancel.
     * @return The cancelled {@link FineTuningJob} object.
     */
    @POST("/v1/fine_tuning/jobs/{fine_tuning_job_id}/cancel")
    Single<FineTuningJob> cancelFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);


    /**
     * Retrieve fine-tuning job
     * <p>
     * Get info about a fine-tuning job.
     *
     * @param fineTuningJobId The ID of the fine-tuning job.
     * @return The {@link FineTuningJob} object with the given ID.
     */
    @GET("/v1/fine_tuning/jobs/{fine_tuning_job_id}")
    Single<FineTuningJob> retrieveFineTuningJob(@Path("fine_tuning_job_id") String fineTuningJobId);


    /**
     * List fine-tuning events
     * <p>
     * Get status updates for a fine-tuning job.
     *
     * @param fineTuningJobId The ID of the fine-tuning job to get events for.
     * @param after           Optional Identifier for the last event from the previous pagination request.
     * @param limit           Optional Defaults to 20 Number of events to retrieve.
     * @return A list of fine-tuning event objects.
     */
    @GET("/v1/fine_tuning/jobs/{fine_tuning_job_id}/events")
    Single<OpenAiApiListResponse<FineTuningJobEvent>> listFineTuningEvents(
            @Path("fine_tuning_job_id") String fineTuningJobId,
            @Query("after") String after,
            @Query("limit") Integer limit);

    /********************* Assistants BETA *************/

    /**
     * {@linkplain POST https://api.openai.com/v1/assistants}
     * <p>
     * Create an assistant with a model and instructions.
     *
     * @param request Request body
     * @return An {@link Assistant} object.
     */
    @POST("/v1/assistants")
    Single<Assistant> createAssistant(@Body CreateAssistantRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/assistants/{assistant_id}}
     * <p>
     * The assistant object matching the specified ID.
     *
     * @param assistantId The ID of the assistant to retrieve.
     * @return The ID of the {@link Assistant} to retrieve.
     */
    @GET("/v1/assistants/{assistant_id}")
    Single<Assistant> retrieveAssistant(@Path("assistant_id") String assistantId);

    /**
     * {@linkplain POST https://api.openai.com/v1/assistants/{assistant_id}}
     * <p>
     * Modifies an assistant.
     *
     * @param assistantId The ID of the assistant to modify.
     * @param request     Request body
     * @return The modified {@link Assistant} object.
     */
    @POST("/v1/assistants/{assistant_id}")
    Single<Assistant> modifyAssistant(@Path("assistant_id") String assistantId, @Body ModifyAssistantRequest request);

    /**
     * {@linkplain DELETE https://api.openai.com/v1/assistants/{assistant_id}}
     * <p>
     * Delete an assistant.
     *
     * @param assistantId The ID of the assistant to delete.
     * @return Deletion status
     */
    @DELETE("/v1/assistants/{assistant_id}")
    Single<DeleteResponse> deleteAssistant(@Path("assistant_id") String assistantId);

    /**
     * {@linkplain  GET https://api.openai.com/v1/assistants}
     * <p>
     * Returns a list of assistants.
     *
     * @param order  Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after  A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @param limit  A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @return A list of {@link Assistant} objects.
     */
    @GET("/v1/assistants")
    Single<OpenAiApiListResponse<Assistant>> listAssistants(
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);

    /**
     * {@linkplain POST https://api.openai.com/v1/assistants/{assistant_id}/files}
     * <p>
     * Create assistant file
     *
     * @param assistantId The ID of the assistant for which to create a File.
     * @param request     Request body
     * @return An {@link AssistantFile} object.
     */
    @POST("/v1/assistants/{assistant_id}/files")
    Single<AssistantFile> createAssistantFile(@Path("assistant_id") String assistantId,
                                              @Body CreateAssistantFileRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/assistants/{assistant_id}/files/{file_id}}
     * <p>
     * Retrieve assistant file
     *
     * @param assistantId The ID of the assistant who the file belongs to.
     * @param fileId      The ID of the file we're getting.
     * @return The {@link AssistantFile} object matching the specified ID.
     */
    @GET("/v1/assistants/{assistant_id}/files/{file_id}")
    Single<AssistantFile> retrieveAssistantFile(@Path("assistant_id") String assistantId,
                                                @Path("file_id") String fileId);

    /**
     * {@linkplain DELETE https://api.openai.com/v1/assistants/{assistant_id}/files/{file_id}}
     * <p>
     * Delete an assistant file.
     *
     * @param assistantId The ID of the assistant who the file belongs to.
     * @param fileId      The ID of the file to delete.
     * @return Deletion status
     */
    @DELETE("/v1/assistants/{assistant_id}/files/{file_id}")
    Single<DeleteResponse> deleteAssistantFile(@Path("assistant_id") String assistantId,
                                               @Path("file_id") String fileId);

    /**
     * {@linkplain  GET https://api.openai.com/v1/assistants/{assistant_id}/files}
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
    @GET("/v1/assistants/{assistant_id}/files")
    Single<OpenAiApiListResponse<AssistantFile>> listAssistantFiles(
            @Path("assistant_id") String assistantId,
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);

    /********************* Threads BETA *************/

    /**
     * {@linkplain POST https://api.openai.com/v1/threads}
     * <p>
     * Create thread
     *
     * @param request Request body
     * @return An {@link Thread} object.
     */
    @POST("/v1/threads")
    Single<Thread> createThread(@Body CreateThreadRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Retrieve thread
     *
     * @param threadId The ID of the thread to retrieve.
     * @return The {@link Thread} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}")
    Single<Thread> retrieveThread(@Path("thread_id") String threadId);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Modify thread
     *
     * @param threadId The ID of the thread to modify. Only the metadata can be modified.
     * @param request  Request body
     * @return The modified {@link java.lang.Thread} object matching the specified ID.
     */
    @POST("/v1/threads/{thread_id}")
    Single<Thread> modifyThread(@Path("thread_id") String threadId,
                                @Body ModifyThreadRequest request);

    /**
     * {@linkplain DELETE https://api.openai.com/v1/threads/{thread_id}}
     * <p>
     * Delete thread
     *
     * @param threadId The ID of the thread to delete.
     * @return Deletion status
     */
    @DELETE("/v1/threads/{thread_id}")
    Single<DeleteResponse> deleteThread(@Path("thread_id") String threadId);

    /********************* Messages BETA *************/

    /**
     * {@linkplain POST https://api.openai.com/v1/threads/{thread_id}/messages}
     * <p>
     * Create message
     *
     * @param threadId The ID of the {@link Thread} to create a message for.
     * @param request  Request body
     * @return An {@link Message} object.
     */
    @POST("/v1/threads/{thread_id}/messages")
    Single<Message> createThreadMessage(@Path("thread_id") String threadId,
                                        @Body CreateMessageRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/messages/{message_id}}
     * <p>
     * Retrieve message
     *
     * @param threadId  The ID of the {@link Thread} to which this message belongs.
     * @param messageId The ID of the message to retrieve.
     * @return The {@link Message} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}/messages/{message_id}")
    Single<Message> retrieveThreadMessage(@Path("thread_id") String threadId,
                                          @Path("message_id") String messageId);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/messages/{message_id}}
     * <p>
     * Modify message
     *
     * @param threadId  The ID of the thread to which this message belongs.
     * @param messageId The ID of the message to modify.
     * @param request   Request body
     * @return The modified {@link Message} object.
     */
    @POST("/v1/threads/{thread_id}/messages/{message_id}")
    Single<Message> modifyThreadMessage(@Path("thread_id") String threadId,
                                        @Path("message_id") String messageId,
                                        @Body ModifyMessageRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/messages}
     * <p>
     * List messages
     *
     * @param threadId The ID of the {@link Thread} the messages belong to.
     * @param limit    A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @param order    Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after    A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before   A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @return A list of {@link Message} objects.
     */
    @GET("/v1/threads/{thread_id}/messages")
    Single<OpenAiApiListResponse<Message>> listThreadMessages(
            @Path("thread_id") String threadId,
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/messages/{message_id}/files/{file_id}}
     * <p>
     * Retrieves a message file.
     *
     * @param threadId  The ID of the thread to which the message and File belong.
     * @param messageId The ID of the message the file belongs to.
     * @param fileId    The ID of the file being retrieved.
     * @return The {@link MessageFile} object.
     */
    @GET("/v1/threads/{thread_id}/messages/{message_id}/files/{file_id}")
    Single<MessageFile> retrieveThreadMessageFile(@Path("thread_id") String threadId,
                                                  @Path("message_id") String messageId,
                                                  @Path("file_id") String fileId);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/messages/{message_id}/files}
     * <p>
     * Returns a list of message files.
     *
     * @param threadId  The ID of the thread that the message and files belong to.
     * @param messageId The ID of the message that the files belongs to.
     * @param limit     A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @param order     Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after     A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before    A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @return A list of {@link MessageFile} objects.
     */
    @GET("/v1/threads/{thread_id}/messages")
    Single<OpenAiApiListResponse<MessageFile>> listThreadMessageFiles(
            @Path("thread_id") String threadId,
            @Path("message_id") String messageId,
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);

    /********************* Runs BETA *************/

    /**
     * {@linkplain POST https://api.openai.com/v1/threads/{thread_id}/runs}
     * <p>
     * Create a run.
     *
     * @param threadId The ID of the thread to run.
     * @param request  Request body
     * @return An {@link Run} object.
     */
    @POST("/v1/threads/{thread_id}/runs")
    Single<Run> createThreadRun(@Path("thread_id") String threadId,
                                @Body CreateRunRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}}
     * <p>
     * Retrieves a run.
     *
     * @param threadId The ID of the {@link Thread} that was run.
     * @param runId    The ID of the run to retrieve.
     * @return The {@link Run} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}/runs/{run_id}")
    Single<Run> retrieveThreadRun(@Path("thread_id") String threadId,
                                  @Path("run_id") String runId);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}}
     * <p>
     * Modify message
     *
     * @param threadId The ID of the {@link Thread} that was run.
     * @param runId    The ID of the run to modify.
     * @param request  Request body
     * @return The modified {@link Run} object.
     */
    @POST("/v1/threads/{thread_id}/runs/{run_id}")
    Single<Run> modifyThreadRun(@Path("thread_id") String threadId,
                                @Path("run_id") String runId,
                                @Body ModifyRunRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/runs}
     * <p>
     * List messages
     *
     * @param threadId The ID of the {@link Thread} the messages belong to.
     * @param limit    A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @param order    Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after    A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before   A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @return A list of {@link Run} objects.
     */
    @GET("/v1/threads/{thread_id}/runs")
    Single<OpenAiApiListResponse<Run>> listThreadRuns(
            @Path("thread_id") String threadId,
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);


    /**
     * {@linkplain POST https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}/submit_tool_outputs}
     *
     * @param threadId The ID of the {@link Thread} to which this run belongs.
     * @param runId    The ID of the run that requires the tool output submission.
     * @param request  Request body
     * @return The modified {@link Run} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}/runs/{run_id}/submit_tool_outputs")
    Single<Run> submitToolOutputs(@Path("thread_id") String threadId,
                                  @Path("run_id") String runId,
                                  @Body SubmitToolOutputsRequest request);

    /**
     * {@linkplain POST https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}/cancel}
     * <p>
     * Cancels a run that is in_progress.
     *
     * @param threadId The ID of the thread to which this run belongs.
     * @param runId    The ID of the run to cancel.
     * @return The modified {@link Run} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}/runs/{run_id}/cancel")
    Single<Run> cancelThreadRun(@Path("thread_id") String threadId,
                                @Path("run_id") String runId);

    /**
     * {@linkplain POST https://api.openai.com/v1/threads/runs}
     * <p>
     * Create a thread and run it in one request.
     *
     * @param request Request body
     * @return A {@link Run} object.
     */
    Single<Run> createThreadAndRun(@Body CreateThreadAndRunRequest request);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}/steps/{step_id}}
     * <p>
     * Retrieves a run step.
     *
     * @param threadId The ID of the thread to which the run and run step belongs.
     * @param runId    The ID of the run to which the run step belongs.
     * @param stepId   The ID of the run step to retrieve.
     * @return The {@link RunStep} object matching the specified ID.
     */
    @GET("/v1/threads/{thread_id}/runs/{run_id}/steps/{step_id}")
    Single<RunStep> retrieveThreadRunStep(@Path("thread_id") String threadId,
                                          @Path("run_id") String runId,
                                          @Path("step_id") String stepId);

    /**
     * {@linkplain GET https://api.openai.com/v1/threads/{thread_id}/runs/{run_id}/steps}
     * <p>
     * List run steps
     *
     * @param threadId The ID of the {@link Thread} the messages belong to.
     * @param runId    The ID of the run steps belong to.
     * @param limit    A limit on the number of objects to be returned. Limit can range between 1 and 100, and the default is 20.
     * @param order    Sort order by the created_at timestamp of the objects. asc for ascending order and desc for descending order.
     * @param after    A cursor for use in pagination. after is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include after=obj_foo in order to fetch the next page of the list.
     * @param before   A cursor for use in pagination. before is an object ID that defines your place in the list. For instance, if you make a list request and receive 100 objects, ending with obj_foo, your subsequent call can include before=obj_foo in order to fetch the previous page of the list.
     * @return A list of {@link RunStep} objects.
     */
    @GET("/v1/threads/{thread_id}/runs/{run_id}/steps")
    Single<OpenAiApiListResponse<RunStep>> listThreadRunSteps(
            @Path("thread_id") String threadId,
            @Path("run_id") String runId,
            @Query("limit") Integer limit,
            @Query("order") String order,
            @Query("after") String after,
            @Query("before") String before);

}
