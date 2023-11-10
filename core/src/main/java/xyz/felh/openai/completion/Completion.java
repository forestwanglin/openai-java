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

    /**
     * The Unix timestamp (in seconds) of when the completion was created.
     */
    private Long created;

    /**
     * The model used for completion.
     */
    private String model;

    /**
     * The list of completion choices the model generated for the input prompt.
     */
    private List<CompletionChoice> choices;

    /**
     * Usage statistics for the completion request.
     */
    private Usage usage;

}
