package xyz.felh.openai.thread.run;

import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class ToolOutput implements IOpenAiBean {

    /**
     * The ID of the tool call in the required_action object within the run object the output is being submitted for.
     */
    private String toolCallId;

    /**
     * The output of the tool call to be submitted to continue the run.
     */
    private String output;

}
