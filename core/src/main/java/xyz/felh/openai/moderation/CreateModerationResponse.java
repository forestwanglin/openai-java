package xyz.felh.openai.moderation;

import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreateModerationResponse implements IOpenAiApiResponse {

    private String id;

    private String model;

    private List<Moderation> results;

}
