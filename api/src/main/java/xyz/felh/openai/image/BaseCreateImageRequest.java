package xyz.felh.openai.image;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
import lombok.experimental.SuperBuilder;

@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder(toBuilder = true)
public abstract class BaseCreateImageRequest implements IOpenAiApiRequest {

    /**
     * Optional
     * Defaults to 1
     * The number of images to generate. Must be between 1 and 10.
     */
    private Integer n;

    /**
     * Optional
     * Defaults to 1024x1024
     * The size of the generated images. Must be one of 256x256, 512x512, or 1024x1024.
     */
    private String size;

    /**
     * Optional
     * Defaults to url
     * The format in which the generated images are returned. Must be one of url or b64_json.
     */
    private String responseFormat;

    /**
     * Optional
     * A unique identifier representing your end-user, which can help OpenAI to monitor and detect abuse.
     * learn more https://platform.openai.com/docs/guides/safety-best-practices/end-user-ids
     */
    private String user;

}
