package xyz.felh.openai.chat.tool;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
     * <p>
     * See {@link Type}
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * function object
     */
    @JSONField(name = "function")
    @JsonProperty("function")
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
        @JSONField(name = "name")
        @JsonProperty("name")
        private String name;

    }

}
