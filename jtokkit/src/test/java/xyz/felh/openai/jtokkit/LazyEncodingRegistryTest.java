package xyz.felh.openai.jtokkit;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.chat.ChatMessageRole;
import xyz.felh.openai.jtokkit.api.Encoding;
import xyz.felh.openai.jtokkit.utils.TikTokenUtils;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.jupiter.api.Assertions.assertTrue;

@Slf4j
public class LazyEncodingRegistryTest extends BaseEncodingRegistryTest<LazyEncodingRegistry> {

    public LazyEncodingRegistryTest() {
        super(new LazyEncodingRegistry());
    }

    @Test
    @SuppressWarnings("unchecked")
    public void initializeWithEmptyEncoding() throws NoSuchFieldException, IllegalAccessException {
        final Field field = AbstractEncodingRegistry.class.getDeclaredField("encodings");
        field.setAccessible(true);
        final ConcurrentHashMap<String, Encoding> encodings = (ConcurrentHashMap<String, Encoding>) field.get(registry);
        assertTrue(encodings.isEmpty());
    }

    @Test
    public void testTokens() {
        log.info("test tokens");
        List<ChatMessage> messages = Arrays.asList(
                new ChatMessage(ChatMessageRole.SYSTEM, "You are a helpful assistant. Do not include pleasantries in your responses. Mark code language tag if there is code."),
                new ChatMessage(ChatMessageRole.USER, "Count 1 to 3"),
                new ChatMessage(ChatMessageRole.ASSISTANT, "1，2, 3"),
                new ChatMessage(ChatMessageRole.USER, " 中国和美国距离有多远？😄😄😄✅ "));
        log.info("{}", TikTokenUtils.estimateTokensInMessage(ChatCompletion.Model.GPT_4.getName(), messages.get(0)));
    }

}
