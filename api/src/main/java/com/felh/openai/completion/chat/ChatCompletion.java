package com.felh.openai.completion.chat;

import com.felh.openai.OpenAiApiObjectWithId;
import com.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<ChatCompletionChoice> choices;

    private Usage usage;

}
