package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.thread.message.Message;

import java.util.List;
import java.util.Map;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateRunRequest implements IOpenAiApiRequest {

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} to use to execute this run.
     */
    @NonNull
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

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
     * Adds additional messages to the thread before creating the run.
     */
    @JSONField(name = "additional_messages")
    @JsonProperty("additional_messages")
    private List<Message> additionalMessages;

    /**
     * Override the tools the assistant can use for this run. This is useful for modifying the behavior on a per-run basis.
     */
    @JSONField(name = "tools")
    @JsonProperty("tools")
    private List<AssistantTool> tools;

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
     * If true, returns a stream of events that happen during the Run as server-sent events, terminating when the Run enters a terminal state with a data: [DONE] message.
     */
    @JSONField(name = "stream")
    @JsonProperty("stream")
    private Boolean stream;

}