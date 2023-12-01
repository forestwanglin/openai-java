package xyz.felh.openai;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class DeleteResponse implements IOpenAiApiResponse {

    /**
     * The id of the object.
     */
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * The type of object deleted, for example "file" or "model"
     */
    @JSONField(name = "object")
    @JsonProperty("object")
    private String object;

    /**
     * True if successfully deleted
     */
    @JSONField(name = "deleted")
    @JsonProperty("deleted")
    private boolean deleted;

}
