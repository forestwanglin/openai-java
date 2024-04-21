package xyz.felh.openai.assistant.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.assistant.AssistToolResources;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.assistant.thread.Thread;

import java.util.List;
import java.util.Map;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateThreadAndRunRequest implements IOpenAiApiRequest {

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} to use to execute this run.
     */
    @NonNull
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * See {@link Thread}
     */
    @JSONField(name = "thread")
    @JsonProperty("thread")
    private Thread thread;

    /**
     * The ID of the Model to be used to execute this run. If a value is provided here, it will override the model associated with the assistant. If not, the model associated with the assistant will be used.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * Override the default system message of the assistant. This is useful for modifying the behavior on a per-run basis.
     */
    @JSONField(name = "instructions")
    @JsonProperty("instructions")
    private String instructions;

    /**
     * Override the tools the assistant can use for this run. This is useful for modifying the behavior on a per-run basis.
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
     * If true, returns a stream of events that happen during the Run as server-sent events, terminating when the Run enters a terminal state with a data: [DONE] message.
     */
    @JSONField(name = "stream")
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * The maximum number of prompt tokens that may be used over the course of the run. The run will make a best effort to use only the number of prompt tokens specified, across multiple turns of the run. If the run exceeds the number of prompt tokens specified, the run will end with status incomplete. See incomplete_details for more info.
     */
    @JSONField(name = "max_prompt_tokens")
    @JsonProperty("max_prompt_tokens")
    private Integer maxPromptTokens;

    /**
     * The maximum number of completion tokens that may be used over the course of the run. The run will make a best effort to use only the number of completion tokens specified, across multiple turns of the run. If the run exceeds the number of completion tokens specified, the run will end with status incomplete. See incomplete_details for more info.
     */
    @JSONField(name = "max_completion_tokens")
    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    /**
     * Controls for how a thread will be truncated prior to the run. Use this to control the intial context window of the run.
     */
    @JSONField(name = "truncation_strategy")
    @JsonProperty("truncation_strategy")
    private TruncationStrategy truncationStrategy;

    /**
     * Controls which (if any) tool is called by the model. none means the model will not call any tools and instead generates a message. auto is the default value and means the model can pick between generating a message or calling a tool. Specifying a particular tool like {"type": "file_search"} or {"type": "function", "function": {"name": "my_function"}} forces the model to call that tool.
     * See {@link CreateRunRequest.ToolChoice}
     */
    @JSONField(name = "tool_choice")
    @JsonProperty("tool_choice")
    private Object toolChoice;

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