package com.felh.openai.image;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class Image implements IApiEntity {

    private String url;

    private String b64Json;

}
