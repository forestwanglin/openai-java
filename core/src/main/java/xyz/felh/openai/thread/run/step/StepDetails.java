package xyz.felh.openai.thread.run.step;

import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;

@Data
public class StepDetails implements IOpenAiBean {

    /**
     * message_creation, tool_calls
     */
    private String type;

    /**
     * Details of the message creation by the run step.
     */
    private MessageCreation messageCreation;

    /**
     * An array of tool calls the run step was involved in. These can be associated with one of three types of tools: code_interpreter, retrieval, or function.
     * <p>
     * See {@link StepToolCall}
     */
    private List<StepToolCall> toolCalls;

    @Data
    public static class MessageCreation implements IOpenAiBean {
        /**
         * The ID of the message that was created by this run step.
         */
        private String messageId;
    }
}
