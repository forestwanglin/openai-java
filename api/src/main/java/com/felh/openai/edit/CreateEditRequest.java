package com.felh.openai.edit;

import com.felh.openai.IOpenAiApiRequest;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateEditRequest implements IOpenAiApiRequest {

    /**
     * string
     * Required
     * ID of the model to use. You can use the text-davinci-edit-001 or code-davinci-edit-001 model with this endpoint.
     */
    @NonNull
    private String model;

    /**
     * Optional
     * Defaults to ''
     * The input text to use as a starting point for the edit.
     */
    private String input;

    /**
     * Required
     * The instruction that tells the model how to edit the prompt.
     */
    @NonNull
    private String instruction;

    /**
     * Optional
     * Defaults to 1
     * How many edits to generate for the input and instruction.
     */
    private Integer n;

    /**
     * Optional
     * Defaults to 1
     * What sampling temperature to use, between 0 and 2. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic.
     * <p>
     * We generally recommend altering this or top_p but not both.
     */
    private Double temperature;

    /**
     * Optional
     * Defaults to 1
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers
     * the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising
     * the top 10% probability mass are considered.
     * We generally recommend altering this or temperature but not both.
     */
    private Double topP;

}
