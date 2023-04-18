package xyz.felh.openai.audio;

import xyz.felh.openai.IOpenAiApiRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateAudioTranslationRequest implements IOpenAiApiRequest {

    /**
     * Required
     * The audio file to transcribe, in one of these formats: mp3, mp4, mpeg, mpga, m4a, wav, or webm.
     */
    @NonNull
    private String file;

    /**
     * Required
     * ID of the model to use. Only whisper-1 is currently available.
     */
    @NonNull
    private String model;

    /**
     * Optional
     * An optional text to guide the model's style or continue a previous audio segment. The prompt should match the audio language.
     */
    private String prompt;

    /**
     * Optional
     * Defaults to json
     * The format of the transcript output, in one of these options: json, text, srt, verbose_json, or vtt.
     */
    private String responseFormat;

    /**
     * Optional
     * Defaults to 0
     * The sampling temperature, between 0 and 1. Higher values like 0.8 will make the output more random,
     * while lower values like 0.2 will make it more focused and deterministic. If set to 0,
     * the model will use log probability to automatically increase the temperature until certain thresholds are hit.
     */
    private Double temperature;

}
