package xyz.felh.openai.finetune;

import xyz.felh.openai.IOpenAiApiObject;
import lombok.Data;

@Data
public class HyperParameters implements IOpenAiApiObject {

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
