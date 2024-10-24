package xyz.felh.openai.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.chat.tool.Tool;
import xyz.felh.openai.chat.tool.ToolChoice;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateChatCompletionRequest implements IOpenAiApiRequest {

    /**
     * ID of the model to use. See the <a href="https://platform.openai.com/docs/models/model-endpoint-compatibility">model endpoint compatibility</a> table for details on which models work with the Chat API.
     */
    @NonNull
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * A list of messages comprising the conversation so far. See <a href="https://platform.openai.com/docs/api-reference/chat/create#chat-create-model">api document</a>
     * <p>
     * See {@link ChatMessage}
     */
    @NonNull
    @JSONField(name = "messages")
    @JsonProperty("messages")
    private List<ChatMessage> messages;

    /**
     * boolean or null
     * <p>
     * Optional
     * Defaults to false
     * Whether or not to store the output of this chat completion request for use in our model distillation or evals products.
     */
    @JSONField(name = "store")
    @JsonProperty("store")
    private Boolean store;

    /**
     * Developer-defined tags and values used for filtering completions in the dashboard.
     */
    @JsonProperty("metadata")
    @JSONField(name = "metadata")
    private Object metadata;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on their existing frequency in the text so far, decreasing the model's likelihood to repeat the same line verbatim.
     * <p>
     * Number or null, Optional, Defaults to 0
     * <p>
     * <a href="https://platform.openai.com/docs/guides/text-generation/parameter-details">See more information about frequency and presence penalties.</a>
     */
    @JSONField(name = "frequency_penalty")
    @JsonProperty("frequency_penalty")
    private Double frequencyPenalty;

    /**
     * Modify the likelihood of specified tokens appearing in the completion.
     * <p>
     * Accepts a JSON object that maps tokens (specified by their token ID in the tokenizer) to an associated bias value from -100 to 100. Mathematically, the bias is added to the logits generated by the model prior to sampling. The exact effect will vary per model, but values between -1 and 1 should decrease or increase likelihood of selection; values like -100 or 100 should result in a ban or exclusive selection of the relevant token.
     * <p>
     * Map, Optional
     * Defaults to null
     */
    @JSONField(name = "logit_bias")
    @JsonProperty("logit_bias")
    private Map<String, Integer> logitBias;

    /**
     * boolean or null
     * <p>
     * Optional
     * Defaults to false
     * Whether to return log probabilities of the output tokens or not. If true, returns the log probabilities of each output token returned in the content of message.
     */
    @JSONField(name = "logprobs")
    @JsonProperty("logprobs")
    private Boolean logprobs;

    /**
     * integer or null
     * <p>
     * Optional
     * An integer between 0 and 20 specifying the number of most likely tokens to return at each token position, each with an associated log probability. logprobs must be set to true if this parameter is used.
     */
    @JSONField(name = "top_logprobs")
    @JsonProperty("top_logprobs")
    private Integer topLogprobs;

    /**
     * The maximum number of tokens to generate in the chat completion.
     * <p>
     * The total length of input tokens and generated tokens is limited by the model's context length. Example Python code for counting tokens.
     * <p>
     * integer or null, Optional
     * <p>
     * Defaults to inf
     */
    @JSONField(name = "max_tokens")
    @JsonProperty("max_tokens")
    private Integer maxTokens;

    /**
     * An upper bound for the number of tokens that can be generated for a completion, including visible output tokens and reasoning tokens.
     */
    @JSONField(name = "max_completion_tokens")
    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    /**
     * How many chat completion choices to generate for each input message.
     * <p>
     * integer or null, Optional
     * <p>
     * Defaults to 1
     */
    @JSONField(name = "n")
    @JsonProperty("n")
    private Integer n;

    /**
     * Output types that you would like the model to generate for this request. Most models are capable of generating text, which is the default:
     * <p>
     * ["text"]
     * <p>
     * The gpt-4o-audio-preview model can also be used to generate audio. To request that this model generate both text and audio responses, you can use:
     * <p>
     * ["text", "audio"]
     */
    @JSONField(name = "modalities")
    @JsonProperty("modalities")
    private List<ChatModality> modalities;

    /**
     * Parameters for audio output. Required when audio output is requested with modalities: ["audio"].
     */
    @JSONField(name = "audio")
    @JsonProperty("audio")
    private Object audio;

    /**
     * Number between -2.0 and 2.0. Positive values penalize new tokens based on whether they appear in the text so far, increasing the model's likelihood to talk about new topics.
     * <p>
     * Number or null, Optional, Defaults to 0
     * <p>
     * <a href="https://platform.openai.com/docs/guides/text-generation/parameter-details">See more information about frequency and presence penalties.</a>
     */
    @JSONField(name = "presence_penalty")
    @JsonProperty("presence_penalty")
    private Double presencePenalty;

    /**
     * An object specifying the format that the model must output. Compatible with GPT-4o, GPT-4o mini, GPT-4 Turbo and all GPT-3.5 Turbo models newer than gpt-3.5-turbo-1106.
     * <p>
     * Setting to { "type": "json_schema", "json_schema": {...} } enables Structured Outputs which guarantees the model will match your supplied JSON schema. Learn more in the Structured Outputs guide.
     * <p>
     * Setting to { "type": "json_object" } enables JSON mode, which guarantees the message the model generates is valid JSON.
     * <p>
     * Important: when using JSON mode, you must also instruct the model to produce JSON yourself via a system or user message. Without this, the model may generate an unending stream of whitespace until the generation reaches the token limit, resulting in a long-running and seemingly "stuck" request. Also note that the message content may be partially cut off if finish_reason="length", which indicates the generation exceeded max_tokens or the conversation exceeded the max context length.
     * See {@link RequestResponseFormat}
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private RequestResponseFormat responseFormat;

    /**
     * This feature is in Beta. If specified, our system will make a best effort to sample deterministically, such that repeated requests with the same seed and parameters should return the same result. Determinism is not guaranteed, and you should refer to the system_fingerprint response parameter to monitor changes in the backend.
     * <p>
     * integer or null, Optional
     */
    @JSONField(name = "seed")
    @JsonProperty("seed")
    private Integer seed;

    /**
     * string or null
     * <p>
     * Optional
     * Defaults to null
     * Specifies the latency tier to use for processing the request. This parameter is relevant for customers subscribed to the scale tier service:
     * <p>
     * If set to 'auto', the system will utilize scale tier credits until they are exhausted.
     * If set to 'default', the request will be processed using the default service tier with a lower uptime SLA and no latency guarentee.
     * When not set, the default behavior is 'auto'.
     * When this parameter is set, the response body will include the service_tier utilized.
     */
    @JSONField(name = "service_tier")
    @JsonProperty("service_tier")
    private String serviceTier;

    /**
     * Up to 4 sequences where the API will stop generating further tokens.
     * <p>
     * string / array / null
     * <p>
     * Optional Defaults to null
     */
    @JSONField(name = "stop")
    @JsonProperty("stop")
    private Object stop;

    /**
     * If set, partial message deltas will be sent, like in ChatGPT. Tokens will be sent as data-only server-sent events as they become available, with the stream terminated by a data: [DONE] message.
     * <p>
     * boolean or null, Optional
     * <p>
     * Defaults to false
     */
    @JSONField(name = "stream")
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * Options for streaming response. Only set this when you set stream: true.
     */
    @JSONField(name = "stream_options")
    @JsonProperty("stream_options")
    private StreamOptions streamOptions;

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     * <p>
     * We generally recommend altering this or {@linkplain CreateChatCompletionRequest#topP} but not both.
     * <p>
     * number or null, Optional
     * <p>
     * Defaults to 1
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * <p>
     * We generally recommend altering this or {@linkplain CreateChatCompletionRequest#temperature} but not both.
     * <p>
     * number or null, Optional
     * <p>
     * Defaults to 1
     */
    @JSONField(name = "top_p")
    @JsonProperty("top_p")
    private Double topP;

    /**
     * A list of tools the model may call. Currently, only functions are supported as a tool. Use this to provide a list of functions the model may generate JSON inputs for.
     * <p>
     * array Optional
     * <p>
     * See {@link Tool}
     */
    @JSONField(name = "tools")
    @JsonProperty("tools")
    private List<Tool> tools;

    /**
     * Controls which (if any) function is called by the model. none means the model will not call a function and instead generates a message. auto means the model can pick between generating a message or calling a function. Specifying a particular function via {"type: "function", "function": {"name": "my_function"}} forces the model to call that function.
     * <p>
     * none is the default when no functions are present. auto is the default if functions are present.
     * <p>
     * string or object, Optional
     * <p>
     * string - none means the model will not call a function and instead generates a message. auto means the model can pick between generating a message or calling a function.
     * object - Specifies a tool the model should use. Use to force the model to call a specific function.{@link ToolChoice}
     */
    @JSONField(name = "tool_choice")
    @JsonProperty("tool_choice")
    private Object toolChoice;

    /**
     * Whether to enable parallel function calling during tool use.
     * <p>
     * Optional
     * Defaults to true
     */
    @JSONField(name = "parallel_tool_calls")
    @JsonProperty("parallel_tool_calls")
    private Boolean parallelToolCalls;

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     *
     * <a href="https://platform.openai.com/docs/guides/safety-best-practices/end-user-ids">Learn more</a>
     * <p>
     * string, Optional
     */
    @JSONField(name = "user")
    @JsonProperty("user")
    private String user;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class StreamOptions implements IOpenAiBean {

        /**
         * If set, an additional chunk will be streamed before the data: [DONE] message. The usage field on this chunk shows the token usage statistics for the entire request, and the choices field will always be an empty array. All other chunks will also include a usage field, but with a null value.
         */
        @JSONField(name = "include_usage")
        @JsonProperty("include_usage")
        private Boolean includeUsage;

    }

}
