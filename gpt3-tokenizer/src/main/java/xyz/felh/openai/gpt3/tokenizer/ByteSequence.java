/*
 * Copyright (c) 2023 Mariusz Bernacki <info@didalgo.com>
 * SPDX-License-Identifier: MIT
 */
package xyz.felh.openai.gpt3.tokenizer;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;

/**
 * Represents a sequences of bytes.
 *
 * @author Forest Wang
 */
public interface ByteSequence {

    /**
     * The empty {@code ByteSequence} of length 0.
     */
    ByteSequence EMPTY = of(new byte[0]);

    /**
     * Returns the byte at the specified offset.
     *
     * @param index the zero-based byte offset within the sequence of bytes (0 &lt;= index &lt; length())
     * @return the byte at the specified offset
     * @throws IndexOutOfBoundsException if the index is out of range (index &lt; 0 || index &gt;= length())
     */
    byte byteAt(int index);

    /**
     * Returns the length of the byte sequence.
     *
     * @return the number of bytes in the sequence
     */
    int length();

    /**
     * Returns a new ByteSequence that is a sub-sequence of the current byte sequence.
     * The sub-sequence starts with the byte value at the specified {@code start} index and
     * extends to the byte value at index {@code end - 1}.
     *
     * @param start the beginning index, inclusive
     * @param end   the ending index, exclusive
     * @return a new ByteSequence that is a sub-sequence of this byte sequence
     * @throws IndexOutOfBoundsException if the start or end index is invalid
     */
    ByteSequence subSequence(int start, int end) throws IndexOutOfBoundsException;

    /**
     * Returns a hash code value for this byte sequence.
     *
     * @return a hash code value for this byte sequence
     */
    @Override
    int hashCode();

    /**
     * Compares the specified object with this byte sequence for equality.
     * Returns {@code true} if and only if the specified object is also a byte sequence
     * and both byte sequences have the same bytes in the same order.
     *
     * @param obj the object to be compared for equality with this byte sequence
     * @return {@code true} if the specified object is equal to this byte sequence, {@code false} otherwise
     */
    @Override
    boolean equals(Object obj);

    /**
     * Returns a byte array representation of this byte sequence.
     * The returned array will be a copy of the internal byte array, ensuring that modifications
     * to the returned array do not affect the original byte sequence.
     *
     * @return a byte array representation of this byte sequence
     */
    byte[] toByteArray();

    /**
     * Converts the byte sequence to a String using the specified Charset.
     *
     * @param charset the Charset to be used for the conversion
     * @return a String representation of this byte sequence using the specified Charset
     */
    String toString(Charset charset);

    /**
     * Returns a new ByteSequence instance containing the specified byte array.
     * The provided byte array is wrapped in an ImmutableByteSequence to ensure
     * that the contents of the byte array are not modified after the ByteSequence
     * is created.
     *
     * @param bytes the byte array to be used for the new ByteSequence
     * @return a new ByteSequence instance containing the specified byte array
     * @throws NullPointerException if the provided byte array is null
     */
    static ByteSequence of(byte[] bytes) {
        return new Of(Arrays.copyOf(bytes, bytes.length));
    }

    /**
     * Returns an immutable ByteSequence that is a copy of the specified ByteSequence.
     * If the provided ByteSequence is already an instance of ImmutableByteSequence,
     * it is returned directly; otherwise, a new ImmutableByteSequence is created.
     *
     * @param sequence the ByteSequence to be copied
     * @return an immutable ByteSequence that is a copy of the specified ByteSequence
     * @throws NullPointerException if the provided ByteSequence is null
     */
    static ByteSequence copyOf(ByteSequence sequence) {
        if (sequence instanceof Of)
            return sequence;
        else
            return of(sequence.toByteArray());
    }

    /**
     * Creates a ByteSequence from the specified String using the UTF-8 charset.
     *
     * @param text the String to be converted to a ByteSequence
     * @return a new ByteSequence that represents the specified String using the UTF-8 charset
     * @throws NullPointerException if the provided text is null
     */
    static ByteSequence from(String text) {
        return from(text, StandardCharsets.UTF_8);
    }

    /**
     * Creates a ByteSequence from the specified String using the specified Charset.
     *
     * @param text    the String to be converted to a ByteSequence
     * @param charset the Charset to be used for the conversion
     * @return a new ByteSequence that represents the specified String using the specified Charset
     * @throws NullPointerException if the provided text or charset is null
     */
    static ByteSequence from(String text, Charset charset) {
        return new Of(text.getBytes(charset));
    }

    /**
     * An immutable implementation of the {@code ByteSequence}.
     */
    final class Of implements ByteSequence, Comparable<Of> {
        private final byte[] bytes;

        private Of(byte[] bytes) {
            this.bytes = bytes;
        }

        @Override
        public byte byteAt(int index) {
            if (index < 0 || index >= length()) {
                throw new IndexOutOfBoundsException("Index " + index + " is out of range (0 <= index < " + length() + ")");
            }
            return bytes[index];
        }

        @Override
        public int length() {
            return bytes.length;
        }

        @Override
        public Of subSequence(int start, int end) {
            return new Of(Arrays.copyOfRange(bytes, start, end));
        }

        @Override
        public int hashCode() {
            return Arrays.hashCode(bytes);
        }

        @Override
        public boolean equals(Object obj) {
            if (obj instanceof Of) {
                return Arrays.equals(bytes, ((Of) obj).bytes);
            }
            return false;
        }

        @Override
        public byte[] toByteArray() {
            return Arrays.copyOf(bytes, bytes.length);
        }

        @Override
        public String toString(Charset charset) {
            return new String(bytes, charset);
        }

        @Override
        public String toString() {
            return toString(StandardCharsets.UTF_8);
        }

        @Override
        public int compareTo(Of other) {
//            return Arrays.compare(bytes, other.bytes);
            return 0; // TODO
        }
    }
}
