package xyz.felh.openai.completion.chat;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.completion.BaseCreateCompletionRequest;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor(force = true)
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
