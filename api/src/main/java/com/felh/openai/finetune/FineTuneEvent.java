package com.felh.openai.finetune;

import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class FineTuneEvent implements IOpenAiApiObject {

    /**
     * The type of object returned, should be "fine-tune-event".
     */
    String object;

    /**
     * The creation time in epoch seconds.
     */
    Long createdAt;

    /**
     * The log level of this message.
     */
    String level;

    /**
     * The event message.
     */
    String message;

}
