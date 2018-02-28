package uk.gov.justice.services.messaging.spi;

import uk.gov.justice.services.messaging.Envelope;
import uk.gov.justice.services.messaging.JsonEnvelope;

import java.util.Iterator;
import java.util.ServiceLoader;
import java.util.function.Function;

/**
 * Interface for EnvelopeBuilderProvider implementations to provide methods for constructing
 * functions wrapping objects into an envelope.
 *
 * Call the static method {@code EnvelopeBuilderProvider.provider()} to retrieve the
 * EnvelopeBuilderProvider instance from service dependency on the classpath.
 */

public interface EnvelopeBuilderProvider {

    /**
     * Loads an implementation of EnvelopeBuilderProvider using the {@link ServiceLoader} mechanism. An
     * instance of the first implementing class from the loader list is returned.
     *
     * @return an instance of EnvelopeBuilderProvider
     * @throws EnvelopeBuilderProviderNotFoundException if no implementations of
     * EnvelopeBuilderProvider are found
     */
    static EnvelopeBuilderProvider provider() {
        final ServiceLoader<EnvelopeBuilderProvider> loader = ServiceLoader.load(EnvelopeBuilderProvider.class);
        final Iterator<EnvelopeBuilderProvider> iterator = loader.iterator();

        if (iterator.hasNext()) {
            return iterator.next();
        }

        throw new EnvelopeBuilderProviderNotFoundException("No EnvelopeBuilderProvider implementation found");
    }

    /**
     * Provides a function that wraps the provided object into a new {@link JsonEnvelope} using
     * metadata from the given envelope.
     *
     * @param envelope - the envelope containing source metadata.
     * @return a function that wraps objects into an envelope.
     */
    Function<Object, JsonEnvelope> withMetadataFrom(final JsonEnvelope envelope);

    /**
     * Provides a function that wraps the provided object into a new {@link JsonEnvelope} using
     * metadata from the given envelope, except the name.
     *
     * @param envelope - the envelope containing source metadata.
     * @param name     - name of the payload.
     * @return a function that wraps objects into an envelope.
     */
    Function<Object, JsonEnvelope> withMetadataFrom(final JsonEnvelope envelope, final String name);

    /**
     * Provides a function that wraps the provided pojo into a new {@link Envelope} using
     * metadata from the given envelope.
     *
     * @param envelope - the envelope containing source metadata.
     * @return a function that wraps objects into an envelope.
     */
    <T> Function<T, Envelope<T>> withMetadataFrom(final Envelope<T> envelope);

    /**
     * Provides a function that wraps the provided pojo into a new {@link Envelope} using
     * metadata from the given envelope, except the name.
     *
     * @param envelope - the envelope containing source metadata.
     * @param name     - name of the payload.
     * @return a function that wraps objects into an envelope.
     */
    <T> Function<T, Envelope<T>> withMetadataFrom(final Envelope<T> envelope, final String name);

}
