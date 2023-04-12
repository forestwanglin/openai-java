package xyz.felh.openai.gpt3.tokenizer;

import xyz.felh.openai.completion.chat.ChatMessage;

import java.util.Iterator;
import java.util.List;
import java.util.ServiceLoader;
import java.util.stream.StreamSupport;

/**
 * Utility class for calculating token count in text and chat messages.
 * <p>
 * This class provides methods for counting tokens in text strings and lists of
 * {@link ChatMessage} objects using a {@link GPT3Tokenizer}. It also supports pluggable
 * {@link Calculator} implementations, allowing customization of token counting logic.</p>
 * <p>
 * To plug in a custom {@code Calculator} implementation, follow these steps:
 * <ol>
 *   <li>Create a new class that implements the {@code TokenCount.Calculator} interface.</li>
 *   <li>In your custom {@code Calculator} implementation, provide the logic for the
 *   {@code countTokensFromString} and {@code countTokensFromMessages} methods, which
 *   handle token counting for text strings and chat messages, respectively.</li>
 *   <li>Create a file named {@code xyz.felh.openai.gpt3.tokenizer.TokenCount$Calculator} in the {@code META-INF/services}
 *   directory of your project. This file is used by the {@code ServiceLoader} mechanism to
 *   discover and load your custom {@code Calculator} implementation.</li>
 *   <li>In the {@code TokenCount$Calculator} file, specify the fully qualified name of your
 *   custom {@code Calculator} implementation. For example: {@code com.example.CustomCalculator}</li>
 *   <li>When your application starts, the {@code ServiceLoader} mechanism will look for the
 *   {@code TokenCount$Calculator} file in the {@code META-INF/services} directory. If it finds
 *   the file and can load the specified custom {@code Calculator} implementation, it will use
 *   that implementation instead of the default {@code TokenCount.StandardCalculator}.</li>
 * </ol>
 */
public class TokenCount {

    /**
     * Calculates the total token count from a list of lines using the given tokenizer,
     * including newline tokens between lines.
     *
     * @param lines     an iterable of lines of text (boring)
     * @param tokenizer the magic thing that tokenizes text
     * @return the total token count, including newline tokens between lines
     */
    public static int fromLinesJoined(Iterable<String> lines, GPT3Tokenizer tokenizer) {
        int tokenCount = StreamSupport.stream(lines.spliterator(), false)
                .mapToInt(line -> fromString(line, tokenizer) + 1)
                .sum();
        return Math.max(0, tokenCount - 1); // subtract 1 token for the last newline character
    }

    /**
     * Calculates the token count for a given text string using the provided tokenizer.
     *
     * @param text      the text string to tokenize (probably lorem ipsum or something)
     * @param tokenizer the tokenizer to use for token counting
     * @return the token count for the input text
     */
    public static int fromString(String text, GPT3Tokenizer tokenizer) {
        return getCalculator().countTokensFromString(text, tokenizer);
    }

    /**
     * Calculates the token count for a list of chat messages using the provided tokenizer
     * and chat format descriptor.
     *
     * @param messages   a list of chat messages (probably gossip)
     * @param tokenizer  the tokenizer to use for token counting
     * @param chatFormat the descriptor defining the chat format
     * @return the token count for the input chat messages
     */
    public static int fromMessages(List<ChatMessage> messages, GPT3Tokenizer tokenizer, ChatFormatDescriptor chatFormat) {
        return getCalculator().countTokensFromMessages(messages, tokenizer, chatFormat);
    }

    /**
     * Returns the current {@link Calculator} instance.
     *
     * @return the calculator instance
     */
    public static Calculator getCalculator() {
        return CalculatorHolder.instance;
    }

    /**
     * Interface for pluggable token counting logic.
     */
    public interface Calculator {
        int countTokensFromString(String text, GPT3Tokenizer tokenizer);

        int countTokensFromMessages(List<ChatMessage> messages, GPT3Tokenizer tokenizer, ChatFormatDescriptor chatFormat);
    }

    /**
     * Default implementation of the {@link Calculator} interface.
     */
    public static class StandardCalculator implements Calculator {

        @Override
        public int countTokensFromString(String text, GPT3Tokenizer tokenizer) {
            return tokenizer.encode(text).size();
        }

        @Override
        public int countTokensFromMessages(List<ChatMessage> messages, GPT3Tokenizer tokenizer, ChatFormatDescriptor chatFormat) {
            int tokenCount = 0;
            for (ChatMessage message : messages) {
                tokenCount += chatFormat.getExtraTokenCountPerMessage();
                if (message.getRole() != null)
                    tokenCount += tokenizer.encode(message.getRole().value()).size();
                if (message.getContent() != null)
                    tokenCount += tokenizer.encode(message.getContent()).size();
            }
            tokenCount += chatFormat.getExtraTokenCountPerRequest(); // Every reply is primed with <im_start>assistant\n
            return tokenCount;
        }
    }

    private static final class CalculatorHolder {
        private static final Calculator instance = loadCalculator();

        private static Calculator loadCalculator() {
            Iterator<Calculator> it = ServiceLoader.load(Calculator.class).iterator();
            if (it.hasNext()) {
                return it.next();
            }
            return new StandardCalculator();
        }
    }

}
