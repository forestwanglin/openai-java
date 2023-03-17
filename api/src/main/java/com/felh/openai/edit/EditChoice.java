package com.felh.openai.edit;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class EditChoice implements IApiEntity {

    private String text;

    private Integer index;

}
