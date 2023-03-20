package xyz.felh.openai;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OpenAiError implements IOpenAiApiObject {

    private ErrorDetail error;

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ErrorDetail implements IOpenAiApiObject {
        /**
         * Human-readable error message
         */
        private String message;

        /**
         * OpenAI error type, for example "invalid_request_error"
         * https://platform.openai.com/docs/guides/error-codes/python-library-error-types
         */
        private String type;

        private String param;

        /**
         * OpenAI error code, for example "invalid_api_key"
         */
        private String code;
    }

}
