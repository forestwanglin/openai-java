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
     * The Unix timestamp (in seconds) for when the fine-tuning job was created.
     */
    private Long createdAt;

    /**
     * The Unix timestamp (in seconds) for when the fine-tuning job was finished. The value will be null if the fine-tuning job is still running.
     */
    private Long finishedAt;

    /**
     * The base model that is being fine-tuned.
     */
    private String model;

    /**
     * The name of the fine-tuned model that is being created. The value will be null if the fine-tuning job is still running.
     */
    private String fineTunedModel;

    /**
     * The organization that owns the fine-tuning job.
     */
    private String organizationId;

    /**
     * The current status of the fine-tuning job, which can be either validating_files, queued, running, succeeded, failed, or cancelled.
     */
    private String status;

    /**
     * The hyperparameters used for the fine-tuning job. See the <a href="https://platform.openai.com/docs/guides/fine-tuning">fine-tuning</a> guide for more details.
     * See {@link Hyperparameters}
     */
    private Hyperparameters hyperparameters;

    /**
     * The file ID used for training.
     */
    private String trainingFile;

    /**
     * string or null
     * The file ID used for validation.
     */
    private String validationFile;

    /**
     * The compiled results file ID(s) for the fine-tuning job. You can retrieve the results with the <a href="https://platform.openai.com/docs/api-reference/files/retrieve-contents">Files API</a>.
     */
    private List<String> resultFiles;

    /**
     * The total number of billable tokens processed by this fine tuning job.
     */
    private Long trainedTokens;

}