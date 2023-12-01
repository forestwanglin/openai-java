package xyz.felh.openai.moderation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreateModerationResponse implements IOpenAiApiResponse {

    /**
     * The unique identifier for the moderation request.
     */
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * The model used to generate the moderation results.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * A list of moderation objects.
     * <p>
     * See {@link Moderation}
     */
    @JSONField(name = "results")
    @JsonProperty("results")
    private List<Moderation> results;

}
