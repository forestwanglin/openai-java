package com.felh.openai.edit;

import com.felh.openai.IApiEntity;
import com.felh.openai.Usage;
import lombok.Data;

import java.util.List;

@Data
public class Edit implements IApiEntity {

    /**
     * The type of object returned
     * list or others
     */
    private String object;

    /**
     * The creation time in epoch seconds.
     */
    public long created;

    private List<EditChoice> choices;

    private Usage usage;

}
