package xyz.felh.openai.image;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseRequest implements IOpenAiApiRequest {

    /**
     * The model to use for image generation.
     * <p>
     * string, Optional
     * Defaults to dall-e-2
     */
    private String model;

    /**
     * The number of images to generate. Must be between 1 and 10. For dall-e-3, only n=1 is supported.
     * <p>
     * integer or null, Optional
     * <p>
     * Defaults to 1
     */
    private Integer n;

    /**
     * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024 for dall-e-2. Must be one of 1024x1024, 1792x1024, or 1024x1792 for dall-e-3
     * <p>
     * string or null, Optional
     * <p>
     * Defaults to 1024x1024
     * <p>
     * See {@link ImageSize}
     */
    private ImageSize size;

    /**
     * The format in which the generated images are returned. Must be one of url or b64_json.
     * <p>
     * string or null, Optional
     * <p>
     * Defaults to url
     * <p>
     * See {@link ImageResponseFormat}
     */
    private ImageResponseFormat responseFormat;

    /**
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     * <p>
     * <a href="https://platform.openai.com/docs/guides/safety-best-practices/end-user-ids">Learn more.</a>
     * <p>
     * string, Optional
     */
    private String user;

}
