package xyz.felh.openai.embedding;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

import java.util.List;

/**
 * See <a href="https://platform.openai.com/docs/api-reference/embeddings">document</a>
 */
@Data
public class Embedding implements IOpenAiApiObject {

    public static String OBJECT = "embedding";

    private String object;

    /**
     * The embedding vector, which is a list of floats. The length of vector depends on the model as listed in the <a href="https://platform.openai.com/docs/guides/embeddings">embedding guide</a>.
     */
    private List<Double> embedding;

    /**
     * The index of the embedding in the list of embeddings.
     */
    private Integer index;

}
