package xyz.felh.openai.completion.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import xyz.felh.openai.completion.chat.func.FunctionCall;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

    public ChatMessage(ChatMessageRole role, String content) {
        this.role = role;
        this.content = content;
    }

    public ChatMessage(ChatMessageRole role, String content, String name) {
        this.role = role;
        this.content = content;
        this.name = name;
    }

    /**
     * Must be either 'system', 'user', or 'assistant'.<br>
     * You may use {@link ChatMessageRole} enum.
     */
    private ChatMessageRole role;

    /**
     * The contents of message
     */
    private String content;

    /**
     * The name of the author of this message. May contain a-z, A-Z, 0-9, and underscores, with a maximum length of 64 characters.
     */
    private String name;

    /**
     * Optional
     * The name and arguments of a function that should be called, as generated by the model.
     */
    private FunctionCall functionCall;
}
