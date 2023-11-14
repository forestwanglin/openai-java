package xyz.felh.openai.assistant.file;

import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

@Data
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CreateAssistantFileRequest implements IOpenAiApiRequest {

    /**
     * A File ID (with purpose="assistants") that the assistant should use. Useful for tools like retrieval and code_interpreter that can access files.
     */
    @NonNull
    private String fileId;

}