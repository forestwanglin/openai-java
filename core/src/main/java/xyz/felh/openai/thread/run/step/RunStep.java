package xyz.felh.openai.thread.run.step;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
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
    private Integer createdAt;

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} associated with the run step.
     */
    private String assistantId;

    /**
     * The ID of the {@link xyz.felh.openai.thread.Thread} that was run.
     */
    private String threadId;

    /**
     * The ID of the {@link xyz.felh.openai.thread.run.Run} that this run step is a part of.
     */
    private String runId;

    /**
     * The type of run step, which can be either message_creation or tool_calls.
     * <p>
     * {@link Type}
     */
    private Type type;

    /**
     * The status of the run step, which can be either in_progress, cancelled, failed, completed, or expired.
     * <p>
     * {@link Status}
     */
    private Status status;

    /**
     * The details of the run step.
     * <p>
     * See {@link StepDetails}
     */
    private StepDetails stepDetails;

    /**
     * The last error associated with this run step. Will be null if there are no errors.
     * <p>
     * See {@link LastError}
     */
    private LastError lastError;

    /**
     * The Unix timestamp (in seconds) for when the run step expired. A step is considered expired if the parent run is expired.
     */
    private Integer expiredAt;

    /**
     * The Unix timestamp (in seconds) for when the run step was cancelled.
     */
    private Integer cancelledAt;

    /**
     * The Unix timestamp (in seconds) for when the run step failed.
     */
    private Integer failedAt;

    /**
     * The Unix timestamp (in seconds) for when the run step completed.
     */
    private Integer completedAt;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

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