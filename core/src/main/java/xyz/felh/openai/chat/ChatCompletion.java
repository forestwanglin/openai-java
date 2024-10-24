package xyz.felh.openai.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;

import java.util.List;

/**
 * The chat completion object
 * <p>
 * Represents a chat completion response returned by model, based on the provided input.
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    public static String OBJECT = "chat.completion";
    public static String CHUNK_OBJECT = "chat.completion.chunk";

    /**
     * A list of chat completion choices. Can be more than one if n is greater than 1.
     * <p>
     * See {@link ChatCompletionChoice}
     */
    @JSONField(name = "choices")
    @JsonProperty("choices")
    private List<ChatCompletionChoice> choices;

    /**
     * The Unix timestamp (in seconds) of when the chat completion was created.
     */
    @JSONField(name = "created")
    @JsonProperty("created")
    private Long created;

    /**
     * The model used for the chat completion.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * The service tier used for processing the request. This field is only included if the service_tier parameter is specified in the request.
     */
    @JSONField(name = "service_tier")
    @JsonProperty("service_tier")
    private String serviceTier;

    /**
     * This fingerprint represents the backend configuration that the model runs with.
     * <p>
     * Can be used in conjunction with the {@linkplain  CreateChatCompletionRequest#getSeed()} request parameter to understand when backend changes have been made that might impact determinism.
     */
    @JSONField(name = "system_fingerprint")
    @JsonProperty("system_fingerprint")
    private String systemFingerprint;

    /**
     * Usage statistics for the completion request.
     * See {@link Usage}
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

    @Getter
    @AllArgsConstructor
    public enum Model {
        /**
         * 4,096 tokens
         * gpt-3.5-turbo
         */
        GPT_3_5_TURBO("gpt-3.5-turbo"),
        /**
         * 临时模型，不建议使用
         * Updated GPT 3.5 TurboNew
         * The latest GPT-3.5 Turbo model with improved instruction following, JSON mode, reproducible outputs,
         * parallel function calling, and more. Returns a maximum of 4,096 output tokens.
         */
        GPT_3_5_TURBO_1106("gpt-3.5-turbo-1106"),
        /**
         * 4,096 tokens
         * Similar capabilities as text-davinci-003 but compatible with legacy Completions endpoint and not Chat Completions.
         */
        GPT_3_5_TURBO_INSTRUCT("gpt-3.5-turbo-instruct"),

        /**
         * The latest GPT-3.5 Turbo model with higher accuracy at responding in requested formats and a fix for a bug
         * which caused a text encoding issue for non-English language function calls. Returns a maximum of 4,096 output tokens.
         */
        GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125"),

        /**
         * GPT4.0
         */
        GPT_4_0613("gpt-4-0613"),

        /**
         * GPT-4 TurboNew
         * The latest GPT-4 model with improved instruction following, JSON mode, reproducible outputs,
         * parallel function calling, and more. Returns a maximum of 4,096 output tokens.
         * This preview model is not yet suited for production traffic.
         * 128,000 tokens
         */
        GPT_4_1106_PREVIEW("gpt-4-1106-preview"),
        /**
         * GPT-4 Turbo with visionNew
         * Ability to understand images, in addition to all other GPT-4 Turbo capabilties.
         * Returns a maximum of 4,096 output tokens. This is a preview model version and not suited yet for production traffic.
         * 128,000 tokens
         */
        GPT_4_VISION_PREVIEW("gpt-4-vision-preview"),
        /**
         * GPT-4 Turbo
         * The latest GPT-4 model intended to reduce cases of “laziness” where the model doesn’t complete a task.
         */
        GPT_4_0125_PREVIEW("gpt-4-0125-preview"),

        /**
         * The previous set of high-intelligence models
         */
        GPT_4_TURBO_20240409("gpt-4-turbo-2024-04-09"),

        /**
         * The fastest and most affordable flagship model
         */
        GPT_4_O_20240806("gpt-4o-2024-08-06"),

        /**
         * The fastest and most affordable flagship model
         */
        GPT_4_O_MINI_20240718("gpt-4o-mini-2024-07-18"),

        /**
         * The o1 series of large language models are trained with reinforcement learning to perform complex reasoning.
         * o1 models think before they answer, producing a long internal chain of thought before responding to the user.
         */
        O1_PREVIEW_20240912("o1-preview-2024-09-12"),
        O1_MINI_20240912("o1-mini-2024-09-12"),

        /**
         * This is a preview release of the GPT-4o Realtime and Audio models.
         * The gpt-4o-realtime-* models are capable of responding to audio and text inputs over a WebSocket interface.
         * Learn more in the Realtime API guide. The gpt-4o-audio-* models below can be used in Chat Completions to generate audio responses.
         */
        GPT_4_O_REALTIME_PREVIEW_20241001("gpt-4o-realtime-preview-2024-10-01"),
        GPT_4_O_AUDIO_PREVIEW_20241001("gpt-4o-audio-preview-2024-10-01"),

        ;

        private final String name;

    }

    public boolean isChunked() {
        return CHUNK_OBJECT.equals(getObject());
    }

}
