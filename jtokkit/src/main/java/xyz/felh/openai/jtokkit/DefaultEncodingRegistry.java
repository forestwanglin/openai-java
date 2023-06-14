package xyz.felh.openai.jtokkit;

import xyz.felh.openai.jtokkit.api.EncodingRegistry;
import xyz.felh.openai.jtokkit.api.EncodingType;

/**
 * Thread-safe default implementation of {@link EncodingRegistry}. During initialization, it registers the default encodings
 * for the different {@link EncodingType}s.
 */
final class DefaultEncodingRegistry extends AbstractEncodingRegistry {

    /**
     * Initializes the registry with the default encodings.
     *
     * @throws IllegalStateException if an unknown encoding type is encountered
     */
    public void initializeDefaultEncodings() {
        for (final EncodingType encodingType : EncodingType.values()) {
            addEncoding(encodingType);
        }
    }

}
