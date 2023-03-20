package xyz.felh.openai.completion;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class CompletionChoice implements IOpenAiApiObject {

    private String text;

    private Integer index;

    private Logprobs logprobs;

    private String finishReason;

}
