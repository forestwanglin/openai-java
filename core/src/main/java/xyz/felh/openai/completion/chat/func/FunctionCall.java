package xyz.felh.openai.completion.chat.func;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * gpt return
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class FunctionCall {

    private String name;

    /**
     * json string
     */
    private String arguments;

}
