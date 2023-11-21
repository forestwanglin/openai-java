package xyz.felh.openai.image;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ImageSize {

    // dall-e-2
    R_256X256("256x256"),
    R_512X512("512x512"),

    // dall-e-2 and dall-e-3
    R_1024X1024("1024x1024"),

    // dall-e-3
    R_1792X1024("1792x1024"),
    R_1024X1792("1024x1792");

    private final String value;

    ImageSize(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
