package xyz.felh.openai.batch;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;

import java.util.Map;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class BatchOutputLineObject implements IOpenAiBean {

    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * A developer-provided per-request id that will be used to match outputs to inputs. Must be unique for each request in a batch.
     */
    @JSONField(name = "custom_id")
    @JsonProperty("custom_id")
    private String customId;

    @JSONField(name = "response")
    @JsonProperty("response")
    private Response response;

    /**
     * For requests that failed with a non-HTTP error, this will contain more information on the cause of the failure.
     */
    @JSONField(name = "error")
    @JsonProperty("error")
    private Error error;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Response implements IOpenAiBean {

        /**
         * The HTTP status code of the response
         */
        @JSONField(name = "status_code")
        @JsonProperty("status_code")
        private Integer statusCode;

        /**
         * An unique identifier for the OpenAI API request. Please include this request ID when contacting support.
         */
        @JSONField(name = "request_id")
        @JsonProperty("request_id")
        private String requestId;

        /**
         * The JSON body of the response
         */
        @JSONField(name = "body")
        @JsonProperty("body")
        private Map<?, ?> body;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Error implements IOpenAiBean {

        /**
         * A machine-readable error code.
         */
        @JSONField(name = "code")
        @JsonProperty("code")
        private String code;

        /**
         * A human-readable error message.
         */
        @JSONField(name = "message")
        @JsonProperty("message")
        private String message;
    }

}
