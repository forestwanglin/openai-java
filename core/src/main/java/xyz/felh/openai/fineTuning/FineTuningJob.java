package xyz.felh.openai.fineTuning;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Long createdAt;

    /**
     * The Unix timestamp (in seconds) for when the fine-tuning job was finished. The value will be null if the fine-tuning job is still running.
     */
    @JSONField(name = "finished_at")
    @JsonProperty("finished_at")
    private Long finishedAt;

    /**
     * The base model that is being fine-tuned.
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * For fine-tuning jobs that have failed, this will contain more information on the cause of the failure.
     */
    @JSONField(name = "error")
    @JsonProperty("error")
    private FineTuningJobError error;

    /**
     * The name of the fine-tuned model that is being created. The value will be null if the fine-tuning job is still running.
     */
    @JSONField(name = "fine_tuned_model")
    @JsonProperty("fine_tuned_model")
    private String fineTunedModel;

    /**
     * The organization that owns the fine-tuning job.
     */
    @JSONField(name = "organization_id")
    @JsonProperty("organization_id")
    private String organizationId;

    /**
     * The current status of the fine-tuning job, which can be either validating_files, queued, running, succeeded, failed, or cancelled.
     * <p>
     * See {@link FineTuningJobStatus}
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private FineTuningJobStatus status;

    /**
     * The hyperparameters used for the fine-tuning job. See the <a href="https://platform.openai.com/docs/guides/fine-tuning">fine-tuning</a> guide for more details.
     * <p>
     * See {@link Hyperparameters}
     */
    @JSONField(name = "hyperparameters")
    @JsonProperty("hyperparameters")
    private Hyperparameters hyperparameters;

    /**
     * The file ID used for training.
     */
    @JSONField(name = "training_file")
    @JsonProperty("training_file")
    private String trainingFile;

    /**
     * string or null
     * The file ID used for validation.
     */
    @JSONField(name = "validation_file")
    @JsonProperty("validation_file")
    private String validationFile;

    /**
     * The compiled results file ID(s) for the fine-tuning job. You can retrieve the results with the <a href="https://platform.openai.com/docs/api-reference/files/retrieve-contents">Files API</a>.
     */
    @JSONField(name = "result_files")
    @JsonProperty("result_files")
    private List<String> resultFiles;

    /**
     * The total number of billable tokens processed by this fine tuning job.
     */
    @JSONField(name = "trained_tokens")
    @JsonProperty("trained_tokens")
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