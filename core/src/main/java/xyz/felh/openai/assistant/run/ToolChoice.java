package xyz.felh.openai.assistant.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.chat.tool.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolChoice implements IOpenAiBean {

    /**
     * The type of the tool. If type is function, the function name must be set
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private AssistantTool.Type type;

    /**
     * When type is function
     * <p>
     * See {@link Function}
     */
    @JSONField(name = "function")
    @JsonProperty("function")
    private Function function;

}
