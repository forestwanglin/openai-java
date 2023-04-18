package xyz.felh.openai.completion.chat;

import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<ChatCompletionChoice> choices;

    private Usage usage;

}
