package xyz.felh.openai.chat;


import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;

@Data
public class Logprobs implements IOpenAiApiObject {

    /**
     * A list of message content tokens with log probability information.
     *
     * See {@link Content}
     */
    @JSONField(name = "content")
    @JsonProperty("content")
    private List<Content> content;

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Content implements IOpenAiBean {

        /**
         * The token.
         */
        @JSONField(name = "token")
        @JsonProperty("token")
        private String token ;

        /**
         * The log probability of this token, if it is within the top 20 most likely tokens.
         * Otherwise, the value -9999.0 is used to signify that the token is very unlikely.
         */
        @JSONField(name = "logprob")
        @JsonProperty("logprob")
        private Double logprob;

        /**
         * A list of integers representing the UTF-8 bytes representation of the token.
         * Useful in instances where characters are represented by multiple tokens and their byte representations
         * must be combined to generate the correct text representation. Can be null if there is no bytes representation for the token.
         */
        @JSONField(name = "bytes")
        @JsonProperty("bytes")
        private List<Integer> bytes;

        /**
         * List of the most likely tokens and their log probability, at this token position.
         * In rare cases, there may be fewer than the number of requested top_logprobs returned.
         */
        @JSONField(name = "top_logprobs")
        @JsonProperty("top_logprobs")
        private List<Content> topLogprobs;

    }

}
