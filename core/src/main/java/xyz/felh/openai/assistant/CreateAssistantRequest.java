package xyz.felh.openai.assistant;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.List;
import java.util.Map;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssistantRequest implements IOpenAiApiRequest {

    /**
     * ID of the model to use. You can use the <a href="https://platform.openai.com/docs/api-reference/models/list">List models</a> API to see all of your available models, or see our <a href="https://platform.openai.com/docs/models/overview">Model overview</a> for descriptions of them.
     */
    @NonNull
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * The name of the assistant. The maximum length is 256 characters.
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * The description of the assistant. The maximum length is 512 characters.
     */
    @JSONField(name = "description")
    @JsonProperty("description")
    private String description;

    /**
     * The system instructions that the assistant uses. The maximum length is 32768 characters.
     */
    @JSONField(name = "instructions")
    @JsonProperty("instructions")
    private String instructions;

    /**
     * A list of tool enabled on the assistant. There can be a maximum of 128 tools per assistant. Tools can be of types code_interpreter, retrieval, or function.
     * <p>
     * Defaults to []
     * <p>
     * See {@link AssistantTool}
     */
    @JSONField(name = "tools")
    @JsonProperty("tools")
    private List<AssistantTool> tools;

    /**
     * A list of <a href="https://platform.openai.com/docs/api-reference/files">file</a> IDs attached to this assistant. There can be a maximum of 20 files attached to the assistant. Files are ordered by their creation date in ascending order.
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
