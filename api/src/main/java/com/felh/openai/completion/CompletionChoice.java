package com.felh.openai.completion;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class CompletionChoice implements IOpenAiApiObject {

    private String text;

    private Integer index;

    private Logprobs logprobs;

    private String finishReason;

}
