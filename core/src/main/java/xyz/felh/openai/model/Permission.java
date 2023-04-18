package xyz.felh.openai.model;

import lombok.EqualsAndHashCode;
import xyz.felh.openai.OpenAiApiObjectWithId;
import lombok.Data;

@EqualsAndHashCode(callSuper = true)
@Data
public class Permission extends OpenAiApiObjectWithId {

    public Boolean allowCreateEngine;

    public Boolean allowSampling;

    public Boolean allowLogprobs;

    public Boolean allowSearchIndices;

    public Boolean allowView;

    public Boolean allowFineTuning;

    public String organization;

    public String group;

    public Boolean isBlocking;

}
