package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

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

}
