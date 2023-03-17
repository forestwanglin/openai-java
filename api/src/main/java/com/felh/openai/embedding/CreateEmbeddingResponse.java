package com.felh.openai.embedding;

import com.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class CreateEmbeddingResponse {

    private String model;

    private String object;

    private List<Embedding> data;

    private Usage usage;

}
