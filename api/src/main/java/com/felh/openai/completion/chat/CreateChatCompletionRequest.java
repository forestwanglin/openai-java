package com.felh.openai.completion.chat;

import com.felh.openai.completion.BaseCreateCompletionRequest;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

@Data
//@Builder
@SuperBuilder(toBuilder = true)
@AllArgsConstructor
@NoArgsConstructor
@ToString(callSuper = true)
public class CreateChatCompletionRequest extends BaseCreateCompletionRequest {

//    /**
//     * ID of the model to use. Currently, only gpt-3.5-turbo and gpt-3.5-turbo-0301 are supported.
//     */
//    private String model;

    /**
     * The messages to generate chat completions for, in the <a
     * href="https://platform.openai.com/docs/guides/chat/introduction">chat format</a>.<br>
     * see {@link ChatMessage}
     */
    private List<ChatMessage> messages;

}
