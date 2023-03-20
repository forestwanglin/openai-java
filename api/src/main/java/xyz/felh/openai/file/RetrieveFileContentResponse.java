package xyz.felh.openai.file;

import xyz.felh.openai.IOpenAiApiResponse;
import lombok.Data;

@Data
public class RetrieveFileContentResponse implements IOpenAiApiResponse {

    private String prompt;

    private String completion;

}
