package com.felh.openai.model;

import com.felh.openai.ApiEntityWithId;
import lombok.Data;

@Data
public class Permission extends ApiEntityWithId {

    /**
     * The creation time in epoch seconds.
     */
    public long created;

    public boolean allowCreateEngine;

    public boolean allowSampling;

    public boolean allowLogProbs;

    public boolean allowSearchIndices;

    public boolean allowView;

    public boolean allowFineTuning;

    public String organization;

    public String group;

    public boolean isBlocking;

}
