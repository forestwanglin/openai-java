package xyz.felh.openai.chat.tool;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Tool implements IOpenAiBean {

    /**
     * The type of the tool. Currently, only function is supported.
     *
     * See {@link Type}
     */
    @NonNull
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * See {@link Function}
     */
    @NonNull
    @JSONField(name = "function")
    @JsonProperty("function")
    private Function function;

}
