package xyz.felh.openai.thread.run.step;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class RunStepDeltaContent implements IOpenAiBean {

    /**
     * The delta containing the fields that have changed on the run step.
     * <p>
     * See {@link StepDetails}
     */
    @JSONField(name = "step_details")
    @JsonProperty("step_details")
    private StepDetails stepDetails;

}
