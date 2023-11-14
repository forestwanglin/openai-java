package xyz.felh.openai.thread.run;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
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
    private Integer createdAt;

    /**
     * The ID of the {@link xyz.felh.openai.thread.Thread} that was executed on as a part of this run.
     */
    private String threadId;

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} used for execution of this run.
     */
    private String assistantId;

    /**
     * The status of the run, which can be either queued, in_progress, requires_action, cancelling, cancelled, failed, completed, or expired.
     * See {@link Status}
     */
    private String status;

    /**
     * Details on the action required to continue the run. Will be null if no action is required.
     * <p>
     * See {@link RequiredAction}
     */
    private RequiredAction requiredAction;

    /**
     * The last error associated with this run. Will be null if there are no errors.
     * <p>
     * See {@link LastError}
     */
    private LastError lastError;

    /**
     * The Unix timestamp (in seconds) for when the run will expire.
     */
    private Integer expiresAt;

    /**
     * The Unix timestamp (in seconds) for when the run was started.
     */
    private Integer startedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was cancelled.
     */
    private Integer cancelledAt;

    /**
     * The Unix timestamp (in seconds) for when the run failed.
     */
    private Integer failedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was completed.
     */
    private Integer completedAt;

    /**
     * The model that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    private String model;

    /**
     * The instructions that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    private String instructions;

    /**
     * The list of tools that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     * <p>
     * See {@link AssistantTool}
     */
    private List<AssistantTool> tools;

    /**
     * The list of {@link xyz.felh.openai.file.File} IDs the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    private List<String> fileIds;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

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