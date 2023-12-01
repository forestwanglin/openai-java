package xyz.felh.openai.thread.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class LastError implements IOpenAiBean {

    /**
     * One of server_error or rate_limit_exceeded.
     */
    @JSONField(name = "code")
    @JsonProperty("code")
    private String code;

    /**
     * A human-readable description of the error.
     */
    @JSONField(name = "message")
    @JsonProperty("message")
    private String message;

}
