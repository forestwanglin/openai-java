package com.felh.openai.edit;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class EditChoice implements IOpenAiApiObject {

    private String text;

    private Integer index;

}
