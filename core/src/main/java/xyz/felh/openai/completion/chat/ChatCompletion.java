package xyz.felh.openai.completion.chat;

import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<ChatCompletionChoice> choices;

    private Usage usage;

}
