package xyz.felh.openai.assistant.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.ToolCall;

import java.util.List;

@Data
public class RequiredAction implements IOpenAiBean {

    /**
     * For now, this is always submit_tool_outputs.
     * See {@link Type}
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

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

    @Getter
    public enum Type {

        SUBMIT_TOOL_OUTPUTS("submit_tool_outputs");

        Type(final String value) {
            this.value = value;
        }

        private final String value;

        @JsonValue
        public String value() {
            return value;
        }

    }

}
