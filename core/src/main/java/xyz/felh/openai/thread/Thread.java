package xyz.felh.openai.thread;

import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Thread extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread";

    /**
     * The Unix timestamp (in seconds) for when the thread was created.
     */
    private Integer createdAt;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

}