package xyz.felh.openai.fineTuning;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class FineTuningJobEvent extends OpenAiApiObjectWithId {

    public static String OBJECT = "fine_tuning.job.event";

    private Long createdAt;

    private String level;

    private String message;

    private Object data;

    private String type;

}