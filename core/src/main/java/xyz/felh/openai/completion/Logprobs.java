package xyz.felh.openai.completion;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class Logprobs implements IOpenAiApiObject {

    private List<String> tokens;

    private List<Double> tokenLogprobs;

    private List<Map<String, Double>> topLogprobs;

    private List<Integer> textOffset;

}
