package xyz.felh.openai;

import lombok.Data;

@Data
public class Usage implements IOpenAiApiObject {

    /**
     * The number of prompt tokens used.
     */
    private Long promptTokens;

    /**
     * The number of completion tokens used.
     */
    private Long completionTokens;

    /**
     * The number of total tokens used
     */
    private Long totalTokens;

}
