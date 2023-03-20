package xyz.felh.openai.image.variation;

import xyz.felh.openai.image.BaseCreateImageRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageVariationRequest extends BaseCreateImageRequest {

    /**
     * Required
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     */
    @NonNull
    private String image;

}
