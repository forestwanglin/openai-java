package com.felh.openai.completion;

import com.felh.openai.IApiEntity;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Logprobs implements IApiEntity {

    private List<String> tokens;
    private List<Double> tokenLogprobs;
    private List<Map<String, Double>> topLogprobs;
    private List<Integer> textOffset;

}
