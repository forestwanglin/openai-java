package xyz.felh.openai;

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
    private String object;

    /**
     * A list containing the actual results
     */
    private List<T> data;


    /**
     * 是否有更多
     */
    private Boolean hasMore;

}