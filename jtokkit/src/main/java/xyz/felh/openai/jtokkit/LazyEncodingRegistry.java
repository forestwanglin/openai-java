package xyz.felh.openai.jtokkit;

import xyz.felh.openai.jtokkit.api.Encoding;
import xyz.felh.openai.jtokkit.api.EncodingRegistry;
import xyz.felh.openai.jtokkit.api.EncodingType;
import xyz.felh.openai.jtokkit.api.ModelType;

import java.util.Optional;

/**
 * A lazy initialization implementation of {@link EncodingRegistry}. It does not register any encoding until either the
 * {@link #getEncoding(EncodingType)} or {@link #getEncoding(String)} method is called.
 * When one of these methods is called, the requested {@link EncodingType} is registered.
 */
final class LazyEncodingRegistry extends AbstractEncodingRegistry {

    @Override
    public Encoding getEncoding(final EncodingType encodingType) {
        addEncoding(encodingType);
        return super.getEncoding(encodingType);
    }

    @Override
    public Optional<Encoding> getEncoding(final String encodingName) {
        EncodingType.fromName(encodingName).ifPresent(this::addEncoding);

        return super.getEncoding(encodingName);
    }

    @Override
    public Encoding getEncodingForModel(final ModelType modelType) {
        addEncoding(modelType.getEncodingType());
        return super.getEncodingForModel(modelType);
    }
}