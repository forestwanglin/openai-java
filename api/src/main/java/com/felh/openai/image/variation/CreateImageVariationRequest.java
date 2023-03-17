package com.felh.openai.image.variation;

import com.felh.openai.image.BaseCreateImageRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
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
    private String image;

}
