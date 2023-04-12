package xyz.felh.openai.gpt3.tokenizer;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.*;
import java.util.Map.Entry;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import java.util.stream.Collectors;

import static java.nio.charset.StandardCharsets.ISO_8859_1;
import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.stream.Collectors.toMap;

/**
 * Java implementation of the GPT3/4 tokenizer.
 * <p>
 * Modifications:
 * <ul>
 *     <li>[MB] 2023-03-25: Repackaged from <a href="https://github.com/openai/tiktoken">Tiktoken</a> for inclusion in gpt3-tokenizer-java.</li>
 *     <li>[MB] 2023-04-02: Major refactoring for cleaner code and improved performance.</li>
 * </ul>
 */
public class GPT3Tokenizer {

    private final Map<ByteSequence, Integer> encoder;
    private final Map<Integer, ByteSequence> decoder;
    private final Map<String, Integer> specialTokensEncoder;
    private final Map<Integer, String> specialTokensDecoder;
    private final Pattern pattern;
    private final Pattern specialPattern;

    public GPT3Tokenizer(Encoding encoding) {
        this.encoder = encoding.mergeableRanks();
        this.decoder = encoder.entrySet().stream()
                .collect(toMap(Entry::getValue, Entry::getKey));
        this.specialTokensEncoder = encoding.getSpecialTokens();
        this.specialTokensDecoder = specialTokensEncoder.entrySet().stream()
                .collect(toMap(Entry::getValue, Entry::getKey));
        this.pattern = encoding.getPattern();
        this.specialPattern = createSpecialRegex(encoding.getSpecialTokens());
    }

    protected Pattern createSpecialRegex(Map<String, ?> specialTokensEncoder) {
        String joinedPattern = specialTokensEncoder.keySet().stream()
                .map(Pattern::quote)
                .collect(Collectors.joining("|"));
        return Pattern.compile(joinedPattern);
    }

    public String decode(List<Integer> tokens) {
        return decodeImpl(tokens);
    }

    protected String decodeImpl(List<Integer> tokens) {
        try {
            ByteArrayOutputStream result = new ByteArrayOutputStream();

            for (Integer token : tokens) {
                ByteSequence bytes = decoder.get(token);
                if (bytes != null)
                    result.write(bytes.toByteArray());
                else
                    result.write(specialTokensDecoder.get(token).getBytes(ISO_8859_1));
            }
            return result.toString(UTF_8.name());
        } catch (IOException ex) {
            System.err.println(ex);
        }
        return null;
    }

    /**
     * Returns the regular expression for detecting special tokens
     *
     * @return the special tokenizing pattern
     */
    protected Pattern getTlSpecialRegex() {
        return specialPattern;
    }

    /**
     * Returns the regular expression for tokenizing text
     *
     * @return the tokenizing pattern
     */
    protected Pattern getTlRegex() {
        return pattern;
    }

    public List<Integer> encode(String text) {
        return encode(text, false);
    }

    public List<Integer> encode(String text, boolean allowedSpecial) {
        return encode(text, allowedSpecial ? specialTokensEncoder.keySet() : new HashSet<>());
    }

    public List<Integer> encode(String text, Set<String> allowedSpecial) {
        return encodeImpl(text, allowedSpecial);
    }

    protected List<Integer> encodeImpl(String text, Set<String> allowedSpecial) {
        Pattern specialRegex = getTlSpecialRegex();
        Pattern regex = getTlRegex();
        List<Integer> ret = new ArrayList<>(text.length() / 4);

        int start = 0;
        int lastPieceTokenLen = 0;
        while (true) {
            Matcher nextSpecial;
            int startFind = start;
            while (true) {
                // Find the next allowed special token, if any
                nextSpecial = specialRegex.matcher(text.substring(startFind));
                if (nextSpecial.find()) {
                    int startMatch = start + nextSpecial.start();
                    if (allowedSpecial.contains(text.substring(startMatch, startMatch + nextSpecial.group().length()))) {
                        break;
                    }
                    startFind = startMatch + 1;
                } else {
                    nextSpecial = null;
                    break;
                }
            }
            int end = (nextSpecial != null) ? (start + nextSpecial.start()) : text.length();

            // Tokenize the text using the regular expression
            Matcher matcher = regex.matcher(text.substring(start, end));
            while (matcher.find()) {
                ByteSequence piece = ByteSequence.from(matcher.group());
                Integer token = encoder.get(piece);
                if (token != null) {
                    lastPieceTokenLen = 1;
                    ret.add(token);
                } else {
                    lastPieceTokenLen = bytePairMerge(piece, ret);
                }
            }

            // Add the special token if one was found
            if (nextSpecial != null) {
                String piece = nextSpecial.group();
                Integer token = specialTokensEncoder.get(piece);
                ret.add(token);
                start += nextSpecial.end();
                lastPieceTokenLen = 0;
            } else {
                break;
            }
        }

        // lastPieceTokenLen is how many tokens came from the last regex split. This is used
        // for determining unstable tokens, since you can't merge across (stable) regex splits
        return ret;
    }

    private static class IntPair {
        // Simple data structure for representing a pair of indices into a byte sequence
        int start, end;

        IntPair(int start, int end) {
            this.start = start;
            this.end = end;
        }
    }

    protected int getRank(ByteSequence piece, List<IntPair> partsList, int startIdx) {
        if (startIdx + 2 < partsList.size()) {
            ByteSequence bytes = piece.subSequence(partsList.get(startIdx).start, partsList.get(startIdx + 2).start);
            Integer rank = encoder.get(bytes);
            return (rank != null) ? rank : Integer.MAX_VALUE;
        } else {
            return Integer.MAX_VALUE;
        }
    }

    ;

    protected int bytePairMerge(ByteSequence piece, Collection<Integer> result) {
        List<IntPair> parts = new ArrayList<>(piece.length() + 1);
        for (int i = 0; i <= piece.length(); i++) {
            parts.add(new IntPair(i, Integer.MAX_VALUE));
        }

        for (int i = 0; i < parts.size() - 2; i++) {
            int rank = getRank(piece, parts, i);
            if (rank != Integer.MAX_VALUE) {
                parts.get(i).end = rank;
            }
        }

        while (parts.size() > 1) {
            int minRank = Integer.MAX_VALUE;
            int minIndex = -1;
            for (int i = 0; i < parts.size() - 1; i++) {
                int rank = parts.get(i).end;
                if (rank < minRank) {
                    minRank = rank;
                    minIndex = i;
                }
            }
            if (minRank == Integer.MAX_VALUE) {
                break;
            }
            parts.remove(minIndex + 1);
            parts.get(minIndex).end = getRank(piece, parts, minIndex);
            if (minIndex > 0) {
                parts.get(minIndex - 1).end = getRank(piece, parts, minIndex - 1);
            }
        }

        int resultCount = 0;
        for (int i = 0; i < parts.size() - 1; i++) {
            IntPair range = new IntPair(parts.get(i).start, parts.get(i + 1).start);
            result.add(encoder.get(piece.subSequence(range.start, range.end)));
            resultCount++;
        }

        return resultCount;
    }

}
