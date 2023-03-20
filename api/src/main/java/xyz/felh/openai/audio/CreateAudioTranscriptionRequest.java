package xyz.felh.openai.audio;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateAudioTranscriptionRequest extends CreateAudioTranslationRequest {

    /**
     * Optional
     * The language of the input audio. Supplying the input language in ISO-639-1 format will improve accuracy and latency.
     */
    private String language;

}
