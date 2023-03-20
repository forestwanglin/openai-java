package xyz.felh.openai.moderation;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateModerationRequest implements IOpenAiApiRequest {

    /**
     * The input text to classify
     */
    @NonNull
    private String input;

    /**
     * Optional
     * Defaults to text-moderation-latest
     * Two content moderations models are available: text-moderation-stable and text-moderation-latest.
     * <p>
     * The default is text-moderation-latest which will be automatically upgraded over time. This ensures you are always
     * using our most accurate model. If you use text-moderation-stable, we will provide advanced notice before updating the model.
     * Accuracy of text-moderation-stable may be slightly lower than for text-moderation-latest.
     */
    private String model;

}
