package xyz.felh.openai.chat;

import com.fasterxml.jackson.annotation.JsonValue;

/**
 * @author Forest Wang
 * @package xyz.felh.openai.chat
 * @class ChatModality
 * @email forestwanglin@gmail.cn
 * @date 2024/10/24
 */
public enum ChatModality {

    TEXT("text"),

    AUDIO("audio");

    private final String value;

    ChatModality(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
