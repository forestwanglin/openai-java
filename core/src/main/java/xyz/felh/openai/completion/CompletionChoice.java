package xyz.felh.openai.completion;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class CompletionChoice implements IOpenAiApiObject {

    private String text;

    private Integer index;

    private Logprobs logprobs;

    /**
     * The reason the model stopped generating tokens. This will be stop if the model hit a natural stop point or a provided stop sequence, length if the maximum number of tokens specified in the request was reached, or content_filter if content was omitted due to a flag from our content filters.
     */
    private String finishReason;

}
