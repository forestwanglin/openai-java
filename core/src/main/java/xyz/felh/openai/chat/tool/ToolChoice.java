package xyz.felh.openai.chat.tool;

import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ToolChoice implements IOpenAiBean {

    /**
     * The type of the tool. Currently, only function is supported.
     * <p>
     * string, Optional
     */
    private String type;

    /**
     * function object
     */
    private Function function;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Function implements IOpenAiBean {

        /**
         * The name of the function to call.
         * <p>
         * string
         */
        @NonNull
        private String name;

    }

}
