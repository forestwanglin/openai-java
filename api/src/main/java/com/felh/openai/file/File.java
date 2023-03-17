package com.felh.openai.file;

import com.felh.openai.OpenAiApiObjectWithId;
import lombok.Data;
import lombok.ToString;

@Data
@ToString(callSuper = true)
public class File extends OpenAiApiObjectWithId {

    /**
     * File size in bytes.
     */
    Long bytes;

    /**
     * The creation time in epoch seconds.
     */
    Long createdAt;

    /**
     * The name of the file.
     */
    String filename;

    /**
     * Description of the file's purpose.
     */
    String purpose;

}
