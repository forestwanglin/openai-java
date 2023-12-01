package xyz.felh.openai.fineTuning;

import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class FineTuningJobError implements IOpenAiApiObject {

    /**
     * A machine-readable error code.
     */
    private String code;

    /**
     * A human-readable error message.
     */
    private String message;

    /**
     * The parameter that was invalid, usually training_file or validation_file. This field will be null if the failure was not parameter-specific.
     */
    private String param;

}
