package xyz.felh.openai.thread.run;

import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

@Data
public class LastError implements IOpenAiBean {

    /**
     * One of server_error or rate_limit_exceeded.
     */
    private String code;

    /**
     * A human-readable description of the error.
     */
    private String message;

}
