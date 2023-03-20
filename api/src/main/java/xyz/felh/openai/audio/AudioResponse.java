package xyz.felh.openai.audio;

import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

@Data
public class AudioResponse implements IOpenAiApiResponse {

    private String text;

}
