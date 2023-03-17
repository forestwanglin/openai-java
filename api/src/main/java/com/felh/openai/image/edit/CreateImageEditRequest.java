package com.felh.openai.image.edit;

import com.felh.openai.image.BaseCreateImageRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageEditRequest extends BaseCreateImageRequest {

    /**
     * Required
     * The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided,
     * image must have transparency, which will be used as the mask.
     */
    @NonNull
    private String image;

    /**
     * Optional
     * An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be edited.
     * Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     */
    private String mask;

    /**
     * Required
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    @NonNull
    private String prompt;

}
