package com.felh.openai.completion;

import com.felh.openai.OpenAiApiObjectWithId;
import com.felh.openai.Usage;
import lombok.Data;

import java.util.List;

/**
 * Given a prompt, the model will return one or more predicted completions,
 * and can also return the probabilities of alternative tokens at each position.
 */
@Data
public class Completion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<CompletionChoice> choices;

    private Usage usage;

}
