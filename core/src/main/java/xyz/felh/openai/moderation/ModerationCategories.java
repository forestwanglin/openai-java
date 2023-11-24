package xyz.felh.openai.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ModerationCategories implements IOpenAiApiObject {

    /**
     * Content that expresses, incites, or promotes hate based on race, gender, ethnicity, religion, nationality, sexual orientation, disability status, or caste. Hateful content aimed at non-protected groups (e.g., chess players) is harrassment.
     */
    private Boolean hate;

    /**
     * Hateful content that also includes violence or serious harm towards the targeted group based on race, gender, ethnicity, religion, nationality, sexual orientation, disability status, or caste.
     */
    @JsonProperty("hate/threatening")
    private Boolean hateThreatening;

    /**
     * Content that expresses, incites, or promotes harassing language towards any target.
     */
    private Boolean harassment;

    /**
     * Harassment content that also includes violence or serious harm towards any target.
     */
    @JsonProperty("harassment/threatening")
    private Boolean harassmentThreatening;

    /**
     * Content that promotes, encourages, or depicts acts of self-harm, such as suicide, cutting, and eating disorders.
     */
    @JsonProperty("self-harm")
    private Boolean selfHarm;

    /**
     * Content where the speaker expresses that they are engaging or intend to engage in acts of self-harm, such as suicide, cutting, and eating disorders.
     */
    @JsonProperty("self-harm/intent")
    private Boolean selfHarmIntent;

    /**
     * Content that encourages performing acts of self-harm, such as suicide, cutting, and eating disorders, or that gives instructions or advice on how to commit such acts.
     */
    @JsonProperty("self-harm/instructions")
    private Boolean selfHarmInstructions;

    /**
     * Content meant to arouse sexual excitement, such as the description of sexual activity, or that promotes sexual services (excluding sex education and wellness).
     */
    private Boolean sexual;

    /**
     * Sexual content that includes an individual who is under 18 years old.
     */
    @JsonProperty("sexual/minors")
    private Boolean sexualMinors;

    /**
     * Content that depicts death, violence, or physical injury.
     */
    private Boolean violence;

    /**
     * Content that depicts death, violence, or physical injury in graphic detail.
     */
    @JsonProperty("violence/graphic")
    private Boolean violenceGraphic;

}
