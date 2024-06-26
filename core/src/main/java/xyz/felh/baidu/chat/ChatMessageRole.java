package xyz.felh.baidu.chat;

import com.fasterxml.jackson.annotation.JsonValue;

public enum ChatMessageRole {

    /**
     * content - Required The contents of the user message.
     * role - Required The role of the messages author, in this case user.
     */
    USER("user"),
    /**
     * content - Required The contents of the assistant message.
     * role - Required The role of the messages author, in this case assistant.
     * tool_calls -  Optional The tool calls generated by the model, such as function calls.
     */
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
