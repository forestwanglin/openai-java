package xyz.felh.openai.image;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Image implements IOpenAiApiObject {

    /**
     * The URL of the generated image, if response_format is url (default).
     */
    private String url;

    /**
     * The base64-encoded JSON of the generated image, if response_format is b64_json.
     */
    private String b64Json;

    /**
     * The prompt that was used to generate the image, if there was any revision to the prompt.
     */
    private String revisedPrompt;

}
