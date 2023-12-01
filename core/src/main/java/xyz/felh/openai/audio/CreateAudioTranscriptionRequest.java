package xyz.felh.openai.audio;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
public class CreateAudioTranscriptionRequest extends CreateAudioTranslationRequest {

    /**
     * Optional
     * The language of the input audio. Supplying the input language in ISO-639-1 format will improve accuracy and latency.
     */
    @JSONField(name = "language")
    @JsonProperty("language")
    private String language;

}
