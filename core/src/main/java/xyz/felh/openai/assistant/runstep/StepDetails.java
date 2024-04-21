package xyz.felh.openai.assistant.runstep;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.Getter;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.assistant.message.MessageContent;

import java.util.Arrays;
import java.util.List;

@Data
public class StepDetails implements IOpenAiBean {

    /**
     * message_creation, tool_calls
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private Type type;

    /**
     * Details of the message creation by the run step.
     */
    @JSONField(name = "message_creation")
    @JsonProperty("message_creation")
    private MessageCreation messageCreation;

    /**
     * An array of tool calls the run step was involved in. These can be associated with one of three types of tools: code_interpreter, retrieval, or function.
     * <p>
     * See {@link StepToolCall}
     */
    @JSONField(name = "tool_calls")
    @JsonProperty("tool_calls")
    private List<StepToolCall> toolCalls;

    @Data
    public static class MessageCreation implements IOpenAiBean {
        /**
         * The ID of the message that was created by this run step.
         */
        @JSONField(name = "message_id")
        @JsonProperty("message_id")
        private String messageId;
    }

    @Getter
    public enum Type {

        MESSAGE_CREATION("message_creation"),
        TOOL_CALLS("tool_calls");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Type findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }

    }
}
