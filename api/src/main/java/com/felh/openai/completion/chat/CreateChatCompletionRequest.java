package com.felh.openai.completion.chat;

import com.felh.openai.completion.BaseCreateCompletionRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CreateChatCompletionRequest extends BaseCreateCompletionRequest {

    /**
     * The messages to generate chat completions for, in the <a
     * href="https://platform.openai.com/docs/guides/chat/introduction">chat format</a>.<br>
     * see {@link ChatMessage}
     */
    @NonNull
    private List<ChatMessage> messages;

}
