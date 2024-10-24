package xyz.felh.openai.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.IOpenAiBean;

/**
 * @author Forest Wang
 * @package xyz.felh.openai.chat
 * @class ChatAudio
 * @email forestwanglin@gmail.cn
 * @date 2024/10/24
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatAudio implements IOpenAiBean {

    /**
     * Specifies the voice type. Supported voices are alloy, echo, fable, onyx, nova, and shimmer.
     */
    @JSONField(name = "voice")
    @JsonProperty("voice")
    private ChatAudioVoice voice;

    /**
     * Specifies the output audio format. Must be one of wav, mp3, flac, opus, or pcm16.
     */
    @JSONField(name = "format")
    @JsonProperty("format")
    private ChatAudioFormat format;

}
