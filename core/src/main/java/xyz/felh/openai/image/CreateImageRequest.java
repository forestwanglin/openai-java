package xyz.felh.openai.image;

import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
