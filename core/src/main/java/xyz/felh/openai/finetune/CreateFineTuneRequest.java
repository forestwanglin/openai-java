package xyz.felh.openai.finetune;

import com.fasterxml.jackson.annotation.JsonProperty;
import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateFineTuneRequest implements IOpenAiApiRequest {

    /**
     * The ID of an uploaded file that contains training data.
     */
    @NonNull
    private String trainingFile;

    /**
     * The ID of an uploaded file that contains validation data.
     */
    private String validationFile;

    /**
     * The name of the base model to fine-tune. You can select one of "ada", "babbage", "curie", or "davinci".
     * To learn more about these models, see the Engines documentation.
     */
    private String model;

    /**
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
     */
    private Integer nEpochs;

    /**
     * The batch size to use for training.
     * The batch size is the number of training examples used to train a single forward and backward pass.
     * <p>
     * By default, the batch size will be dynamically configured to be ~0.2% of the number of examples in the training
     * set, capped at 256 - in general, we've found that larger batch sizes tend to work better for larger datasets.
     */
    private Integer batchSize;

    /**
     * The learning rate multiplier to use for training.
     * The fine-tuning learning rate is the original learning rate used for pretraining multiplied by this value.
     * <p>
     * By default, the learning rate multiplier is the 0.05, 0.1, or 0.2 depending on final batch_size
     * (larger learning rates tend to perform better with larger batch sizes).
     * We recommend experimenting with values in the range 0.02 to 0.2 to see what produces the best results.
     */
    private Double learningRateMultiplier;

    /**
     * The weight to use for loss on the prompt tokens.
     * This controls how much the model tries to learn to generate the prompt
     * (as compared to the completion which always has a weight of 1.0),
     * and can add a stabilizing effect to training when completions are short.
     * <p>
     * If prompts are extremely long (relative to completions), it may make sense to reduce this weight so as to
     * avoid over-prioritizing learning the prompt.
     */
    private Double promptLossWeight;

    /**
     * If set, we calculate classification-specific metrics such as accuracy and F-1 score using the validation set
     * at the end of every epoch. These metrics can be viewed in the results file.
     * <p>
     * In order to compute classification metrics, you must provide a validation_file.
     * Additionally, you must specify {@link CreateFineTuneRequest#classificationNClasses} for multiclass
     * classification or {@link CreateFineTuneRequest#classificationPositiveClass} for binary classification.
     */
    private Boolean computeClassificationMetrics;

    /**
     * The number of classes in a classification task.
     * <p>
     * This parameter is required for multiclass classification.
     */
    @JsonProperty("classification_n_classes")
    private Integer classificationNClasses;

    /**
     * The positive class in binary classification.
     * <p>
     * This parameter is needed to generate precision, recall, and F1 metrics when doing binary classification.
     */
    private String classificationPositiveClass;

    /**
     * If this is provided, we calculate F-beta scores at the specified beta values.
     * The F-beta score is a generalization of F-1 score. This is only used for binary classification.
     * <p>
     * With a beta of 1 (i.e. the F-1 score), precision and recall are given the same weight.
     * A larger beta score puts more weight on recall and less on precision.
     * A smaller beta score puts more weight on precision and less on recall.
     */
    private List<Double> classificationBetas;

    /**
     * A string of up to 40 characters that will be added to your fine-tuned model name.
     */
    private String suffix;

}
