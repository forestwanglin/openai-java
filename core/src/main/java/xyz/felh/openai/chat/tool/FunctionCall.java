package xyz.felh.openai.chat.tool;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionCall implements IOpenAiBean {

    /**
     * The name of the function to call.
     */
    @NonNull
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * The arguments to call the function with, as generated by the model in JSON format. Note that the model does not always generate valid JSON, and may hallucinate parameters not defined by your function schema. Validate the arguments in your code before calling your function.
     */
    @NonNull
    @JSONField(name = "arguments")
    @JsonProperty("arguments")
    private String arguments;

}
