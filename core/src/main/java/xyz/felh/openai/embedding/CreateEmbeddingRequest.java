package xyz.felh.openai.embedding;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;
import xyz.felh.openai.chat.ChatMessage;

import java.util.Arrays;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateEmbeddingRequest implements IOpenAiApiRequest {

    /**
     * Required
     * ID of the model to use. You can use the List models API to see all of your available models, or see our Model overview for descriptions of them.
     */
    @NonNull
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * string or array Required
     * <p>
     * Input text to embed, encoded as a string or array of tokens. To embed multiple inputs in a single request, pass an array of strings or array of token arrays. The input must not exceed the max input tokens for the model (8192 tokens for text-embedding-ada-002), cannot be an empty string, and any array must be 2048 dimensions or less. Example Python code for counting tokens.
     * <p>
     * string string - The string that will be turned into an embedding.
     * <p>
     * array array - The array of strings that will be turned into an embedding. Each line's max is 8191
     * <p>
     * array array - The array of integers that will be turned into an embedding.
     * <p>
     * array array - The array of arrays containing integers that will be turned into an embedding.
     */
    @NonNull
    @JSONField(name = "input")
    @JsonProperty("input")
    private Object input;

    /**
     * string Optional Defaults to float
     * <p>
     * The format to return the embeddings in. Can be either float or base64.
     * See {@link EncodingFormat}
     */
    @JSONField(name = "encoding_format")
    @JsonProperty("encoding_format")
    private EncodingFormat encodingFormat;

    /**
     * Optional
     * <p>
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse. Learn more.
     */
    @JSONField(name = "user")
    @JsonProperty("user")
    private String user;

    @Getter
    public enum EncodingFormat {

        FLOAT("float"),
        BASE64("base64");

        private final String value;

        EncodingFormat(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static EncodingFormat findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

}
