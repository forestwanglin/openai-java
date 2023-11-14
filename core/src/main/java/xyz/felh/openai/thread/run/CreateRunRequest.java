package xyz.felh.openai.thread.run;

import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;
import xyz.felh.openai.assistant.AssistantTool;

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
    private String assistantId;

    /**
     * The ID of the Model to be used to execute this run. If a value is provided here, it will override the model associated with the assistant. If not, the model associated with the assistant will be used.
     */
    private String model;

    /**
     * Override the default system message of the assistant. This is useful for modifying the behavior on a per-run basis.
     */
    private String instructions;

    /**
     * Override the tools the assistant can use for this run. This is useful for modifying the behavior on a per-run basis.
     */
    private List<AssistantTool> tools;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    private Map<String, String> metadata;

}