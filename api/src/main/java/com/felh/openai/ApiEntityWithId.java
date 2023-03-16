package com.felh.openai;

import lombok.Data;

/**
 * An abstract class of OpenAi API Response
 */
@Data
public abstract class ApiEntityWithId implements IApiEntity {

    /**
     * An identifier for this model, used to specify the model when making completions, etc
     */
    private String id;

    /**
     * The type of object returned
     * list or others
     */
    private String object;

}
