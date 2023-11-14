package xyz.felh.openai.thread.run;

import lombok.Data;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.ToolCall;

import java.util.List;

@Data
public class RequiredAction implements IOpenAiBean {

    /**
     * For now, this is always submit_tool_outputs.
     */
    private String type;

    /**
     * Details on the tool outputs needed for this run to continue.
     * <p>
     * See {@link ToolOutput}
     */
    private ToolOutput submitToolOutputs;

    @Data
    public static class ToolOutput {
        /**
         * A list of the relevant tool calls.
         * <p>
         * See {@link ToolCall}
         */
        private List<ToolCall> toolCalls;
    }

}
