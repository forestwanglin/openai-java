package xyz.felh.openai.fineTuning;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class FineTuningJob extends OpenAiApiObjectWithId {

    public static String OBJECT = "fine_tuning.job";

    /**
     * The unix timestamp for when the fine-tuning job was created.
     */
    Long createdAt;

    /**
     * The unix timestamp for when the fine-tuning job was finished.
     */
    Long finishedAt;

    /**
     * The base model that is being fine-tuned.
     */
    String model;

    /**
     * The name of the fine-tuned model that is being created.
     */
    String fineTunedModel;

    /**
     * The organization that owns the fine-tuning job.
     */
    String organizationId;

    /**
     * The current status of the fine-tuning job, which can be either created, pending, running, succeeded, failed, or cancelled.
     */
    String status;

    /**
     * The hyperparameters used for the fine-tuning job.
     */
    Hyperparameters hyperparameters;

    /**
     * The file ID used for training.
     */
    String trainingFile;

    /**
     * string or null
     * The file ID used for validation.
     */
    String validationFile;

    /**
     * The compiled results files for the fine-tuning job.
     */
    List<String> resultFiles;

    /**
     * The total number of billable tokens processed by this fine tuning job.
     */
    Long trainedTokens;

}