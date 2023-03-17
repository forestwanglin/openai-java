package com.felh.openai.finetune;

import com.felh.openai.IApiEntity;
import lombok.Data;

@Data
public class HyperParameters implements IApiEntity {

    /**
     * The batch size to use for training.
     */
    String batchSize;

    /**
     * The learning rate multiplier to use for training.
     */
    Double learningRateMultiplier;

    /**
     * The number of epochs to train the model for.
     */
    Integer nEpochs;

    /**
     * The weight to use for loss on the prompt tokens.
     */
    Double promptLossWeight;

}
