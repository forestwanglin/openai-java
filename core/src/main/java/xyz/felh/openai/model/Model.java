package xyz.felh.openai.model;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;

/**
 * List and describe the various models available in the API.
 * You can refer to the Models documentation to understand what models are available and the differences between them.
 * <p>
 * See <a href="https://platform.openai.com/docs/api-reference/models">document</a>
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class Model extends OpenAiApiObjectWithId {

    public static String OBJECT = "model";

    /**
     * The Unix timestamp (in seconds) when the model was created.
     */
    @JSONField(name = "created")
    @JsonProperty("created")
    public Long created;

    /**
     * The organization that owns the model.
     */
    @JSONField(name = "owned_by")
    @JsonProperty("owned_by")
    private String ownedBy;

}
