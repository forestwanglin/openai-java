package xyz.felh.openai.embedding;

import xyz.felh.openai.IOpenAiApiResponse;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class CreateEmbeddingResponse implements IOpenAiApiResponse {

    private String model;

    private String object;

    private List<Embedding> data;

    private Usage usage;

}
