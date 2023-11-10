package xyz.felh.openai.chat.tool;

import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tool implements IOpenAiBean {

    /**
     * The type of the tool. Currently, only function is supported.
     */
    @NonNull
    private String type;

    /**
     * See {@link Function}
     */
    @NonNull
    private Function function;

}
