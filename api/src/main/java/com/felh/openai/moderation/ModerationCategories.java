package com.felh.openai.moderation;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class ModerationCategories implements IOpenAiApiObject {

    public boolean hate;

    @JsonProperty("hate/threatening")
    public boolean hateThreatening;

    @JsonProperty("self-harm")
    public boolean selfHarm;

    public boolean sexual;

    @JsonProperty("sexual/minors")
    public boolean sexualMinors;

    public boolean violence;

    @JsonProperty("violence/graphic")
    public boolean violenceGraphic;

}
