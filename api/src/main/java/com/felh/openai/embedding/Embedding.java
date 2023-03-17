package com.felh.openai.embedding;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

import java.util.List;

@Data
public class Embedding implements IOpenAiApiObject {

    private String object;

    private List<Double> embedding;

    private Integer index;

}
