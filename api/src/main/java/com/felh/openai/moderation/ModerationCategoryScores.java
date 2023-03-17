package com.felh.openai.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class ModerationCategoryScores implements IApiEntity {

    public double hate;

    @JsonProperty("hate/threatening")
    public double hateThreatening;

    @JsonProperty("self-harm")
    public double selfHarm;

    public double sexual;

    @JsonProperty("sexual/minors")
    public double sexualMinors;

    public double violence;

    @JsonProperty("violence/graphic")
    public double violenceGraphic;

}
