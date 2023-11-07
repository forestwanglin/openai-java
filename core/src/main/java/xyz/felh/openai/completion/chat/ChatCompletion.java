package xyz.felh.openai.completion.chat;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    private Long created;

    private String model;

    private List<ChatCompletionChoice> choices;

    /**
     * This fingerprint represents the backend configuration that the model runs with.
     * Can be used in conjunction with the seed request parameter to understand when backend changes have been made that might impact determinism.
     */
    private String systemFingerprint;

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
         * 临时模型，不建议使用
         * Snapshot of gpt-3.5-turbo from March 1st 2023. Will be deprecated on June 13th 2024.
         */
        GPT_3_5_TURBO_0301("gpt-3.5-turbo-0301"),
        /**
         * 临时模型，不建议使用
         * Snapshot of gpt-3.5-turbo from June 13th 2023. Will be deprecated on June 13, 2024.
         */
        GPT_3_5_TURBO_0613("gpt-3.5-turbo-0613"),
        /**
         * gpt-3.5-turbo-16k
         * Currently points to gpt-3.5-turbo-0613. Will point to gpt-3.5-turbo-1106 starting Dec 11, 2023.
         * 2023-12-11之后会被GPT_3_5_TURBO取代
         */
        GPT_3_5_TURBO_16K("gpt-3.5-turbo-16k"),
        /**
         * 临时模型，不建议使用
         * Snapshot of gpt-3.5-16k-turbo from June 13th 2023. Will be deprecated on June 13, 2024.
         */
        GPT_3_5_TURBO_16K_0613("gpt-3.5-turbo-16k-0613"),
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
         * 临时模型，不建议使用
         * Snapshot of gpt-4 from March 14th 2023 with function calling support.
         * This model version will be deprecated on June 13th 2024.
         */
        GPT_4_0314("gpt-4-0314"),
        /**
         * 临时模型，不建议使用
         * Snapshot of gpt-4 from June 13th 2023 with improved function calling support.
         */
        GPT_4_0613("gpt-4-0613"),

        /**
         * GPT4.0 超长上下文
         */
        GPT_4_32K("gpt-4-32k"),
        /**
         * 临时模型，不建议使用
         * Snapshot of gpt-4-32k from March 14th 2023 with function calling support.
         * This model version will be deprecated on June 13th 2024.
         */
        GPT_4_32K_0314("gpt-4-32k-0314"),
        /**
         * 临时模型，不建议使用
         * Snapshot of gpt-4-32k from June 13th 2023 with improved function calling support.
         */
        GPT_4_32K_0613("gpt-4-32k-0613"),

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
