package xyz.felh.openai.completion.chat.tool;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.completion.chat.func.FunctionCall;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolCall {

    private String id;

    /**
     * The type of the tool. Currently, only function is supported.
     */
    private String type;

    private FunctionCall function;

}
