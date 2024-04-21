package xyz.felh.openai.assistant;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiApiObject;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;
import java.util.Map;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AssistToolResources implements IOpenAiBean {

    @JSONField(name = "code_interpreter")
    @JsonProperty("code_interpreter")
    private ToolResourcesCodeInterpreter codeInterpreter;

    @JSONField(name = "file_search")
    @JsonProperty("file_search")
    private ToolResourcesFileSearch fileSearch;


    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolResourcesCodeInterpreter implements IOpenAiApiObject {

        /**
         * A list of file IDs made available to the code_interpreter tool. There can be a maximum of 20 files associated with the tool.
         */
        @JSONField(name = "file_ids")
        @JsonProperty("file_ids")
        private List<String> fileIds;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolResourcesFileSearch implements IOpenAiApiObject {

        /**
         * A list of file IDs made available to the code_interpreter tool. There can be a maximum of 20 files associated with the tool.
         */
        @JSONField(name = "vector_store_ids")
        @JsonProperty("vector_store_ids")
        private List<String> vectorStoreIds;

        /**
         * A helper to create a vector store with file_ids and attach it to this assistant. There can be a maximum of 1 vector store attached to the assistant.
         */
        @JSONField(name = "vector_stores")
        @JsonProperty("vector_stores")
        private List<ToolResourcesFileSearchVectorStore> vectorStores;

    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class ToolResourcesFileSearchVectorStore implements IOpenAiApiObject {

        /**
         * A list of file IDs to add to the vector store. There can be a maximum of 10000 files in a vector store.
         */
        @JSONField(name = "file_ids")
        @JsonProperty("file_ids")
        private List<String> fileIds;

        /**
         * Set of 16 key-value pairs that can be attached to a vector store. This can be useful for storing additional information about the vector store in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
         */
        @JSONField(name = "metadata")
        @JsonProperty("metadata")
        private Map<?, ?> metadata;

    }
}