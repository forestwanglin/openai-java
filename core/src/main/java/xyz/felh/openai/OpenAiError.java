package xyz.felh.openai;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiError implements IOpenAiApiObject {

    @JSONField(name = "error")
    @JsonProperty("error")
    private ErrorDetail error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetail implements IOpenAiApiObject {
        /**
         * Human-readable error message
         */
        @JSONField(name = "message")
        @JsonProperty("message")
        private String message;

        /**
         * OpenAI error type, for example "invalid_request_error"
         * <a href="https://platform.openai.com/docs/guides/error-codes/python-library-error-types">Reference</a>
         */
        @JSONField(name = "type")
        @JsonProperty("type")
        private String type;

        @JSONField(name = "param")
        @JsonProperty("param")
        private String param;

        /**
         * OpenAI error code, for example "invalid_api_key"
         */
        @JSONField(name = "code")
        @JsonProperty("code")
        private String code;
    }

}
