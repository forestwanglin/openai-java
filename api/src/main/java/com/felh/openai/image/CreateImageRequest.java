package com.felh.openai.image;

import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateImageRequest extends BaseCreateImageRequest {

    /**
     * Required
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    @NonNull
    private String prompt;

}
