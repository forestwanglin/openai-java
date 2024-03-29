package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.thread.message.Message;

import java.util.List;
import java.util.Map;

@Data
public class Thread implements IOpenAiBean {

    /**
     * A list of messages to start the thread with.
     * <p>
     * See {@link Message}
     * <p>
     * role and content is required
     */
    @JSONField(name = "messages")
    @JsonProperty("messages")
    private List<Message> messages;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

}
