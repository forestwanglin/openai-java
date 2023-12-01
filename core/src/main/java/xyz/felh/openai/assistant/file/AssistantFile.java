package xyz.felh.openai.assistant.file;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

/**
 * BETA version
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class AssistantFile extends OpenAiApiObjectWithId {

    public static String OBJECT = "assistant.file";

    /**
     * The Unix timestamp (in seconds) for when the assistant was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The assistant ID that the file is attached to.
     */
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

}