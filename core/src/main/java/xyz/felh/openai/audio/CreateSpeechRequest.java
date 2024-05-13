package xyz.felh.openai.audio;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.IOpenAiApiRequest;

import java.util.Arrays;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateSpeechRequest implements IOpenAiApiRequest {

    /**
     * One of the available <a href="https://platform.openai.com/docs/models/tts">TTS models</a>: tts-1 or tts-1-hd
     */
    @NonNull
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * The text to generate audio for. The maximum length is 4096 characters.
     */
    @NonNull
    @JSONField(name = "input")
    @JsonProperty("input")
    private String input;

    /**
     * The voice to use when generating the audio. Supported voices are alloy, echo, fable, onyx, nova, and shimmer. Previews of the voices are available in the <a href="https://platform.openai.com/docs/guides/text-to-speech/voice-options">Text to speech guide</a>.
     * <p>
     * See {@link Voice}
     */
    @NonNull
    @JSONField(name = "voice")
    @JsonProperty("voice")
    private Voice voice;

    /**
     * string Optional Defaults to mp3
     * <p>
     * The format to audio in. Supported formats are mp3, opus, aac, and flac.
     * <p>
     * See {@link ResponseFormat}
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    /**
     * number Optional Defaults to 1
     * <p>
     * The speed of the generated audio. Select a value from 0.25 to 4.0. 1.0 is the default.
     */
    @JSONField(name = "speed")
    @JsonProperty("speed")
    private Double speed;

    @Getter
    public enum Voice {

        ALLOY("alloy"),
        ECHO("echo"),
        FABLE("fable"),
        ONYX("onyx"),
        NOVA("nova"),
        SHIMMER("shimmer"),
        ;

        private final String value;

        Voice(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Voice findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

    @Getter
    public enum ResponseFormat {

        MP3("mp3"),
        OPUS("opus"),
        AAC("aac"),
        FLAC("flac"),
        WAV("wav");

        private final String value;

        ResponseFormat(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static ResponseFormat findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

}