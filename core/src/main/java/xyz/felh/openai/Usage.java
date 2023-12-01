package xyz.felh.openai;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class Usage implements IOpenAiApiObject {

    /**
     * The number of prompt tokens used.
     */
    @JSONField(name = "prompt_tokens")
    @JsonProperty("prompt_tokens")
    private Long promptTokens;

    /**
     * The number of completion tokens used.
     */
    @JSONField(name = "completion_tokens")
    @JsonProperty("completion_tokens")
    private Long completionTokens;

    /**
     * The number of total tokens used
     */
    @JSONField(name = "total_tokens")
    @JsonProperty("total_tokens")
    private Long totalTokens;

}
