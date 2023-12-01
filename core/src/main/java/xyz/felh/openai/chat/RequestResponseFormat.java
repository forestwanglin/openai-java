package xyz.felh.openai.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
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


    public enum TypeValue {
        TEXT("text"),
        JSON_OBJECT("json_object");

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

}
