package xyz.felh.openai.moderation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class ModerationCategoryScores implements IOpenAiApiObject {

    /**
     * The score for the category 'hate'.
     */
    @JSONField(name = "hate")
    @JsonProperty("hate")
    private Double hate;

    /**
     * The score for the category 'hate/threatening'.
     */
    @JsonProperty("hate/threatening")
    @JSONField(name = "hate/threatening")
    private Double hateThreatening;

    /**
     * The score for the category 'harassment'.
     */
    @JSONField(name = "harassment")
    @JsonProperty("harassment")
    private Double harassment;

    /**
     * The score for the category 'harassment/threatening'.
     */
    @JsonProperty("harassment/threatening")
    @JSONField(name = "harassment/threatening")
    private Double harassmentThreatening;

    /**
     * The score for the category 'self-harm'.
     */
    @JsonProperty("self-harm")
    @JSONField(name = "self-harm")
    private Double selfHarm;

    /**
     * The score for the category 'self-harm/intent'.
     */
    @JsonProperty("self-harm/intent")
    @JSONField(name = "self-harm/intent")
    private Double selfHarmIntent;

    /**
     * The score for the category 'self-harm/instructions'.
     */
    @JsonProperty("self-harm/instructions")
    @JSONField(name = "self-harm/instructions")
    private Double selfHarmInstructions;

    /**
     * The score for the category 'sexual'.
     */
    @JSONField(name = "sexual")
    @JsonProperty("sexual")
    private Double sexual;

    /**
     * The score for the category 'sexual/minors'.
     */
    @JsonProperty("sexual/minors")
    @JSONField(name = "sexual/minors")
    private Double sexualMinors;

    /**
     * The score for the category 'violence'.
     */
    @JSONField(name = "violence")
    @JsonProperty("violence")
    private Double violence;

    /**
     * The score for the category 'violence/graphic'.
     */
    @JsonProperty("violence/graphic")
    @JSONField(name = "violence/graphic")
    private Double violenceGraphic;

}
