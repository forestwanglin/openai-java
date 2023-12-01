package xyz.felh.openai;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;

/**
 * Runtime Exception for HTTP
 */
public class OpenAiHttpException extends RuntimeException {

    /**
     * HTTP status code
     */
    @JSONField(name = "status_code")
    @JsonProperty("status_code")
    public final int statusCode;

    /**
     * OpenAI error code, for example "invalid_api_key"
     */
    @JSONField(name = "code")
    @JsonProperty("code")
    public final String code;

    @JSONField(name = "param")
    @JsonProperty("param")
    public final String param;

    /**
     * OpenAI error type, for example "invalid_request_error"
     * https://platform.openai.com/docs/guides/error-codes/python-library-error-types
     */
    public final String type;

    public OpenAiHttpException(OpenAiError error, Exception parent, int statusCode) {
        super(error.getError().getMessage(), parent);
        this.statusCode = statusCode;
        this.code = error.getError().getCode();
        this.param = error.getError().getParam();
        this.type = error.getError().getType();
    }

    public static void main(String[] args) {
        System.out.println("ffff");
    }

}
