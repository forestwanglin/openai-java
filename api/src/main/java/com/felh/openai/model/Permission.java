package com.felh.openai.model;

import com.felh.openai.OpenAiApiObjectWithId;
import lombok.Data;

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
