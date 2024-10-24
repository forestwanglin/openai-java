package xyz.felh.openai.chat;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Forest Wang
 * @package xyz.felh.openai.chat
 * @class ChatAudioVoice
 * @email forestwanglin@gmail.cn
 * @date 2024/10/24
 */
public enum ChatAudioVoice {

    ALLOY("alloy"),
    ECHO("echo"),
    FABLE("fable"),
    ONYX("onyx"),
    NOVA("nova"),
    SHIMMER("shimmer");

    private final String value;

    ChatAudioVoice(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
