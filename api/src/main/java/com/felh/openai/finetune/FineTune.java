package com.felh.openai.finetune;

import com.felh.openai.ApiEntityWithId;
import com.felh.openai.file.File;
import lombok.Data;

import java.util.List;

@Data
public class FineTune extends ApiEntityWithId {

    /**
     * The name of the base model.
     */
    private String model;

    /**
     * The creation time in epoch seconds.
     */
    private Long createdAt;

    /**
     * List of events in this job's lifecycle. Null when getting a list of fine-tune jobs.
     */
    private List<FineTuneEvent> events;

    /**
     * The ID of the fine-tuned model, null if tuning job is not finished.
     * This is the id used to call the model.
     */
    private String fineTunedModel;

    /**
     * The specified hyper-parameters for the tuning job.
     */
    private HyperParameters hyperparams;

    /**
     * The ID of the organization this model belongs to.
     */
    private String organizationId;

    /**
     * Result files for this fine-tune job.
     */
    private List<File> resultFiles;

    /**
     * The status os the fine-tune job. "pending", "succeeded", or "cancelled"
     */
    private String status;

    /**
     * Training files for this fine-tune job.
     */
    private List<File> trainingFiles;

    /**
     * The last update time in epoch seconds.
     */
    private Long updatedAt;

    private List<File> validationFiles;

}
