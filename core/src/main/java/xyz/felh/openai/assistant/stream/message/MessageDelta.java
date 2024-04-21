package xyz.felh.openai.assistant.stream.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;


@EqualsAndHashCode(callSuper = true)
@Data
public class MessageDelta extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.message.delta";

    /**
     * The delta containing the fields that have changed on the Message.
     */
    @JSONField(name = "delta")
    @JsonProperty("delta")
    private MessageDeltaContent delta;

}