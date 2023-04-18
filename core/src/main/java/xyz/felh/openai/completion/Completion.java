package xyz.felh.openai.completion;

import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

/**
 * Given a prompt, the model will return one or more predicted completions,
 * and can also return the probabilities of alternative tokens at each position.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Completion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<CompletionChoice> choices;

    private Usage usage;

}
