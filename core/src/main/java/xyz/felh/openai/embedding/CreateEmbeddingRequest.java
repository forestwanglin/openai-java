package xyz.felh.openai.embedding;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateEmbeddingRequest implements IOpenAiApiRequest {

    /**
     * Required
     * ID of the model to use. You can use the List models API to see all of your available models,
     * or see our Model overview for descriptions of them.
     */
    @NonNull
    private String model;

    /**
     * string or array
     * Required
     * Input text to get embeddings for, encoded as a string or array of tokens. To get embeddings for multiple inputs
     * in a single request, pass an array of strings or array of token arrays. Each input must not exceed 8192 tokens in length.
     */
    @NonNull
    private String input;

    /**
     * Optional
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     */
    private String user;

}
