package xyz.felh.openai.fineTuning;

import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class Hyperparameters implements IOpenAiApiObject {

    /**
     * string or integer
     * <p>
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset. "auto" decides the optimal number of epochs based on the size of the dataset. If setting the number manually, we support any number between 1 and 50 epochs.
     */
    private Object nEpochs;

    /**
     * string or integer Optional Defaults to auto
     * <p>
     * Number of examples in each batch. A larger batch size means that model parameters are updated less frequently, but with lower variance.
     */
    private Object batchSize;

    /**
     * string or number Optional Defaults to auto
     * <p>
     * Scaling factor for the learning rate. A smaller learning rate may be useful to avoid overfitting.
     */
    private Object learningRateMultiplier;

}
