package xyz.felh.openai.audio;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Getter;

import java.util.Arrays;

@Getter
public enum AudioModelType {

    WHISPER_1("whisper-1"),
    TTS_1("tts-1"),
    TTS_1_HD("tts-1-hd");

    private final String value;

    AudioModelType(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

    public static AudioModelType findByValue(String value) {
        return Arrays.stream(values()).filter(it -> it.value().equals(value)).findFirst().orElse(null);
    }

}
