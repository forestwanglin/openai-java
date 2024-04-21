package xyz.felh.openai.assistant.vector.store;

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
public class CreateVectorStoreRequest implements IOpenAiApiRequest {

    /**
     * A list of File IDs that the vector store should use. Useful for tools like file_search that can access files.
     */
    @JSONField(name = "file_ids")
    @JsonProperty("file_ids")
    private List<String> fileIds;

    /**
     * The name of the vector store.
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    @JSONField(name = "expires_after")
    @JsonProperty("expires_after")
    private VectorStore.ExpiresAfter expiresAfter;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

}