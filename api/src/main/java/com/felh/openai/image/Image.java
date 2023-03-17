package com.felh.openai.image;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Image implements IOpenAiApiObject {

    private String url;

    private String b64Json;

}
