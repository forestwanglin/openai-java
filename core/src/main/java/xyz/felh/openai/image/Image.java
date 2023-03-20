package xyz.felh.openai.image;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Image implements IOpenAiApiObject {

    private String url;

    private String b64Json;

}
