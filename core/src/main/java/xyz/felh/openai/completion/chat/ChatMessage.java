package xyz.felh.openai.completion.chat;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage {

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

}
