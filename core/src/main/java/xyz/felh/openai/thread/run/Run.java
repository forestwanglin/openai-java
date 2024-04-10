package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import xyz.felh.openai.assistant.AssistantTool;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Run extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.run";

    /**
     * The Unix timestamp (in seconds) for when the run was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The ID of the {@link xyz.felh.openai.thread.Thread} that was executed on as a part of this run.
     */
    @JSONField(name = "thread_id")
    @JsonProperty("thread_id")
    private String threadId;

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} used for execution of this run.
     */
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The status of the run, which can be either queued, in_progress, requires_action, cancelling, cancelled, failed, completed, or expired.
     * See {@link Status}
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    /**
     * Details on the action required to continue the run. Will be null if no action is required.
     * <p>
     * See {@link RequiredAction}
     */
    @JSONField(name = "required_action")
    @JsonProperty("required_action")
    private RequiredAction requiredAction;

    /**
     * The last error associated with this run. Will be null if there are no errors.
     * <p>
     * See {@link LastError}
     */
    @JSONField(name = "last_error")
    @JsonProperty("last_error")
    private LastError lastError;

    /**
     * The Unix timestamp (in seconds) for when the run will expire.
     */
    @JSONField(name = "expires_at")
    @JsonProperty("expires_at")
    private Integer expiresAt;

    /**
     * The Unix timestamp (in seconds) for when the run was started.
     */
    @JSONField(name = "started_at")
    @JsonProperty("started_at")
    private Integer startedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was cancelled.
     */
    @JSONField(name = "cancelled_at")
    @JsonProperty("cancelled_at")
    private Integer cancelledAt;

    /**
     * The Unix timestamp (in seconds) for when the run failed.
     */
    @JSONField(name = "failed_at")
    @JsonProperty("failed_at")
    private Integer failedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was completed.
     */
    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Integer completedAt;

    /**
     * The model that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * The instructions that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    @JSONField(name = "instructions")
    @JsonProperty("instructions")
    private String instructions;

    /**
     * The list of tools that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     * <p>
     * See {@link AssistantTool}
     */
    @JSONField(name = "tools")
    @JsonProperty("tools")
    private List<AssistantTool> tools;

    /**
     * The list of {@link xyz.felh.openai.file.File} IDs the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    @JSONField(name = "file_ids")
    @JsonProperty("file_ids")
    private List<String> fileIds;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    /**
     * Usage statistics related to the run. This value will be null if the run is not in a terminal state (i.e. in_progress, queued, etc.).
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

    /**
     * The sampling temperature used for this run. If not set, defaults to 1.
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    public enum Status {
        QUEUED("queued"),
        IN_PROGRESS("in_progress"),
        REQUIRES_ACTION("requires_action"),
        CANCELLING("cancelling"),
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