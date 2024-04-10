package xyz.felh.openai.thread.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

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

    /**
     * A list of file IDs that the assistant should use. Useful for tools like retrieval and code_interpreter that can access files. A maximum of 10 files can be attached to a message.
     */
    @JSONField(name = "file_ids")
    @JsonProperty("file_ids")
    private List<String> fileIds;

}
