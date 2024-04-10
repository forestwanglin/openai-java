package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JSONField(name = "tool_outputs")
    @JsonProperty("tool_outputs")
    private List<ToolOutput> toolOutputs;

    /**
     * If true, returns a stream of events that happen during the Run as server-sent events, terminating when the Run enters a terminal state with a data: [DONE] message.
     */
    @JSONField(name = "stream")
    @JsonProperty("stream")
    private Boolean stream;

}
