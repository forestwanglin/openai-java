package xyz.felh.openai.chat;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    /**
     * A list of chat completion choices. Can be more than one if n is greater than 1.
     * <p>
     * See {@link ChatCompletionChoice}
     */
    private List<ChatCompletionChoice> choices;

    /**
     * The Unix timestamp (in seconds) of when the chat completion was created.
     */
    private Long created;

    /**
     * The model used for the chat completion.
     */
    private String model;

    /**
     * This fingerprint represents the backend configuration that the model runs with.
     * <p>
     * Can be used in conjunction with the {@linkplain  CreateChatCompletionRequest#getSeed()} request parameter to understand when backend changes have been made that might impact determinism.
     */
    private String systemFingerprint;

    /**
     * Usage statistics for the completion request.
     * See {@link Usage}
     */
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
         * GPT4.0
         */
        GPT_4("gpt-4"),
        /**
         * GPT4.0 超长上下文
         */
        GPT_4_32K("gpt-4-32k"),
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
        GPT_4_VISION_PREVIEW("gpt-4-vision-preview");

        private final String name;

    }

}
