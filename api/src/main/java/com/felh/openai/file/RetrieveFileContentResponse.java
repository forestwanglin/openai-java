package com.felh.openai.file;

import com.felh.openai.IOpenAiApiResponse;
import lombok.Data;

@Data
public class RetrieveFileContentResponse implements IOpenAiApiResponse {

    private String prompt;

    private String completion;

}
