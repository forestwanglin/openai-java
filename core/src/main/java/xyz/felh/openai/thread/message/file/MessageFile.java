package xyz.felh.openai.thread.message.file;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
public class MessageFile extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.message.file";

    /**
     * The Unix timestamp (in seconds) for when the message file was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The ID of the {@link xyz.felh.openai.thread.message.Message} that the {@link xyz.felh.openai.file.File} is attached to.
     */
    @JSONField(name = "message_id")
    @JsonProperty("message_id")
    private String messageId;

}