package com.felh.openai.completion.chat;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ChatCompletionChoice implements IOpenAiApiObject {

    private Integer index;

    private ChatMessage message;

    private String finishReason;

}
