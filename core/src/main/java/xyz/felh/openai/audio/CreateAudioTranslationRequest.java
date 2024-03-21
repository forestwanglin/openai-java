package xyz.felh.openai.audio;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.chat.ChatMessage;

import java.util.Arrays;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateAudioTranslationRequest implements IOpenAiApiRequest {

    /**
     * Required must set filePath or file
     * <p>
     * The audio file object (not file name) translate, in one of these formats: flac, mp3, mp4, mpeg, mpga, m4a, ogg, wav, or webm.
     */
    @JSONField(name = "file_path")
    @JsonProperty("file_path")
    private String filePath;

    @JSONField(name = "file")
    @JsonProperty("file")
    private byte[] file;

    /**
     * The name must contain extension in order to let API know how the file's type
     */
    @NonNull
    @JSONField(name = "file_name")
    @JsonProperty("file_name")
    private String fileName;

    /**
     * Required
     * ID of the model to use. Only whisper-1 is currently available.
     */
    @NonNull
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * Optional
     * An optional text to guide the model's style or continue a previous audio segment. The prompt should match the audio language.
     */
    @JSONField(name = "prompt")
    @JsonProperty("prompt")
    private String prompt;

    /**
     * Optional Defaults to json
     * <p>
     * The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt.
     * <p>
     * See {@link ResponseFormat}
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private ResponseFormat responseFormat;

    /**
     * Optional
     * Defaults to 0
     * <p>
     * The sampling temperature, between 0 and 1. Higher values like 0.8 will make the output more random, while lower values like 0.2 will make it more focused and deterministic. If set to 0, the model will use log probability to automatically increase the temperature until certain thresholds are hit.
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    @Getter
    public enum ResponseFormat {

        JSON("json"),
        TEXT("text"),
        SRT("srt"),
        VERBOSE_JSON("verbose_json"),
        VTT("vtt");

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
