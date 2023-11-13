package xyz.felh.openai.embedding;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

import java.util.List;

@Data
public class Embedding implements IOpenAiApiObject {

    public static String OBJECT = "embedding";

    private String object;

    private List<Double> embedding;

    private Integer index;

}
