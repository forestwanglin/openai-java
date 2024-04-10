package xyz.felh.openai.thread.run.step;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.thread.run.LastError;

import java.util.Arrays;
import java.util.Map;


@EqualsAndHashCode(callSuper = true)
@Data
public class RunStep extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.run.step";

    /**
     * The Unix timestamp (in seconds) for when the run step was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} associated with the run step.
     */
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The ID of the {@link xyz.felh.openai.thread.Thread} that was run.
     */
    @JSONField(name = "thread_id")
    @JsonProperty("thread_id")
    private String threadId;

    /**
     * The ID of the {@link xyz.felh.openai.thread.run.Run} that this run step is a part of.
     */
    @JSONField(name = "run_id")
    @JsonProperty("run_id")
    private String runId;

    /**
     * The type of run step, which can be either message_creation or tool_calls.
     * <p>
     * {@link Type}
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * The status of the run step, which can be either in_progress, cancelled, failed, completed, or expired.
     * <p>
     * {@link Status}
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    /**
     * The details of the run step.
     * <p>
     * See {@link StepDetails}
     */
    @JSONField(name = "step_details")
    @JsonProperty("step_details")
    private StepDetails stepDetails;

    /**
     * The last error associated with this run step. Will be null if there are no errors.
     * <p>
     * See {@link LastError}
     */
    @JSONField(name = "last_error")
    @JsonProperty("last_error")
    private LastError lastError;

    /**
     * The Unix timestamp (in seconds) for when the run step expired. A step is considered expired if the parent run is expired.
     */
    @JSONField(name = "expired_at")
    @JsonProperty("expired_at")
    private Integer expiredAt;

    /**
     * The Unix timestamp (in seconds) for when the run step was cancelled.
     */
    @JSONField(name = "cancelled_at")
    @JsonProperty("cancelled_at")
    private Integer cancelledAt;

    /**
     * The Unix timestamp (in seconds) for when the run step failed.
     */
    @JSONField(name = "failed_at")
    @JsonProperty("failed_at")
    private Integer failedAt;

    /**
     * The Unix timestamp (in seconds) for when the run step completed.
     */
    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Integer completedAt;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    /**
     * Usage statistics related to the run step. This value will be null while the run step's status is in_progress.
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

    public enum Type {
        MESSAGE_CREATION("message_creation"),
        TOOL_CALLS("tool_calls");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Type findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }

    }

    public enum Status {
        IN_PROGRESS("in_progress"),
        CANCELLED("cancelled"),
        FAILED("failed"),
        COMPLETED("completed"),
        EXPIRED("expired"),
        ;

        private final String value;

        Status(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Status findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }

    }

}