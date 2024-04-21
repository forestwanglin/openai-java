package xyz.felh.openai.assistant;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.List;
import java.util.Map;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ModifyAssistantRequest implements IOpenAiApiRequest {

    /**
     * ID of the model to use. You can use the <a href="https://platform.openai.com/docs/api-reference/models/list">List models</a> API to see all of your available models, or see our <a href="https://platform.openai.com/docs/models/overview">Model overview</a> for descriptions of them.
     */
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
     * A set of resources that are used by the assistant's tools. The resources are specific to the type of tool. For example, the code_interpreter tool requires a list of file IDs, while the file_search tool requires a list of vector store IDs.
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

    /**
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic.
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * <p>
     * We generally recommend altering this or temperature but not both.
     */
    @JSONField(name = "top_p")
    @JsonProperty("top_p")
    private Double topP;

    /**
     * Specifies the format that the model must output. Compatible with GPT-4 Turbo and all GPT-3.5 Turbo models since gpt-3.5-turbo-1106.
     * <p>
     * Setting to { "type": "json_object" } enables JSON mode, which guarantees the message the model generates is valid JSON.
     * <p>
     * Important: when using JSON mode, you must also instruct the model to produce JSON yourself via a system or user message. Without this, the model may generate an unending stream of whitespace until the generation reaches the token limit, resulting in a long-running and seemingly "stuck" request. Also note that the message content may be partially cut off if finish_reason="length", which indicates the generation exceeded max_tokens or the conversation exceeded the max context length.
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private Object responseFormat;

}
