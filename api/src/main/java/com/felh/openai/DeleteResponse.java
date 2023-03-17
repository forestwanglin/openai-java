package com.felh.openai;

import lombok.Data;

@Data
public class DeleteResponse implements IOpenAiApiResponse {

    /**
     * The id of the object.
     */
    private String id;

    /**
     * The type of object deleted, for example "file" or "model"
     */
    private String object;

    /**
     * True if successfully deleted
     */
    private boolean deleted;

}
