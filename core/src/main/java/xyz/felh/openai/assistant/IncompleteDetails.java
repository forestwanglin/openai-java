package xyz.felh.openai.assistant;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class IncompleteDetails implements IOpenAiBean {

    /**
     * The reason the message is incomplete.
     */
    @JSONField(name = "reason")
    @JsonProperty("reason")
    private String reason;

}
