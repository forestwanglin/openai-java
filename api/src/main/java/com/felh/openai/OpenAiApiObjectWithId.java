package com.felh.openai;

import lombok.Data;

/**
 * An abstract class of OpenAi API Response
 */
@Data
public abstract class OpenAiApiObjectWithId implements IOpenAiApiObject {

    /**
     * An identifier for this model, used to specify the model when making completions, etc
     */
    private String id;

    /**
     * The type of object returned
     */
    private String object;

}
