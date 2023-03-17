package com.felh.openai.completion.chat;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatMessageRole {

    SYSTEM("system"),
    USER("user"),
    ASSISTANT("assistant");

    private final String value;

    ChatMessageRole(final String value) {
        this.value = value;
    }

    @JsonValue
    public String value() {
        return value;
    }

}
