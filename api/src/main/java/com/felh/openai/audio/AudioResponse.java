package com.felh.openai.audio;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class AudioResponse implements IApiEntity {

    private String text;

}
