package xyz.felh.openai.file;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class File extends OpenAiApiObjectWithId {

    public static String OBJECT = "file";

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
     * one of ['fine-tune', 'assistants']
     * assistants -  Supported formats: ['c', 'cpp', 'csv', 'docx', 'html', 'java', 'json', 'md', 'pdf', 'php', 'pptx', 'py', 'rb', 'tex', 'txt', 'css', 'jpeg', 'jpg', 'js', 'gif', 'png', 'tar', 'ts', 'xlsx', 'xml', 'zip']
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
