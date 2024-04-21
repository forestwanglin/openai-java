package xyz.felh.openai.assistant.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TruncationStrategy implements IOpenAiBean {

    /**
     * The truncation strategy to use for the thread. The default is auto. If set to last_messages, the thread will be truncated to the n most recent messages in the thread. When set to auto, messages in the middle of the thread will be dropped to fit the context length of the model, max_prompt_tokens.
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * The number of most recent messages from the thread when constructing the context for the run.
     */
    @JSONField(name = "last_messages")
    @JsonProperty("last_messages")
    private Integer lastMessages;

    @Getter
    public enum Type {

        AUTO("auto"),
        LAST_MESSAGES("last_messages"),
        MAX_PROMPT_TOKENS("max_prompt_tokens");

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
