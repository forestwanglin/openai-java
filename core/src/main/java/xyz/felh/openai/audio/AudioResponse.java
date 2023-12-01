package xyz.felh.openai.audio;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

@Data
public class AudioResponse implements IOpenAiApiResponse {

    @JSONField(name = "text")
    @JsonProperty("text")
    private String text;

}
