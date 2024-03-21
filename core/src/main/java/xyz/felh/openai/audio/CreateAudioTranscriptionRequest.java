package xyz.felh.openai.audio;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.util.List;

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

    /**
     * The timestamp granularities to populate for this transcription.
     * <p>
     * response_format must be set verbose_json to use timestamp granularities.
     * Either or both of these options are supported: word, or segment. Note:
     * There is no additional latency for segment timestamps, but generating word timestamps incurs additional latency.
     */
    @JSONField(name = "timestamp_granularities")
    @JsonProperty("timestamp_granularities")
    private List<String> timestampGranularities;

}
