package xyz.felh.openai.moderation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class ModerationCategories implements IOpenAiApiObject {

    /**
     * Content that expresses, incites, or promotes hate based on race, gender, ethnicity, religion, nationality, sexual orientation, disability status, or caste. Hateful content aimed at non-protected groups (e.g., chess players) is harrassment.
     */
    @JSONField(name = "hate")
    @JsonProperty("hate")
    private Boolean hate;

    /**
     * Hateful content that also includes violence or serious harm towards the targeted group based on race, gender, ethnicity, religion, nationality, sexual orientation, disability status, or caste.
     */
    @JsonProperty("hate/threatening")
    @JSONField(name = "hate/threatening")
    private Boolean hateThreatening;

    /**
     * Content that expresses, incites, or promotes harassing language towards any target.
     */
    @JSONField(name = "harassment")
    @JsonProperty("harassment")
    private Boolean harassment;

    /**
     * Harassment content that also includes violence or serious harm towards any target.
     */
    @JsonProperty("harassment/threatening")
    @JSONField(name = "harassment/threatening")
    private Boolean harassmentThreatening;

    /**
     * Content that promotes, encourages, or depicts acts of self-harm, such as suicide, cutting, and eating disorders.
     */
    @JSONField(name = "self-harm")
    @JsonProperty("self-harm")
    private Boolean selfHarm;

    /**
     * Content where the speaker expresses that they are engaging or intend to engage in acts of self-harm, such as suicide, cutting, and eating disorders.
     */
    @JSONField(name = "self-harm/intent")
    @JsonProperty("self-harm/intent")
    private Boolean selfHarmIntent;

    /**
     * Content that encourages performing acts of self-harm, such as suicide, cutting, and eating disorders, or that gives instructions or advice on how to commit such acts.
     */
    @JSONField(name = "self-harm/instructions")
    @JsonProperty("self-harm/instructions")
    private Boolean selfHarmInstructions;

    /**
     * Content meant to arouse sexual excitement, such as the description of sexual activity, or that promotes sexual services (excluding sex education and wellness).
     */
    @JSONField(name = "sexual")
    @JsonProperty("sexual")
    private Boolean sexual;

    /**
     * Sexual content that includes an individual who is under 18 years old.
     */
    @JSONField(name = "sexual/minors")
    @JsonProperty("sexual/minors")
    private Boolean sexualMinors;

    /**
     * Content that depicts death, violence, or physical injury.
     */
    @JSONField(name = "violence")
    @JsonProperty("violence")
    private Boolean violence;

    /**
     * Content that depicts death, violence, or physical injury in graphic detail.
     */
    @JSONField(name = "violence/graphic")
    @JsonProperty("violence/graphic")
    private Boolean violenceGraphic;

}
