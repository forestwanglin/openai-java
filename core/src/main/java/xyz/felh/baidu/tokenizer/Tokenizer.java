package xyz.felh.baidu.tokenizer;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.baidu.Usage;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
public class Tokenizer extends OpenAiApiObjectWithId {

    public static String OBJECT = "tokenizer.erniebot";

    /**
     * token统计信息
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

}
