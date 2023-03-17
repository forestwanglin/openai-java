package com.felh.openai.moderation;

import com.felh.openai.IApiEntity;
import lombok.Data;

import java.util.List;

@Data
public class CreateModerationResponse implements IApiEntity {

    private String id;

    private String model;

    private List<Moderation> results;

}
