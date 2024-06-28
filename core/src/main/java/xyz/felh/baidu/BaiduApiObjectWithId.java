package xyz.felh.baidu;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

/**
 * An abstract class of OpenAi API Response
 */
@Data
public abstract class BaiduApiObjectWithId implements IBaiduApiObject {

    /**
     * A unique identifier for the chat completion.
     */
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * The object type
     */
    @JSONField(name = "object")
    @JsonProperty("object")
    private String object;

}
