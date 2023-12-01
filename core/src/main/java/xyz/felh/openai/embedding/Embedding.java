package xyz.felh.openai.embedding;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

import java.util.List;

/**
 * See <a href="https://platform.openai.com/docs/api-reference/embeddings">document</a>
 */
@Data
public class Embedding implements IOpenAiApiObject {

    public static String OBJECT = "embedding";

    /**
     * The object type, which is always "embedding".
     */
    @JSONField(name = "object")
    @JsonProperty("object")
    private String object;

    /**
     * The embedding vector, which is a list of floats. The length of vector depends on the model as listed in the <a href="https://platform.openai.com/docs/guides/embeddings">embedding guide</a>.
     */
    @JSONField(name = "embedding")
    @JsonProperty("embedding")
    private List<Double> embedding;

    /**
     * The index of the embedding in the list of embeddings.
     */
    @JSONField(name = "index")
    @JsonProperty("index")
    private Integer index;

}
