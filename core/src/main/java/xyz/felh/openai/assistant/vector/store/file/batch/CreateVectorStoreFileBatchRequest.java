package xyz.felh.openai.assistant.vector.store.file.batch;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.List;


@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateVectorStoreFileBatchRequest implements IOpenAiApiRequest {

    /**
     * A list of File IDs that the vector store should use. Useful for tools like file_search that can access files.
     */
    @JSONField(name = "file_ids")
    @JsonProperty("file_ids")
    private List<String> fileIds;

}