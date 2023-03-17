package com.felh.openai.file;

import lombok.Data;

@Data
public class DeleteFileResponse {
    /**
     * The id of the object.
     */
    String id;

    /**
     * The type of object deleted, for example "file" or "model"
     */
    String object;

    /**
     * True if successfully deleted
     */
    boolean deleted;
}
