package xyz.felh.openai.image;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class ImageResponse implements IOpenAiApiResponse, IOpenAiApiObject {

    @JSONField(name = "created")
    @JsonProperty("created")
    private Long created;

    @JSONField(name = "data")
    @JsonProperty("data")
    private List<Image> data;

}
