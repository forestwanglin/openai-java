package com.felh.openai.audio;

import com.felh.openai.IOpenAiApiObject;
import com.felh.openai.IOpenAiApiResponse;
import lombok.Data;

@Data
public class AudioResponse implements IOpenAiApiResponse {

    private String text;

}
