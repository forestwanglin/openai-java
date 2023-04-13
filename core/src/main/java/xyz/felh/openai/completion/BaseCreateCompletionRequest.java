package xyz.felh.openai.completion;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.Map;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor(force = true)
//@Builder
@SuperBuilder(toBuilder = true)
public abstract class BaseCreateCompletionRequest implements IOpenAiApiRequest {

    /**
     * ID of the model to use. Currently, only gpt-3.5-turbo and gpt-3.5-turbo-0301 are supported.
     */
    @NonNull
    private String model;

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic.
     * We generally recommend altering this or top_p but not both.
     * Optional     Defaults to 1
     */
    private Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of
     * the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * We generally recommend altering this or temperature but not both.
     * Optional Defaults to 1
     */
    private Double topP;


    /**
     * How many completions to generate for each prompt.
     * Note: Because this parameter generates many completions, it can quickly consume your token quota.
     * Use carefully and ensure that you have reasonable settings for max_tokens and stop.
     * Optional  Defaults to 1
     */
    private Integer n;

    /**
     * Whether to stream back partial progress. If set, tokens will be sent as data-only server-sent events
     * as they become available, with the stream terminated by a data: [DONE] message.
     * Optional Defaults to false
     */
    private Boolean stream;

    /**
     * Up to 4 sequences where the API will stop generating further tokens. The returned text will not contain the stop sequence.
     * string or array
     * Optional Defaults to null
     */
    private String stop;

    /**
     * The maximum number of tokens to generate in the completion.
     * The token count of your prompt plus max_tokens cannot exceed the model's context length. Most models have
     * a context length of 2048 tokens (except for the newest models, which support 4096).
     * Optional     Defaults to 16
     */
    private Integer maxTokens;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far,
     * increasing the model's likelihood to talk about new topics.
     * See more information about frequency and presence penalties.
     * Optional
     * Defaults to 0
     */
    private Double presencePenalty;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far,
     * decreasing the model's likelihood to repeat the same line verbatim.
     * See more information about frequency and presence penalties.
     * Optional
     * Defaults to 0
     */
    private Double frequencyPenalty;

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     * Accepts a json object that maps tokens (specified by their token ID in the GPT tokenizer) to an associated
     * bias value from -100 to 100. You can use this tokenizer tool (which works for both GPT-2 and GPT-3) to
     * convert text to token IDs. Mathematically, the bias is added to the logits generated by the model prior to sampling.
     * The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection;
     * values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
     * As an example, you can pass {"50256": -100} to prevent the &lt;|endoftext|&gt; token from being generated.
     * <p>
     * Optional
     * Defaults to null
     */
    private Map<String, Integer> logitBias;

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. Learn more.
     * Optional
     */
    private String user;

}
