package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

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

}
