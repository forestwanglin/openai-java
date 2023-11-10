package xyz.felh.openai.chat.tool;

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
    private String id;

    /**
     * The type of the tool. Currently, only function is supported.
     */
    @NonNull
    private String type;

    /**
     * The function that the model called.
     * <p>
     * See {@link FunctionCall}
     */
    @NonNull
    private FunctionCall function;

}
