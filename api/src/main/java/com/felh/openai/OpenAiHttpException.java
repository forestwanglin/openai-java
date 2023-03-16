package com.felh.openai;

public class OpenAiHttpException extends RuntimeException {

    /**
     * HTTP status code
     */
    public final int statusCode;

    /**
     * OpenAI error code, for example "invalid_api_key"
     */
    public final String code;

    public final String param;

    /**
     * OpenAI error type, for example "invalid_request_error"
     * https://platform.openai.com/docs/guides/error-codes/python-library-error-types
     */
    public final String type;

    public OpenAiHttpException(OpenAiError error, Exception parent, int statusCode) {
        super(error.getError().getMessage(), parent);
        // todo error.error looks dumb
        this.statusCode = statusCode;
        this.code = error.getError().getCode();
        this.param = error.getError().getParam();
        this.type = error.getError().getType();
    }

}
