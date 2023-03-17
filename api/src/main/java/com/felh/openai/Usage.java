package com.felh.openai;

import lombok.Data;

@Data
public class Usage implements IApiEntity{

    /**
     * The number of prompt tokens used.
     */
    long promptTokens;

    /**
     * The number of completion tokens used.
     */
    long completionTokens;

    /**
     * The number of total tokens used
     */
    long totalTokens;

}
