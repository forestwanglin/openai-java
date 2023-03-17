package com.felh.openai.completion.chat;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class ChatCompletionChoice implements IApiEntity {

    private Integer index;

    private ChatMessage message;

    private String finishReason;

}
