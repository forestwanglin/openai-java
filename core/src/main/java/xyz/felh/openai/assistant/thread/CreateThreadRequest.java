package xyz.felh.openai.assistant.thread;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.assistant.AssistToolResources;
import xyz.felh.openai.assistant.message.Message;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateThreadRequest implements IOpenAiApiRequest {

    /**
     * A list of {@link Message} to start the thread with.
     * <p>
     * required fields: role, content
     */
    @JSONField(name = "messages")
    @JsonProperty("messages")
    private List<Message> messages;

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