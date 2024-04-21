package xyz.felh.openai.assistant.vector.store;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;
import xyz.felh.openai.OpenAiApiObjectWithId;

import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class VectorStore extends OpenAiApiObjectWithId {

    public static String OBJECT = "vector_store";

    /**
     * The Unix timestamp (in seconds) for when the vector store was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The name of the vector store.
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

    /**
     * The byte size of the vector store.
     */
    @JSONField(name = "bytes")
    @JsonProperty("bytes")
    private Integer bytes;

    @JSONField(name = "file_counts")
    @JsonProperty("file_counts")
    private FileCounts fileCounts;

    /**
     * The status of the vector store, which can be either expired, in_progress, or completed. A status of completed indicates that the vector store is ready for use.
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    /**
     * The expiration policy for a vector store.
     */
    @JSONField(name = "expires_after")
    @JsonProperty("expires_after")
    private ExpiresAfter expiresAfter;

    /**
     * The Unix timestamp (in seconds) for when the vector store will expire.
     */
    @JSONField(name = "expires_at")
    @JsonProperty("expires_at")
    private Long expiresAt;

    /**
     * The Unix timestamp (in seconds) for when the vector store was last active.
     */
    @JSONField(name = "last_active_at")
    @JsonProperty("last_active_at")
    private Long lastActiveAt;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    @Getter
    public enum Status {

        EXPIRED("expired"),
        IN_PROGRESS("in_progress"),
        COMPLETED("completed");

        Status(final String value) {
            this.value = value;
        }

        private final String value;

        @JsonValue
        public String value() {
            return value;
        }

    }


    @Data
    @Builder
    @AllArgsConstructor
    @NoArgsConstructor
    public static class ExpiresAfter implements IOpenAiBean {

        /**
         * Anchor timestamp after which the expiration policy applies. Supported anchors: last_active_at.
         */
        @JSONField(name = "anchor")
        @JsonProperty("anchor")
        private String anchor;

        /**
         * The number of days after the anchor time that the vector store will expire.
         */
        @JSONField(name = "days")
        @JsonProperty("days")
        private Integer days;

    }

}
