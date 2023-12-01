package xyz.felh.openai.assistant.file;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JSONField(name = "file_id")
    @JsonProperty("file_id")
    private String fileId;

}