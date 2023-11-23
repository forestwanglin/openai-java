package xyz.felh.openai.fineTuning;

import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateFineTuningJobRequest implements IOpenAiApiRequest {

    /**
     * The ID of an uploaded file that contains training data.
     * Your dataset must be formatted as a JSONL file. Additionally, you must upload your file with the purpose fine-tune.
     */
    @NonNull
    private String trainingFile;

    /**
     * Optional
     * <p>
     * The ID of an uploaded file that contains validation data.
     * <p>
     * If you provide this file, the data is used to generate validation metrics periodically during fine-tuning.
     * These metrics can be viewed in the fine-tuning results file. The same data should not be present in both train and validation files.
     * <p>
     * Your dataset must be formatted as a JSONL file. You must upload your file with the purpose fine-tune.
     */
    private String validationFile;

    /**
     * The name of the model to fine-tune. You can select one of the <a href="https://platform.openai.com/docs/guides/fine-tuning/what-models-can-be-fine-tuned">supported models</a>.
     */
    @NonNull
    private String model;

    /**
     * Optional
     * The hyperparameters used for the fine-tuning job.
     */
    private Hyperparameters hyperparameters;

    /**
     * A string of up to 18 characters that will be added to your fine-tuned model name.
     * <p>
     * For example, a suffix of "custom-model-name" would produce a model name like ft:gpt-3.5-turbo:openai:custom-model-name:7p4lURel.
     */
    private String suffix;

}