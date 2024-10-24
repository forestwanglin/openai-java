package xyz.felh.openai.chat;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Forest Wang
 * @package xyz.felh.openai.chat
 * @class ChatAudioFormat
 * @email forestwanglin@gmail.cn
 * @date 2024/10/24
 */
public enum ChatAudioFormat {

    WAV("wav"),
    MP3("mp3"),
    FLAC("flac"),
    OPUS("opus"),
    PCM16("pcm16");

    private final String value;

    ChatAudioFormat(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
