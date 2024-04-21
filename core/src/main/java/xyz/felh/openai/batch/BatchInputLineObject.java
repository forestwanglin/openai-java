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
public class BatchInputLineObject implements IOpenAiBean {

    /**
     * A developer-provided per-request id that will be used to match outputs to inputs. Must be unique for each request in a batch.
     */
    @JSONField(name = "custom_id")
    @JsonProperty("custom_id")
    private String customId;

    /**
     * The HTTP method to be used for the request. Currently only POST is supported.
     */
    @JSONField(name = "method")
    @JsonProperty("method")
    private String method;

    /**
     * The OpenAI API relative URL to be used for the request. Currently only /v1/chat/completions is supported.
     */
    @JSONField(name = "url")
    @JsonProperty("url")
    private String url;

    @JSONField(name = "body")
    @JsonProperty("body")
    private Map<?, ?> body;

}
