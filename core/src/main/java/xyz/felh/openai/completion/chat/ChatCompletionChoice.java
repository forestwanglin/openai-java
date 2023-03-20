package xyz.felh.openai.completion.chat;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ChatCompletionChoice implements IOpenAiApiObject {

    private Integer index;

    private ChatMessage message;

    private String finishReason;

}
