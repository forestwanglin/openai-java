package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class ToolOutput implements IOpenAiBean {

    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     */
    @JSONField(name = "tool_call_id")
    @JsonProperty("tool_call_id")
    private String toolCallId;

    /**
     * The output of the tool call to be submitted to continue the run.
     */
    @JSONField(name = "output")
    @JsonProperty("output")
    private String output;

}
