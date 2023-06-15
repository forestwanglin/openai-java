package xyz.felh.openai.completion.chat.func;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * support from gpt-3.5-turbo-0613 and gpt-4-0613 (2023)
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Function {

    /**
     * Required
     * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.
     */
    private String name;

    /**
     * Optional
     * The description of what the function does.
     */
    private String description;

    /**
     * Optional
     * The parameters the functions accepts, described as a JSON Schema object. See the guide for examples, and the JSON Schema reference for documentation about the format.
     */
    private Object parameters;

}
