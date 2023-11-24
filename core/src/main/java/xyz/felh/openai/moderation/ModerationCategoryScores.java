package xyz.felh.openai.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ModerationCategoryScores implements IOpenAiApiObject {

    /**
     * The score for the category 'hate'.
     */
    private Double hate;

    /**
     * The score for the category 'hate/threatening'.
     */
    @JsonProperty("hate/threatening")
    public Double hateThreatening;

    /**
     * The score for the category 'harassment'.
     */
    private Double harassment;

    /**
     * The score for the category 'harassment/threatening'.
     */
    @JsonProperty("harassment/threatening")
    private Double harassmentThreatening;

    /**
     * The score for the category 'self-harm'.
     */
    @JsonProperty("self-harm")
    public Double selfHarm;

    /**
     * The score for the category 'self-harm/intent'.
     */
    @JsonProperty("self-harm/intent")
    public Double selfHarmIntent;

    /**
     * The score for the category 'self-harm/instructions'.
     */
    @JsonProperty("self-harm/instructions")
    public Double selfHarmInstructions;

    /**
     * The score for the category 'sexual'.
     */
    public Double sexual;

    /**
     * The score for the category 'sexual/minors'.
     */
    @JsonProperty("sexual/minors")
    public Double sexualMinors;

    /**
     * The score for the category 'violence'.
     */
    public Double violence;

    /**
     * The score for the category 'violence/graphic'.
     */
    @JsonProperty("violence/graphic")
    public Double violenceGraphic;

}
