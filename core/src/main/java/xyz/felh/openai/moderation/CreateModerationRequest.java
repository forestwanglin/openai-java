package xyz.felh.openai.moderation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateModerationRequest implements IOpenAiApiRequest {

    /**
     * string or array Required
     * <p>
     * The input text to classify
     */
    @NonNull
    @JSONField(name = "input")
    @JsonProperty("input")
    private Object input;

    /**
     * Optional Defaults to text-moderation-latest
     * <p>
     * Two content moderations models are available: text-moderation-stable and text-moderation-latest.
     * <p>
     * The default is text-moderation-latest which will be automatically upgraded over time. This ensures you are always
     * using our most accurate model. If you use text-moderation-stable, we will provide advanced notice before updating the model.
     * Accuracy of text-moderation-stable may be slightly lower than for text-moderation-latest.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

}
