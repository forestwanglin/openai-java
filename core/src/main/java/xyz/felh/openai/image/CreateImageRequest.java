package xyz.felh.openai.image;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageRequest extends BaseRequest {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters for dall-e-2 and 4000 characters for dall-e-3.
     * <p>
     * string, Required
     */
    @NonNull
    private String prompt;

    /**
     * The quality of the image that will be generated. hd creates images with finer details and greater consistency across the image. This param is only supported for dall-e-3.
     * <p>
     * string, Optional
     * <p>
     * Default to standard
     * <p>
     * See {@link ImageQuality}
     */
    private ImageQuality quality;

    /**
     * The style of the generated images. Must be one of vivid or natural. Vivid causes the model to lean towards generating hyper-real and dramatic images. Natural causes the model to produce more natural, less hyper-real looking images. This param is only supported for dall-e-3.
     * <p>
     * string or null, Optional
     * <p>
     * Defaults to vivid
     * <p>
     * See {@link ImageStyle}
     */
    private ImageStyle style;

}
