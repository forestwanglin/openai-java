package xyz.felh.openai.assistant;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.Function;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistantTool implements IOpenAiBean {

    /**
     * The type of tool being defined: code_interpreter, file_search, function
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * When type is function
     * <p>
     * See {@link Function}
     */
    @JSONField(name = "function")
    @JsonProperty("function")
    private Function function;

    @Getter
    public enum Type {

        CODE_INTERPRETER("code_interpreter"),
        FILE_SEARCH("file_search"),
        FUNCTION("function");

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
