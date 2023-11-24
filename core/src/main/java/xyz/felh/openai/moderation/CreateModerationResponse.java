package xyz.felh.openai.moderation;

import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

import java.util.List;

@Data
public class CreateModerationResponse implements IOpenAiApiResponse {

    /**
     * The unique identifier for the moderation request.
     */
    private String id;

    /**
     * The model used to generate the moderation results.
     */
    private String model;

    /**
     * A list of moderation objects.
     * <p>
     * See {@link Moderation}
     */
    private List<Moderation> results;

}
