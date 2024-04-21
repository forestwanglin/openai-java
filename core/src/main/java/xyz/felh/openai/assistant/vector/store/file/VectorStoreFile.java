package xyz.felh.openai.assistant.vector.store.file;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.openai.LastError;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
public class VectorStoreFile extends OpenAiApiObjectWithId {

    public static String OBJECT = "vector_store.file";

    /**
     * The Unix timestamp (in seconds) for when the vector store was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The ID of the vector store that the File is attached to.
     */
    @JSONField(name = "vector_store_id")
    @JsonProperty("vector_store_id")
    private String vectorStoreId;

    /**
     * The status of the vector store file, which can be either in_progress, completed, cancelled, or failed. The status completed indicates that the vector store file is ready for use.
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    /**
     * The last error associated with this vector store file. Will be null if there are no errors.
     */
    @JSONField(name = "last_error")
    @JsonProperty("last_error")
    private LastError lastError;

    @Getter
    public enum Status {

        IN_PROGRESS("in_progress"),
        COMPLETED("completed"),
        CANCELLED("cancelled"),
        FAILED("failed");

        Status(final String value) {
            this.value = value;
        }

        private final String value;

        @JsonValue
        public String value() {
            return value;
        }

    }


}