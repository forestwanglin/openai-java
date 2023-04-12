package xyz.felh.openai.gpt3.tokenizer;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import xyz.felh.openai.gpt3.tokenizer.ByteSequence;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.junit.jupiter.api.Assertions.*;

class ByteSequenceTest {

    private byte[] TEST_SEQUENCE_BYTES;
    private ByteSequence TEST_SEQUENCE;

    @BeforeEach
    void setUp() {
        TEST_SEQUENCE = ByteSequence.of(TEST_SEQUENCE_BYTES = "TEST_SEQUENCE".getBytes());
    }

    @Test
    void byteAt_gives_byte_at_requested_position() {
        assertEquals((byte) '1', ByteSequence.from("1").byteAt(0));
        assertEquals((byte) '2', ByteSequence.from("12").byteAt(1));
        assertEquals((byte) '9', ByteSequence.from("123456789").byteAt(8));
    }

    @Test
    void length_gives_number_of_bytes_in_sequence() {
        assertEquals(0, ByteSequence.EMPTY.length());
        assertEquals(1, ByteSequence.from("1").length());
        assertEquals(9, ByteSequence.from("123456789").length());
    }

    @Test
    void subSequence_gives_subsequence_between_given_start_and_end() {
        assertEquals(ByteSequence.EMPTY, ByteSequence.from("123456789").subSequence(9, 9));
        assertEquals(ByteSequence.from("1"), ByteSequence.from("123456789").subSequence(0, 1));
        assertEquals(ByteSequence.from("9"), ByteSequence.from("123456789").subSequence(8, 9));
    }

    @Test
    void hashCode_gives_identical_hashCode_for_identical_sequences() {
        ByteSequence aSequence = ByteSequence.from("TEST_SEQUENCE");
        ByteSequence anotherSequence = ByteSequence.from("TEST_SEQUENCE");
        assertEquals(anotherSequence.hashCode(), aSequence.hashCode());
    }

    @Test
    void equals_identifies_identical_byte_sequences() {
        ByteSequence aSequence = ByteSequence.from("TEST_SEQUENCE");
        ByteSequence anotherSequence = ByteSequence.from("TEST_SEQUENCE");
        assertEquals(anotherSequence, aSequence);
    }

    @Test
    void toByteArray_produces_correct_byte_array_representation() {
        assertArrayEquals(TEST_SEQUENCE_BYTES, TEST_SEQUENCE.toByteArray());
    }

    @Test
    void toString_gives_string_representation_using_given_charset() {
        String stringRepresentation = "TEST_SEQUENCE";
        ByteSequence aSequence = ByteSequence.from(stringRepresentation);
        assertEquals(stringRepresentation, aSequence.toString(UTF_8));
    }

    @Test
    void copyOf_creates_distinct_copy_when_not_immutable() {
        ByteSequence copy = ByteSequence.copyOf(TEST_SEQUENCE);
        assertEquals(TEST_SEQUENCE, copy);
        assertSame(TEST_SEQUENCE, copy);
    }

    @Test
    void from_converts_string_to_byte_sequence_using_utf8() {
        String string = "TEST_SEQUENCE";
        ByteSequence fromString = ByteSequence.from(string);
        assertArrayEquals(string.getBytes(UTF_8), fromString.toByteArray());
    }
}
