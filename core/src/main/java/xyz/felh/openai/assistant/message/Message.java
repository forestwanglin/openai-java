package xyz.felh.openai.assistant.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.assistant.IncompleteDetails;
import xyz.felh.openai.assistant.run.Run;
import xyz.felh.openai.assistant.thread.Thread;

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
     * The {@link Thread} ID that this message belongs to.
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

    /**
     * On an incomplete message, details about why the message is incomplete.
     */
    @JSONField(name = "incomplete_details")
    @JsonProperty("incomplete_details")
    private IncompleteDetails incompleteDetails;

    /**
     * The Unix timestamp (in seconds) for when the message was completed.
     */
    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Integer completedAt;

    /**
     * The Unix timestamp (in seconds) for when the message was marked as incomplete.
     */
    @JSONField(name = "incomplete_at")
    @JsonProperty("incomplete_at")
    private Integer incompleteAt;

    /**
     * The entity that produced the message. One of user or assistant.
     * <p>
     * See {@link Message Role}
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
     * If applicable, the ID of the {@link Run} associated with the authoring of this message.
     */
    @JSONField(name = "run_id")
    @JsonProperty("run_id")
    private String runId;

    /**
     * A list of files attached to the message, and the tools they were added to.
     */
    @JSONField(name = "attachments")
    @JsonProperty("attachments")
    private List<Attachment> attachments;

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

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Attachment implements IOpenAiBean {

        /**
         * The ID of the file to attach to the message.
         */
        @JSONField(name = "file_id")
        @JsonProperty("file_id")
        private String fileId;

        @JSONField(name = "tools")
        @JsonProperty("tools")
        private List<Tool> tools;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Tool implements IOpenAiBean {

        @JSONField(name = "type")
        @JsonProperty("type")
        private AssistantTool.Type type;
    }

}