package xyz.felh.openai.fineTuning;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class FineTuningJobEvent extends OpenAiApiObjectWithId {

    public static String OBJECT = "fine_tuning.job.event";

    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Long createdAt;

    @JSONField(name = "level")
    @JsonProperty("level")
    private String level;

    @JSONField(name = "message")
    @JsonProperty("message")
    private String message;

    @JSONField(name = "data")
    @JsonProperty("data")
    private Object data;

    @JSONField(name = "type")
    @JsonProperty("type")
    private String type;

}