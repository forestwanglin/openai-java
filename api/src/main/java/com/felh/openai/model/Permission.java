package com.felh.openai.model;

import com.felh.openai.ApiEntityWithId;
import lombok.Data;

@Data
public class Permission extends ApiEntityWithId {

    public boolean allowCreateEngine;

    public boolean allowSampling;

    public boolean allowLogprobs;

    public boolean allowSearchIndices;

    public boolean allowView;

    public boolean allowFineTuning;

    public String organization;

    public String group;

    public boolean isBlocking;

}
