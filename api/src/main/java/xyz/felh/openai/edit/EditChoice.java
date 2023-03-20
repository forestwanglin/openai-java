package xyz.felh.openai.edit;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class EditChoice implements IOpenAiApiObject {

    private String text;

    private Integer index;

}
