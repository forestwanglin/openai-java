package xyz.felh.openai.thread.run.step;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;

@Data
public class StepDetails implements IOpenAiBean {

    /**
     * message_creation, tool_calls
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private String type;

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
}
