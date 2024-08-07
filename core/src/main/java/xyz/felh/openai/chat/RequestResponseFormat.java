package xyz.felh.openai.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import xyz.felh.openai.IOpenAiBean;

import java.util.Arrays;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestResponseFormat implements IOpenAiBean {

    /**
     * Must be one of text or json_object.
     * <p>
     * String, Optional
     * <p>
     * Defaults to text, See {@link TypeValue}
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private TypeValue type;

    /**
     * It is required if type = json_schema
     */
    @JSONField(name = "json_schema")
    @JsonProperty("json_schema")
    private JsonSchema jsonSchema;

    public enum TypeValue {
        TEXT("text"),
        JSON_OBJECT("json_object"),
        JSON_SCHEMA("json_schema");

        private final String value;

        TypeValue(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static TypeValue findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class JsonSchema implements IOpenAiBean {

        /**
         * The name of the response format. Must be a-z, A-Z, 0-9, or contain underscores and dashes, with a maximum length of 64.
         */
        @NonNull
        @JSONField(name = "name")
        @JsonProperty("name")
        private String name;

        /**
         * string
         * <p>
         * Optional
         * A description of what the response format is for, used by the model to determine how to respond in the format.
         */
        @JSONField(name = "description")
        @JsonProperty("description")
        private String description;

        /**
         * The schema for the response format, described as a JSON Schema object.
         * <p>
         * See <a href="https://platform.openai.com/docs/guides/structured-outputs">Structured Outputs</a>
         */
        @JSONField(name = "schema")
        @JsonProperty("schema")
        private Object schema;

        /**
         * boolean or null
         * <p>
         * Optional
         * Defaults to false
         * Whether to enable strict schema adherence when generating the output. If set to true, the model will always follow the exact schema defined in the schema field. Only a subset of JSON Schema is supported when strict is true. To learn more, read the Structured Outputs guide.
         */
        @JSONField(name = "strict")
        @JsonProperty("strict")
        private Boolean strict;

    }

}
