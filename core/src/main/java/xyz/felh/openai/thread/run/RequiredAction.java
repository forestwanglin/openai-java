package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.ToolCall;

import java.util.List;

@Data
public class RequiredAction implements IOpenAiBean {

    /**
     * For now, this is always submit_tool_outputs.
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private String type;

    /**
     * Details on the tool outputs needed for this run to continue.
     * <p>
     * See {@link ToolOutput}
     */
    @JSONField(name = "submit_tool_outputs")
    @JsonProperty("submit_tool_outputs")
    private ToolOutput submitToolOutputs;

    @Data
    public static class ToolOutput {
        /**
         * A list of the relevant tool calls.
         * <p>
         * See {@link ToolCall}
         */
        @JSONField(name = "tool_calls")
        @JsonProperty("tool_calls")
        private List<ToolCall> toolCalls;
    }

}
