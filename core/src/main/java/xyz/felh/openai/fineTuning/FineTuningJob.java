package xyz.felh.openai.fineTuning;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.chat.ChatMessage;

import java.util.Arrays;
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
     * For fine-tuning jobs that have failed, this will contain more information on the cause of the failure.
     */
    private FineTuningJobError error;

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
     * <p>
     * See {@link FineTuningJobStatus}
     */
    private FineTuningJobStatus status;

    /**
     * The hyperparameters used for the fine-tuning job. See the <a href="https://platform.openai.com/docs/guides/fine-tuning">fine-tuning</a> guide for more details.
     * <p>
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

    @Getter
    public enum FineTuningJobStatus {

        VALIDATING_FILES("validating_files"),
        QUEUED("queued"),
        RUNNING("running"),
        SUCCEEDED("succeeded"),
        FAILED("failed"),
        CANCELLED("cancelled");

        private final String value;

        FineTuningJobStatus(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static FineTuningJobStatus findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

}