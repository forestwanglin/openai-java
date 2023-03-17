package com.felh.openai.embedding;

import com.felh.openai.IApiEntity;
import lombok.Data;

import java.util.List;

@Data
public class Embedding implements IApiEntity {

    private String object;

    private List<Double> embedding;

    private Integer index;

}
