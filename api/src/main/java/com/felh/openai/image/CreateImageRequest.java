package com.felh.openai.image;

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
public class CreateImageRequest extends BaseCreateImageRequest {

    /**
     * Required
     * A text description of the desired image(s). The maximum length is 1000 characters.
     */
    private String prompt;

}
