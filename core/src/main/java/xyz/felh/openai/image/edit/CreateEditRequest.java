package xyz.felh.openai.image.edit;

import xyz.felh.openai.image.BaseRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateEditRequest extends BaseRequest {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     * <p>
     * string
     */
    @NonNull
    private String prompt;

    /**
     * The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided, image must have transparency, which will be used as the mask.
     * <p>
     * string, Required
     */
    private byte[] image;
    private String imagePath;

    /**
     * An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     * <p>
     * string, Optional
     */
    private byte[] mask;
    private String maskPath;

}
