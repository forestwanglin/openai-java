package xyz.felh.openai.completion.chat;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ChatCompletionChoice implements IOpenAiApiObject {

    private Integer index;

    private ChatMessage message;

    // stream = true
    private ChatMessage delta;

    /**
     * stop: API returned complete message, or a message terminated by one of the stop sequences provided via the stop parameter
     * length: Incomplete model output due to max_tokens parameter or token limit
     * tool_calls: The model called a tool
     * content_filter: Omitted content due to a flag from our content filters
     * null: API response still in progress or incomplete
     */
    private String finishReason;

}