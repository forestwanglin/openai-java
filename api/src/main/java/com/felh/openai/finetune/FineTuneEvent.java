package com.felh.openai.finetune;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class FineTuneEvent implements IApiEntity {

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
