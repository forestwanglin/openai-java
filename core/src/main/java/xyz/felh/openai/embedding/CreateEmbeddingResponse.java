package xyz.felh.openai.embedding;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiResponse;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class CreateEmbeddingResponse implements IOpenAiApiResponse {

    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    @JSONField(name = "object")
    @JsonProperty("object")
    private String object;

    /**
     * See {@link Embedding}
     */
    @JSONField(name = "data")
    @JsonProperty("data")
    private List<Embedding> data;

    /**
     * See {@link Usage}
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

}
