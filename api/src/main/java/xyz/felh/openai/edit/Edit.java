package xyz.felh.openai.edit;

import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class Edit implements IOpenAiApiObject {

    /**
     * The type of object returned, always "edit"
     */
    private String object;

    /**
     * The creation time in epoch seconds.
     */
    public Long created;

    private List<EditChoice> choices;

    private Usage usage;

}
