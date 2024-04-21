package xyz.felh.openai.assistant.thread;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.assistant.AssistToolResources;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Thread extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread";

    /**
     * The Unix timestamp (in seconds) for when the thread was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * A set of resources that are made available to the assistant's tools in this thread. The resources are specific to the type of tool. For example, the code_interpreter tool requires a list of file IDs, while the file_search tool requires a list of vector store IDs.
     */
    @JSONField(name = "tool_resources")
    @JsonProperty("tool_resources")
    private AssistToolResources toolResources;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

}