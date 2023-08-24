package xyz.felh.openai.fineTuning;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class FineTuningJobEvent extends OpenAiApiObjectWithId {

    Long createdAt;

    String level;

    String message;

    Object data;

    String type;

}