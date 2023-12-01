package xyz.felh.openai;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.util.List;

/**
 * A List wrapper class of OpenAi API endpoints
 *
 * @param <T>
 */
@Data
public class OpenAiApiListResponse<T extends IOpenAiApiObject> implements IOpenAiApiResponse {

    /**
     * always "list"
     */
    @JSONField(name = "object")
    @JsonProperty("object")
    private String object;

    /**
     * A list containing the actual results
     */
    @JSONField(name = "data")
    @JsonProperty("data")
    private List<T> data;


    /**
     * 是否有更多
     */
    @JSONField(name = "has_more")
    @JsonProperty("has_more")
    private Boolean hasMore;

    /**
     * add when list assistants
     */
    @JSONField(name = "first_id")
    @JsonProperty("first_id")
    private String firstId;

    /**
     * add when list assistants
     */
    @JSONField(name = "last_id")
    @JsonProperty("last_id")
    private String lastId;

}