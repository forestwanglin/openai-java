package xyz.felh.openai.batch;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.openai.IOpenAiApiRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateBatchRequest implements IOpenAiApiRequest {

    /**
     * The ID of an uploaded file that contains requests for the new batch.
     * <p>
     * See upload file for how to upload a file.
     * <p>
     * Your input file must be formatted as a JSONL file, and must be uploaded with the purpose batch.
     */
    @NonNull
    @JSONField(name = "input_file_id")
    @JsonProperty("input_file_id")
    private String inputFileId;

    /**
     * The endpoint to be used for all requests in the batch. Currently only /v1/chat/completions is supported.
     */
    @NonNull
    @JSONField(name = "endpoint")
    @JsonProperty("endpoint")
    private String endpoint;

    /**
     * The time frame within which the batch should be processed. Currently only '24h' is supported.
     */
    @NonNull
    @JSONField(name = "completion_window")
    @JsonProperty("completion_window")
    private String completionWindow;

    /**
     * Optional custom metadata for the batch.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Object metadata;

}
