package com.felh.openai.image;

import com.felh.openai.IOpenAiApiObject;
import com.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ImageResponse implements IOpenAiApiResponse, IOpenAiApiObject {

    private Long created;

    private List<Image> data;

}
