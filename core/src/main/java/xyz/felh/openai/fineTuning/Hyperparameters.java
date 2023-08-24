package xyz.felh.openai.fineTuning;

import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

@Data
public class Hyperparameters implements IOpenAiApiObject {

    /**
     * The number of epochs to train the model for. An epoch refers to one full cycle through the training dataset.
     * "Auto" decides the optimal number of epochs based on the size of the dataset. If setting the number manually,
     * we support any number between 1 and 50 epochs.
     */
    String nEpochs;

}
