package xyz.felh.openai.completion;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateCompletionRequest extends BaseCreateCompletionRequest {

    /**
     * The prompt(s) to generate completions for, encoded as a string, array of strings, array of tokens,
     * or array of token arrays. Note that &lt;|endoftext|&gt; is the document separator that the model sees during training,
     * so if a prompt is not specified the model will generate as if from the beginning of a new document.
     * string or array
     * Optional Defaults to &lt;|endoftext|&gt;
     */
    private String prompt;

    /**
     * The suffix that comes after a completion of inserted text.
     * Optional Defaults to null
     */
    private String suffix;

    /**
     * Include the log probabilities on the logprobs most likely tokens, as well the chosen tokens.
     * For example, if logprobs is 5, the API will return a list of the 5 most likely tokens.
     * The API will always return the logprob of the sampled token, so there may be up to logprobs+1 elements in the response.
     * The maximum value for logprobs is 5. If you need more than this,
     * please contact us through our Help center and describe your use case.
     * Optional Defaults to null
     */
    private Integer logprobs;

    /**
     * Echo back the prompt in addition to the completion
     * Optional Defaults to false
     */
    private Boolean echo;

    /**
     * Generates best_of completions server-side and returns the "best"
     * (the one with the highest log probability per token). Results cannot be streamed.
     * When used with n, best_of controls the number of candidate completions and n specifies how many to
     * return – best_of must be greater than n.
     * Note: Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for max_tokens and stop.
     * Optional
     * Defaults to 1
     */
    private Integer bestOf;

}
