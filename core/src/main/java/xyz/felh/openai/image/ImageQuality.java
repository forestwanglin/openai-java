package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Arrays;

/**
 * This param is only supported for dall-e-3
 */
public enum ImageQuality {

    STANDARD("standard"),

    HD("hd");

    private final String value;

    ImageQuality(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static ImageQuality findByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value().equals(value)).findFirst().orElse(null);
    }

}
