package xyz.felh.openai.jtokkit.api;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ModelType {

    // chat
    GPT_4_0613("gpt-4-0613", EncodingType.CL100K_BASE, 8192,8192),

    GPT_4_O_2024_08_06("gpt-4o-2024-08-06", EncodingType.O200K_BASE, 128000, 16384),
    GPT_4_O_MINI_2024_07_18("gpt-4o-mini-2024-07-18", EncodingType.O200K_BASE, 128000, 16384),

    GPT_4_TURBO_2024_04_09("gpt-4-turbo-2024-04-09", EncodingType.CL100K_BASE, 128000, 4096),
    GPT_3_5_TURBO_0125("gpt-3.5-turbo-0125", EncodingType.CL100K_BASE, 16385, 4096),

    O1_PREVIEW_20240912("o1-preview-2024-09-12", EncodingType.O200K_BASE, 128000, 32768),
    O1_MINI_20240912("o1-mini-2024-09-12", EncodingType.O200K_BASE, 128000, 65536),

    GPT_4_O_REALTIME_PREVIEW_20241001("gpt-4o-realtime-preview-2024-10-01", EncodingType.O200K_BASE, 128000, 4096),
    GPT_4_O_AUDIO_PREVIEW_20241001("gpt-4o-audio-preview-2024-10-01", EncodingType.O200K_BASE, 128000, 16384),

    // text
    TEXT_DAVINCI_003("text-davinci-003", EncodingType.P50K_BASE, 4097),
    TEXT_DAVINCI_002("text-davinci-002", EncodingType.P50K_BASE, 4097),
    TEXT_DAVINCI_001("text-davinci-001", EncodingType.R50K_BASE, 2049),
    TEXT_CURIE_001("text-curie-001", EncodingType.R50K_BASE, 2049),
    TEXT_BABBAGE_001("text-babbage-001", EncodingType.R50K_BASE, 2049),
    TEXT_ADA_001("text-ada-001", EncodingType.R50K_BASE, 2049),
    DAVINCI("davinci", EncodingType.R50K_BASE, 2049),
    CURIE("curie", EncodingType.R50K_BASE, 2049),
    BABBAGE("babbage", EncodingType.R50K_BASE, 2049),
    ADA("ada", EncodingType.R50K_BASE, 2049),

    // code
    CODE_DAVINCI_002("code-davinci-002", EncodingType.P50K_BASE, 8001),
    CODE_DAVINCI_001("code-davinci-001", EncodingType.P50K_BASE, 8001),
    CODE_CUSHMAN_002("code-cushman-002", EncodingType.P50K_BASE, 2048),
    CODE_CUSHMAN_001("code-cushman-001", EncodingType.P50K_BASE, 2048),
    DAVINCI_CODEX("davinci-codex", EncodingType.P50K_BASE, 4096),
    CUSHMAN_CODEX("cushman-codex", EncodingType.P50K_BASE, 2048),

    // edit
    TEXT_DAVINCI_EDIT_001("text-davinci-edit-001", EncodingType.P50K_EDIT, 3000),
    CODE_DAVINCI_EDIT_001("code-davinci-edit-001", EncodingType.P50K_EDIT, 3000),

    // embeddings
    TEXT_EMBEDDING_ADA_002("text-embedding-ada-002", EncodingType.CL100K_BASE, 8191),  // $0.00010 / 1K tokens
    TEXT_EMBEDDING_3_SMALL("text-embedding-3-small", EncodingType.CL100K_BASE, 8191), // 1536 DIMENSION 20240125 $0.00002 / 1K tokens
    TEXT_EMBEDDING_3_LARGE("text-embedding-3-large", EncodingType.CL100K_BASE, 8191), // 3072 DIMENSION 20240125 $0.00013 / 1K tokens

    // old embeddings
    TEXT_SIMILARITY_DAVINCI_001("text-similarity-davinci-001", EncodingType.R50K_BASE, 2046),
    TEXT_SIMILARITY_CURIE_001("text-similarity-curie-001", EncodingType.R50K_BASE, 2046),
    TEXT_SIMILARITY_BABBAGE_001("text-similarity-babbage-001", EncodingType.R50K_BASE, 2046),
    TEXT_SIMILARITY_ADA_001("text-similarity-ada-001", EncodingType.R50K_BASE, 2046),
    TEXT_SEARCH_DAVINCI_DOC_001("text-search-davinci-doc-001", EncodingType.R50K_BASE, 2046),
    TEXT_SEARCH_CURIE_DOC_001("text-search-curie-doc-001", EncodingType.R50K_BASE, 2046),
    TEXT_SEARCH_BABBAGE_DOC_001("text-search-babbage-doc-001", EncodingType.R50K_BASE, 2046),
    TEXT_SEARCH_ADA_DOC_001("text-search-ada-doc-001", EncodingType.R50K_BASE, 2046),
    CODE_SEARCH_BABBAGE_CODE_001("code-search-babbage-code-001", EncodingType.R50K_BASE, 2046),
    CODE_SEARCH_ADA_CODE_001("code-search-ada-code-001", EncodingType.R50K_BASE, 2046);

    private static final Map<String, ModelType> nameToModelType = Arrays.stream(values())
            .collect(Collectors.toMap(ModelType::getName, Function.identity()));

    /**
     * -- GETTER --
     * Returns the name of the model type as used by the OpenAI API.
     *
     * @return the name of the model type
     */
    private final String name;
    /**
     * -- GETTER --
     * Returns the encoding type that is used by this model type.
     *
     * @return the encoding type
     */
    private final EncodingType encodingType;
    /**
     * -- GETTER --
     * Returns the maximum context length that is supported by this model type. Note that
     * the maximum context length consists of the amount of prompt tokens and the amount of
     * completion tokens (where applicable).
     *
     * @return the maximum context length for this model type
     */
    private final int maxContextLength;

    private final int maxOutputLength;

    ModelType(
            final String name,
            final EncodingType encodingType,
            final int maxContextLength
    ) {
        this(name, encodingType, maxContextLength, 0);
    }

    ModelType(
            final String name,
            final EncodingType encodingType,
            final int maxContextLength,
            final int maxOutputLength
    ) {
        this.name = name;
        this.encodingType = encodingType;
        this.maxContextLength = maxContextLength;
        this.maxOutputLength = maxOutputLength;
    }

    /**
     * Returns a {@link ModelType} for the given name, or {@link Optional#empty()} if no
     * such model type exists.
     *
     * @param name the name of the model type
     * @return the model type or {@link Optional#empty()}
     */
    public static Optional<ModelType> fromName(final String name) {
        return Optional.ofNullable(nameToModelType.get(name));
    }

}
