package xyz.felh.openai.thread.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.openai.OpenAiApiObjectWithId;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Message extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.message";

    /**
     * The Unix timestamp (in seconds) for when the thread was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The {@link xyz.felh.openai.thread.Thread} ID that this message belongs to.
     */
    @JSONField(name = "thread_id")
    @JsonProperty("thread_id")
    private String threadId;

    /**
     * The status of the message, which can be either in_progress, incomplete, or completed.
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    @JSONField(name = "incomplete_details")
    @JsonProperty("incomplete_details")
    private IncompleteDetails incompleteDetails;

    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Integer completedAt;

    @JSONField(name = "incomplete_at")
    @JsonProperty("incomplete_at")
    private Integer incompleteAt;

    /**
     * The entity that produced the message. One of user or assistant.
     * <p>
     * See {@link xyz.felh.openai.thread.message.Message Role}
     */
    @JSONField(name = "role")
    @JsonProperty("role")
    private Role role;

    /**
     * The content of the message in array of text and/or images.
     * <p>
     * See {@link MessageContent}
     */
    @JSONField(name = "content")
    @JsonProperty("content")
    private List<MessageContent> content;

    /**
     * If applicable, the ID of the {@link xyz.felh.openai.assistant.Assistant} that authored this message.
     */
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * If applicable, the ID of the {@link xyz.felh.openai.thread.run.Run} associated with the authoring of this message.
     */
    @JSONField(name = "run_id")
    @JsonProperty("run_id")
    private String runId;

    /**
     * A list of file IDs that the assistant should use. Useful for tools like retrieval and code_interpreter that can access files. A maximum of 10 files can be attached to a message.
     * <p>
     * See {@link xyz.felh.openai.file.File}
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

    @Getter
    public enum Role {

        USER("user"),
        ASSISTANT("assistant");

        Role(final String value) {
            this.value = value;
        }

        private final String value;

        @JsonValue
        public String value() {
            return value;
        }

    }

    @Getter
    public enum Status {

        IN_PROGRESS("in_progress"),
        INCOMPLETE("incomplete"),
        COMPLETED("completed");

        Status(final String value) {
            this.value = value;
        }

        private final String value;

        @JsonValue
        public String value() {
            return value;
        }

    }

}