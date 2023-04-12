package xyz.felh.openai.gpt3.tokenizer;

import org.junit.jupiter.api.Test;
import xyz.felh.openai.completion.chat.ChatMessage;
import xyz.felh.openai.completion.chat.ChatMessageRole;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenCountTest {
    GPT3Tokenizer tokenizer = new GPT3Tokenizer(Encoding.CL100K_BASE);

    @Test
    void fromLinesJoined_gives_total_token_count_including_newlines() {
        assertEquals(0, TokenCount.fromLinesJoined(Collections.emptyList(), tokenizer));
        assertEquals(1, TokenCount.fromLinesJoined(Collections.singletonList("1"), tokenizer));
        assertEquals(3, TokenCount.fromLinesJoined(Arrays.asList("1", "2"), tokenizer));
        assertEquals(5, TokenCount.fromLinesJoined(Arrays.asList("1", "2", "3"), tokenizer));
    }


    public static void main(String[] args) {
        GPT3Tokenizer tokenizer = new GPT3Tokenizer(Encoding.CL100K_BASE);
        List<ChatMessage> a = new ArrayList<>();
        a.add(new ChatMessage(ChatMessageRole.USER, "请问我的名字有几个字"));
        int tokens = TokenCount.fromMessages(a, tokenizer, ChatFormatDescriptor.forModel("gpt-3.5-turbo"));
        System.out.println("tokens: " + tokens);
    }

}
