package xyz.felh.openai.gpt3.tokenizer;

import lombok.Data;

import java.io.*;
import java.util.Base64;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Represents variants of BPE encoding.
 * <p>
 * Modifications:
 * <ul>
 *     <li>[MB] 2023-03-25: Repackaged from <a href="https://github.com/openai/tiktoken">Tiktoken</a> for inclusion in gpt3-tokenizer-java.</li>
 *     <li>[MB] 2023-04-02: Major refactoring for cleaner code and improved performance.</li>
 * </ul>
 */
@Data
public class Encoding {

    private String tiktokenFilename;
    private Map<ByteSequence, Integer> mergeableRanks;
    private Map<String, Integer> specialTokens;
    private Pattern pattern;

    public static final String ENDOFTEXT = "<|endoftext|>";
    public static final String FIM_PREFIX = "<|fim_prefix|>";
    public static final String FIM_MIDDLE = "<|fim_middle|>";
    public static final String FIM_SUFFIX = "<|fim_suffix|>";
    public static final String ENDOFPROMPT = "<|endofprompt|>";

    public static final Encoding CL100K_BASE = new Encoding(
            "cl100k_base.tiktoken", new HashMap<>(),
            new HashMap<String, Integer>() {
                {
                    put(ENDOFTEXT, 100257);
                    put(FIM_PREFIX, 100258);
                    put(FIM_MIDDLE, 100259);
                    put(FIM_SUFFIX, 100260);
                    put(ENDOFPROMPT, 100276);
                }
            },
            Pattern.compile("(?i:'s|'t|'re|'ve|'m|'ll|'d)|[^\\r\\n\\p{L}\\p{N}]?\\p{L}+|\\p{N}{1,3}| ?[^\\s\\p{L}\\p{N}]+[\\r\\n]*|\\s*[\\r\\n]+|\\s+(?!\\S)|\\s+")
    );

    public static final Encoding P50K_BASE = new Encoding(
            "p50k_base.tiktoken", new HashMap<>(),
            new HashMap<String, Integer>() {
                {
                    put(ENDOFTEXT, 50256);
                }
            },
            Pattern.compile("'s|'t|'re|'ve|'m|'ll|'d| ?\\p{L}+| ?\\p{N}+| ?[^\\s\\p{L}\\p{N}]+|\\s+(?!\\S)|\\s+")
    );

    public static final Encoding P50K_EDIT = new Encoding(
            "p50k_base.tiktoken", new HashMap<>(),
            new HashMap<String, Integer>() {
                {
                    put(ENDOFTEXT, 50256);
                    put(FIM_PREFIX, 50281);
                    put(FIM_MIDDLE, 50282);
                    put(FIM_SUFFIX, 50283);
                }
            },
            Pattern.compile("'s|'t|'re|'ve|'m|'ll|'d| ?\\p{L}+| ?\\p{N}+| ?[^\\s\\p{L}\\p{N}]+|\\s+(?!\\S)|\\s+")
    );

    public static final Encoding R50K_BASE = new Encoding(
            "r50k_base.tiktoken", new HashMap<>(),
            new HashMap<String, Integer>() {
                {
                    put(ENDOFTEXT, 50256);
                }
            },
            Pattern.compile("'s|'t|'re|'ve|'m|'ll|'d| ?\\p{L}+| ?\\p{N}+| ?[^\\s\\p{L}\\p{N}]+|\\s+(?!\\S)|\\s+")
    );


    private Encoding(String tiktokenFilename,
                     Map<ByteSequence, Integer> mergeableRanks,
                     Map<String, Integer> specialTokens,
                     Pattern pattern) {
        this.tiktokenFilename = tiktokenFilename;
        this.mergeableRanks = mergeableRanks;
        this.pattern = pattern;
        this.specialTokens = Collections.unmodifiableMap(new HashMap<>(specialTokens)); // only wrapped HashMap is efficient enough; Map.copyOf() has performance issues
    }

    public Map<ByteSequence, Integer> mergeableRanks() {
        if (mergeableRanks.isEmpty()) {
            synchronized (mergeableRanks) {
                if (mergeableRanks.isEmpty())
                    Lookup.loadTiktokenBase(tiktokenFilename, mergeableRanks);
            }
        }
        return Collections.unmodifiableMap(this.mergeableRanks);
    }

    public static Encoding forName(String encodingName) {
        switch (encodingName.toLowerCase()) {
            case "cl100k_base":
                return CL100K_BASE;
            case "p50k_base":
                return P50K_BASE;
            case "p50k_edit":
                return P50K_EDIT;
            case "r50k_base":
                return R50K_BASE;
            default:
                throw new IllegalArgumentException("Unknown encoding: " + encodingName);
        }
    }

    public static Encoding forModel(String modelName) {
        String encodingName = Lookup.modelToEncoding.get(modelName);
        if (encodingName == null) {
            encodingName = Lookup.modelPrefixToEncoding.keySet().stream()
                    .filter(modelName::startsWith)
                    .findFirst()
                    .map(Lookup.modelPrefixToEncoding::get)
                    .orElseThrow(() -> new IllegalArgumentException("Unknown model name: " + modelName));
        }
        return forName(encodingName);
    }

    final static class Lookup {
        private static final Map<String, String> modelPrefixToEncoding;
        private static final Map<String, String> modelToEncoding;

        static {
            Map<String, String> mp2e = new HashMap<>();
            mp2e.put("gpt-4-", "cl100k_base");
            mp2e.put("gpt-3.5-turbo-", "cl100k_base");
            modelPrefixToEncoding = mp2e;

            Map<String, String> m2e = new HashMap<>();
            m2e.put("gpt-4", "cl100k_base");
            m2e.put("gpt-3.5-turbo", "cl100k_base");
            m2e.put("text-davinci-003", "p50k_base");
            m2e.put("text-davinci-002", "p50k_base");
            m2e.put("text-davinci-001", "r50k_base");
            m2e.put("text-curie-001", "r50k_base");
            m2e.put("text-babbage-001", "r50k_base");
            m2e.put("text-ada-001", "r50k_base");
            m2e.put("davinci", "r50k_base");
            m2e.put("curie", "r50k_base");
            m2e.put("babbage", "r50k_base");
            m2e.put("ada", "r50k_base");
            m2e.put("code-davinci-002", "p50k_base");
            m2e.put("code-davinci-001", "p50k_base");
            m2e.put("code-cushman-002", "p50k_base");
            m2e.put("code-cushman-001", "p50k_base");
            m2e.put("davinci-codex", "p50k_base");
            m2e.put("cushman-codex", "p50k_base");
            m2e.put("text-davinci-edit-001", "p50k_edit");
            m2e.put("code-davinci-edit-001", "p50k_edit");
            m2e.put("text-embedding-ada-002", "cl100k_base");
            m2e.put("text-similarity-davinci-001", "r50k_base");
            m2e.put("text-similarity-curie-001", "r50k_base");
            m2e.put("text-similarity-babbage-001", "r50k_base");
            m2e.put("text-similarity-ada-001", "r50k_base");
            m2e.put("text-search-davinci-doc-001", "r50k_base");
            m2e.put("text-search-curie-doc-001", "r50k_base");
            m2e.put("text-search-babbage-doc-001", "r50k_base");
            m2e.put("text-search-ada-doc-001", "r50k_base");
            m2e.put("code-search-babbage-code-001", "r50k_base");
            m2e.put("code-search-ada-code-001", "r50k_base");
            modelToEncoding = m2e;
        }

        public static Map<ByteSequence, Integer> loadTiktokenBase(String filename, Map<ByteSequence, Integer> resultMap) {
            try (InputStream in = Lookup.class.getResourceAsStream(filename)) {
                Map<ByteSequence, Integer> result = (resultMap == null) ? new HashMap<ByteSequence, Integer>() : resultMap;
                new BufferedReader(new InputStreamReader(in)).lines()
                        .filter(line -> !line.isEmpty())
                        .map(line -> line.split(" ", 2))
                        .forEach(parts -> result.put(
                                ByteSequence.of(Base64.getDecoder().decode(parts[0])),
                                Integer.parseInt(parts[1]))
                        );
                return result;

            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }
    }

}
