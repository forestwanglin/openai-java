package xyz.felh.openai.chat.tool;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Function {

    /**
     * The name of the function to be called. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.
     * <p>
     * String
     */
    @NonNull
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * A description of what the function does, used by the model to choose when and how to call the function.
     * <p>
     * string, Optional
     */
    @JSONField(name = "description")
    @JsonProperty("description")
    private String description;

    /**
     * The parameters the functions accepts, described as a JSON Schema object. See the <a href="https://platform.openai.com/docs/guides/function-calling">guide</a> for examples, and the <a href="https://json-schema.org/understanding-json-schema">JSON Schema reference</a> for documentation about the format.
     * <p>
     * To describe a function that accepts no parameters, provide the value {"type": "object", "properties": {}}.
     */
    @NonNull
    @JSONField(name = "parameters")
    @JsonProperty("parameters")
    private Object parameters;

}
