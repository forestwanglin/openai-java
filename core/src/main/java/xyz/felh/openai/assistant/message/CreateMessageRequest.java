package xyz.felh.openai.assistant.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateMessageRequest implements IOpenAiApiRequest {

    /**
     * The role of the entity that is creating the message. Currently only user is supported.
     */
    @NonNull
    @JSONField(name = "role")
    @JsonProperty("role")
    private Message.Role role;

    /**
     * The content of the message.
     */
    @NonNull
    @JSONField(name = "content")
    @JsonProperty("content")
    private String content;

    /**
     * A list of files attached to the message, and the tools they were added to.
     */
    @JSONField(name = "attachments")
    @JsonProperty("attachments")
    private List<Message.Attachment> attachments;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

}