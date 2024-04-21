package xyz.felh.openai.assistant.stream.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.assistant.message.Message;
import xyz.felh.openai.assistant.message.MessageContent;

import java.util.List;

@Data
public class MessageDeltaContent implements IOpenAiBean {

    @JSONField(name = "role")
    @JsonProperty("role")
    private Message.Role role;

    /**
     * The content of the message in array of text and/or images.
     */
    @JSONField(name = "content")
    @JsonProperty("content")
    private List<MessageContent> content;

}
