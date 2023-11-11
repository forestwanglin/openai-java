package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * This param is only supported for dall-e-3
 */
public enum ImageStyle {

    VIVID("vivid"),

    NATURAL("natural");

    private final String value;

    ImageStyle(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
