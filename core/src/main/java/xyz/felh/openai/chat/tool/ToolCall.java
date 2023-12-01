package xyz.felh.openai.chat.tool;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolCall implements IOpenAiBean {

    /**
     * The ID of the tool call.
     */
    @NonNull
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * The type of the tool. Currently, only function is supported.
     * <p>
     * See {@link Type}
     */
    @NonNull
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * The function that the model called.
     * <p>
     * See {@link FunctionCall}
     */
    @NonNull
    @JSONField(name = "function")
    @JsonProperty("function")
    private FunctionCall function;

}
