package xyz.felh.openai.completion.chat;

import lombok.*;
import lombok.experimental.SuperBuilder;
import xyz.felh.openai.completion.BaseCreateCompletionRequest;
import xyz.felh.openai.completion.chat.tool.Tool;

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
     * This feature is in Beta. If specified, our system will make a best effort to sample deterministically,
     * such that repeated requests with the same seed and parameters should return the same result.
     * Determinism is not guaranteed, and you should refer to the system_fingerprint response parameter
     * to monitor changes in the backend.
     */
    private Integer seed;

    /**
     * A list of functions the model may generate JSON inputs for.
     */
    private List<Tool> tools;

    /**
     * Controls which (if any) function is called by the model.
     * none means the model will not call a function and instead generates a message.
     * auto means the model can pick between generating a message or calling a function.
     * Specifying a particular function via {"type: "function", "function": {"name": "my_function"}} forces the model to call that function.
     */
    private Object toolChoice;

}
