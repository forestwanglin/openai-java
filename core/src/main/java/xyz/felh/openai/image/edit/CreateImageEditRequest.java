package xyz.felh.openai.image.edit;

import xyz.felh.openai.image.BaseCreateImageRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageEditRequest extends BaseCreateImageRequest {

    /**
     * Required image or imagePath
     * The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided,
     * image must have transparency, which will be used as the mask.
     */
    private String imagePath;
    private byte[] image;

    /**
     * Optional
     * An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be edited.
     * Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     */
    private byte[] mask;
    private String maskPath;

    /**
     * Required
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    @NonNull
    private String prompt;

}
