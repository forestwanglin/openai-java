package com.felh.openai;

import lombok.Data;

import java.util.List;

/**
 * A wrapper class of OpenAi API endpoints
 *
 * @param <T>
 */
@Data
public class OpenAiListResponse<T extends ApiEntityWithId> extends ApiEntityWithId {

    /**
     * A list containing the actual results
     */
    public List<T> data;

}
