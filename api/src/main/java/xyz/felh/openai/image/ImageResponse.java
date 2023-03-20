package xyz.felh.openai.image;

import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ImageResponse implements IOpenAiApiResponse, IOpenAiApiObject {

    private Long created;

    private List<Image> data;

}
