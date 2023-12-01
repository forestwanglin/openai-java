package xyz.felh.openai.image;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Image implements IOpenAiApiObject {

    /**
     * The URL of the generated image, if response_format is url (default).
     */
    @JSONField(name = "url")
    @JsonProperty("url")
    private String url;

    /**
     * The base64-encoded JSON of the generated image, if response_format is b64_json.
     */
    @JSONField(name = "b64_json")
    @JsonProperty("b64_json")
    private String b64Json;

    /**
     * The prompt that was used to generate the image, if there was any revision to the prompt.
     */
    @JSONField(name = "revised_prompt")
    @JsonProperty("revised_prompt")
    private String revisedPrompt;

}
