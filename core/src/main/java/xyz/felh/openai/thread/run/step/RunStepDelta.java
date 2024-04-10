package xyz.felh.openai.thread.run.step;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
public class RunStepDelta extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.run.step.delta";

    @JSONField(name = "delta")
    @JsonProperty("delta")
    private RunStepDeltaContent delta;

}