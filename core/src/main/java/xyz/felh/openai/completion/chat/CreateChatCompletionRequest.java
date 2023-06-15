package xyz.felh.openai.completion.chat;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.completion.BaseCreateCompletionRequest;
import xyz.felh.openai.completion.chat.func.Function;

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

    /**
     * A list of functions the model may generate JSON inputs for.
     */
    private List<Function> functions;

    /**
     * Controls how the model responds to function calls. "none" means the model does not call a function,
     * and responds to the end-user. "auto" means the model can pick between an end-user or calling a function.
     * Specifying a particular function via {"name":\ "my_function"} forces the model to call that function.
     * "none" is the default when no functions are present. "auto" is the default if functions are present.
     */
    private Object functionCall;

}
