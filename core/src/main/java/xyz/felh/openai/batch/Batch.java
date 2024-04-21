package xyz.felh.openai.batch;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.OpenAiApiListResponse;
import xyz.felh.openai.OpenAiApiObjectWithId;

import java.util.Map;

/**
 * See <a href="https://platform.openai.com/docs/api-reference/batch/object">document</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Batch extends OpenAiApiObjectWithId {

    public static String OBJECT = "batch";

    /**
     * The OpenAI API endpoint used by the batch.
     */
    @JSONField(name = "endpoint")
    @JsonProperty("endpoint")
    private String endpoint;

    @JSONField(name = "errors")
    @JsonProperty("errors")
    private OpenAiApiListResponse<BatchError> errors;

    /**
     * The ID of the input file for the batch.
     */
    @JSONField(name = "input_file_id")
    @JsonProperty("input_file_id")
    private String inputFileId;

    /**
     * The time frame within which the batch should be processed.
     */
    @JSONField(name = "completion_window")
    @JsonProperty("completion_window")
    private String completionWindow;

    /**
     * The current status of the batch.
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private String status;

    /**
     * The ID of the file containing the outputs of successfully executed requests.
     */
    @JSONField(name = "output_file_id")
    @JsonProperty("output_file_id")
    private String outputFileId;

    /**
     * The ID of the file containing the outputs of requests with errors.
     */
    @JSONField(name = "error_file_id")
    @JsonProperty("error_file_id")
    private String errorFileId;

    /**
     * The Unix timestamp (in seconds) for when the batch was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * The Unix timestamp (in seconds) for when the batch started processing.
     */
    @JSONField(name = "in_progress_at")
    @JsonProperty("in_progress_at")
    private Long inProgressAt;

    /**
     * The Unix timestamp (in seconds) for when the batch will expire.
     */
    @JSONField(name = "expires_at")
    @JsonProperty("expires_at")
    private Long expiresAt;

    /**
     * The Unix timestamp (in seconds) for when the batch started finalizing.
     */
    @JSONField(name = "finalizing_at")
    @JsonProperty("finalizing_at")
    private Long finalizingAt;

    /**
     * The Unix timestamp (in seconds) for when the batch was completed.
     */
    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Long completedAt;

    /**
     * The Unix timestamp (in seconds) for when the batch failed.
     */
    @JSONField(name = "failed_at")
    @JsonProperty("failed_at")
    private Long failedAt;

    /**
     * The Unix timestamp (in seconds) for when the batch expired.
     */
    @JSONField(name = "expired_at")
    @JsonProperty("expired_at")
    private Long expiredAt;

    /**
     * The Unix timestamp (in seconds) for when the batch started cancelling.
     */
    @JSONField(name = "cancelling_at")
    @JsonProperty("cancelling_at")
    private Long cancellingAt;

    /**
     * The Unix timestamp (in seconds) for when the batch was cancelled.
     */
    @JSONField(name = "cancelled_at")
    @JsonProperty("cancelled_at")
    private Long cancelledAt;

    /**
     * The request counts for different statuses within the batch.
     */
    @JSONField(name = "request_counts")
    @JsonProperty("request_counts")
    private RequestCounts requestCounts;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<?, ?> metadata;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class RequestCounts implements IOpenAiBean {

        /**
         * Total number of requests in the batch.
         */
        @JSONField(name = "total")
        @JsonProperty("total")
        private Integer total;

        /**
         * Number of requests that have been completed successfully.
         */
        @JSONField(name = "completed")
        @JsonProperty("completed")
        private Integer completed;

        /**
         * Number of requests that have failed.
         */
        @JSONField(name = "failed")
        @JsonProperty("failed")
        private Integer failed;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class BatchError implements IOpenAiApiObject {

        /**
         * An error code identifying the error type.
         */
        @JSONField(name = "code")
        @JsonProperty("code")
        private String code;

        /**
         * A human-readable message providing more details about the error.
         */
        @JSONField(name = "message")
        @JsonProperty("message")
        private String message;

        /**
         * The name of the parameter that caused the error, if applicable.
         */
        @JSONField(name = "param")
        @JsonProperty("param")
        private String param;

        /**
         * The line number of the input file where the error occurred, if applicable.
         */
        @JSONField(name = "line")
        @JsonProperty("line")
        private Integer line;

    }

}
