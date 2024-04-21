package xyz.felh.openai.assistant.vector.store.file;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiRequest;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateVectorStoreFileRequest implements IOpenAiApiRequest {

    /**
     * A File ID that the vector store should use. Useful for tools like file_search that can access files.
     */
    @JSONField(name = "file_id")
    @JsonProperty("file_id")
    private String fileId;

}