package xyz.felh.openai.thread.message;

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
    private String role;

    /**
     * The content of the message.
     */
    @JSONField(name = "content")
    @JsonProperty("content")
    private String content;

    /**
     * A list of {@link xyz.felh.openai.file.File} IDs that the message should use. There can be a maximum of 10 files attached to a message. Useful for tools like retrieval and code_interpreter that can access and use files.
     */
    @JSONField(name = "file_ids")
    @JsonProperty("file_ids")
    private List<String> fileIds;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

}