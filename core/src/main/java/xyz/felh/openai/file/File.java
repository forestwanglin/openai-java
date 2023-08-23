package xyz.felh.openai.file;

import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import lombok.Data;
import lombok.ToString;

@EqualsAndHashCode(callSuper = true)
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

    /**
     * The current status of the file, which can be either
     * uploaded, processed, pending, error, deleting or deleted.
     */
    String status;

    /**
     * Additional details about the status of the file. If the file is in the error state, this will include a message describing the error.
     */
    String statusDetails;

}
