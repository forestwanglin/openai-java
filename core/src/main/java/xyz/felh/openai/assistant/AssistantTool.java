package xyz.felh.openai.assistant;

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
     * The type of tool being defined: code_interpreter, retrieval, function
     */
    private String type;

    /**
     * When type is function
     * <p>
     * See {@link Function}
     */
    private Function function;

    @Getter
    public enum Type {

        CODE_INTERPRETER("code_interpreter"),
        RETRIEVAL("retrieval"),
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
