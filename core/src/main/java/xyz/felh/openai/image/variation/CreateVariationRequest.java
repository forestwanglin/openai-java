package xyz.felh.openai.image.variation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.image.BaseRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor(force = true)
@ToString(callSuper = true)
@SuperBuilder(toBuilder = true)
public class CreateVariationRequest extends BaseRequest {

    /**
     * The image to use as the basis for the variation(s). Must be a valid PNG file, less than 4MB, and square.
     * <p>
     * string, Required
     */
    @JSONField(name = "image")
    @JsonProperty("image")
    private byte[] image;
    @JSONField(name = "image_path")
    @JsonProperty("image_path")
    private String imagePath;

}
