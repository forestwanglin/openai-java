package xyz.felh.openai.image.edit;

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
public class CreateEditRequest extends BaseRequest {

    /**
     * A text description of the desired image(s). The maximum length is 1000 characters.
     * <p>
     * string
     */
    @NonNull
    @JSONField(name = "prompt")
    @JsonProperty("prompt")
    private String prompt;

    /**
     * The image to edit. Must be a valid PNG file, less than 4MB, and square. If mask is not provided, image must have transparency, which will be used as the mask.
     * <p>
     * string, Required
     */
    @JSONField(name = "image")
    @JsonProperty("image")
    private byte[] image;
    @JSONField(name = "image_path")
    @JsonProperty("image_path")
    private String imagePath;

    /**
     * An additional image whose fully transparent areas (e.g. where alpha is zero) indicate where image should be edited. Must be a valid PNG file, less than 4MB, and have the same dimensions as image.
     * <p>
     * string, Optional
     */
    @JSONField(name = "mask")
    @JsonProperty("mask")
    private byte[] mask;
    @JSONField(name = "mask_path")
    @JsonProperty("mask_path")
    private String maskPath;

}
