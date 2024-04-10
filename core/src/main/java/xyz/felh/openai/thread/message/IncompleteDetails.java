package xyz.felh.openai.thread.message;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class IncompleteDetails implements IOpenAiBean {

    @JSONField(name = "reason")
    @JsonProperty("reason")
    private String reason;

}
