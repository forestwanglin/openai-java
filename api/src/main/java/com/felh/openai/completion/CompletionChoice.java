package com.felh.openai.completion;

import com.felh.openai.IApiEntity;
import lombok.Data;

import java.util.List;

@Data
public class CompletionChoice implements IApiEntity {

    private String text;

    private Integer index;

    private Logprobs logprobs;

    private String finishReason;

}
