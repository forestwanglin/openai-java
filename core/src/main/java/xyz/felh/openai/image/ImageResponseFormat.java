package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ImageResponseFormat {

    URL("url"),

    B64_JSON("b64_json");

    private final String value;

    ImageResponseFormat(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static ImageResponseFormat findByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value().equals(value)).findFirst().orElse(null);
    }

}
