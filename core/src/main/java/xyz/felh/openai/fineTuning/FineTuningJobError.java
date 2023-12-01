package xyz.felh.openai.fineTuning;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class FineTuningJobError implements IOpenAiApiObject {

    /**
     * A machine-readable error code.
     */
    @JSONField(name = "code")
    @JsonProperty("code")
    private String code;

    /**
     * A human-readable error message.
     */
    @JSONField(name = "message")
    @JsonProperty("message")
    private String message;

    /**
     * The parameter that was invalid, usually training_file or validation_file. This field will be null if the failure was not parameter-specific.
     */
    @JSONField(name = "param")
    @JsonProperty("param")
    private String param;

}
