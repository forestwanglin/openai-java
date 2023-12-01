package xyz.felh.openai.moderation;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class Moderation implements IOpenAiApiObject {

    /**
     * Whether the content violates <a href="https://platform.openai.com/policies/usage-policies">OpenAI's usage policies</a>.
     */
    @JSONField(name = "flagged")
    @JsonProperty("flagged")
    private Boolean flagged;

    /**
     * A list of the categories, and whether they are flagged or not.
     */
    @JSONField(name = "categories")
    @JsonProperty("categories")
    private ModerationCategories categories;

    /**
     * A list of the categories along with their scores as predicted by model.
     */
    @JSONField(name = "category_scores")
    @JsonProperty("category_scores")
    private ModerationCategoryScores categoryScores;

}
