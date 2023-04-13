package xyz.felh.openai.image.variation;

import xyz.felh.openai.image.BaseCreateImageRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageVariationRequest extends BaseCreateImageRequest {

    /**
     * Required image or imagePath
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    private String imagePath;

    private byte[] image;

}
