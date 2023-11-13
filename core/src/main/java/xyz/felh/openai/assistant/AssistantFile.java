package xyz.felh.openai.assistant;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

/**
 * BETA version
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssistantFile extends OpenAiApiObjectWithId {

    public static String OBJECT = "assistant.file";

    /**
     * The Unix timestamp (in seconds) for when the assistant was created.
     */
    private Integer createdAt;

    /**
     * The assistant ID that the file is attached to.
     */
    private String assistantId;

}