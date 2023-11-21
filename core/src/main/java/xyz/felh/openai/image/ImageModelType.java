package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

public enum ImageModelType {

    DALL_E_2("dall-e-2"),

    DALL_E_3("dall-e-3");

    private final String value;

    ImageModelType(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static ImageModelType findByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value().equals(value)).findFirst().orElse(null);
    }

}
