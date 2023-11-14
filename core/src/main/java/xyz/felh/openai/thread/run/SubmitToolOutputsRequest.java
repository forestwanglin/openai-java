package xyz.felh.openai.thread.run;

import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class SubmitToolOutputsRequest implements IOpenAiApiRequest {

    /**
     * A list of tools for which the outputs are being submitted.
     * <p>
     * See {@link ToolOutput}
     */
    @NonNull
    private List<ToolOutput> toolOutputs;

}
