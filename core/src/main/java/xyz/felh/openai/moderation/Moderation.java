package xyz.felh.openai.moderation;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Moderation implements IOpenAiApiObject {

    /**
     * Whether the content violates <a href="https://platform.openai.com/policies/usage-policies">OpenAI's usage policies</a>.
     */
    private Boolean flagged;

    /**
     * A list of the categories, and whether they are flagged or not.
     */
    private ModerationCategories categories;

    /**
     * A list of the categories along with their scores as predicted by model.
     */
    private ModerationCategoryScores categoryScores;

}
