package xyz.felh.openai.thread.message;

import lombok.Data;
import lombok.EqualsAndHashCode;
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
    private Integer createdAt;

    /**
     * The {@link xyz.felh.openai.thread.Thread} ID that this message belongs to.
     */
    private String threadId;

    /**
     * The entity that produced the message. One of user or assistant.
     * <p>
     * See {@link xyz.felh.openai.chat.ChatMessageRole}
     */
    private String role;

    /**
     * The content of the message in array of text and/or images.
     * <p>
     * See {@link MessageContent}
     */
    private List<MessageContent> content;

    /**
     * If applicable, the ID of the {@link xyz.felh.openai.assistant.Assistant} that authored this message.
     */
    private String assistantId;

    /**
     * If applicable, the ID of the {@link xyz.felh.openai.thread.run.Run} associated with the authoring of this message.
     */
    private String runId;

    /**
     * A list of file IDs that the assistant should use. Useful for tools like retrieval and code_interpreter that can access files. A maximum of 10 files can be attached to a message.
     * <p>
     * See {@link xyz.felh.openai.file.File}
     */
    private List<String> fileIds;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

}