package xyz.felh.openai.chat.tool;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum Type {

    FUNCTION("function"),
    ;

    private final String value;

    Type(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static Type findByValue(String value) {
        return Arrays.stream(values()).filter(it ->
                it.value.equals(value)).findFirst().orElse(null);
    }

}