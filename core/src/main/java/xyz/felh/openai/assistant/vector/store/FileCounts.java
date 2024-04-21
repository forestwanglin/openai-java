package xyz.felh.openai.assistant.vector.store;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class FileCounts implements IOpenAiBean {

    /**
     * The number of files that are currently being processed.
     */
    @JSONField(name = "in_progress")
    @JsonProperty("in_progress")
    private Integer inProgress;

    /**
     * The number of files that have been successfully processed.
     */
    @JSONField(name = "completed")
    @JsonProperty("completed")
    private Integer completed;

    /**
     * The number of files that have failed to process.
     */
    @JSONField(name = "failed")
    @JsonProperty("failed")
    private Integer failed;

    /**
     * The number of files that were cancelled.
     */
    @JSONField(name = "cancelled")
    @JsonProperty("cancelled")
    private Integer cancelled;

    /**
     * The total number of files.
     */
    @JSONField(name = "total")
    @JsonProperty("total")
    private Integer total;


}
