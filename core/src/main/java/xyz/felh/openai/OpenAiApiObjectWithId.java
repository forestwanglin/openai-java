package xyz.felh.openai;

import lombok.Data;

/**
 * An abstract class of OpenAi API Response
 */
@Data
public abstract class OpenAiApiObjectWithId implements IOpenAiApiObject {

    /**
     * A unique identifier for the chat completion.
     */
    private String id;

    /**
     * The object type, which is always {@linkplain  chat.completion}.
     */
    private String object;

}
