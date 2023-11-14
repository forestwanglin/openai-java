package xyz.felh.openai.thread.message;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

import java.util.Arrays;
import java.util.List;

@Data
public class MessageContent implements IOpenAiBean {

    /**
     * image_file or text
     * <p>
     * See {@link Type}
     */
    private Type type;

    /**
     * References an image {@link xyz.felh.openai.file.File} in the content of a message.
     */
    private ImageFile imageFile;

    /**
     * The text content that is part of a message.
     */
    private Text text;

    @Data
    public static class ImageFile {
        /**
         * The {@link xyz.felh.openai.file.File} ID of the image in the message content.
         */
        private String fileId;
    }

    @Data
    public static class Text {
        /**
         * The data that makes up the text.
         */
        private String value;

        /**
         * annotations
         * <p>
         * File citation
         * A citation within the message that points to a specific quote from a specific File associated with the assistant or the message. Generated when the assistant uses the "retrieval" tool to search files.
         * <p>
         * File path
         * A URL for the file that's generated when the assistant used the code_interpreter tool to generate a file.
         */
        private List<TextAnnotation> annotations;
    }

    @Data
    public static class TextAnnotation {
        /**
         * file_citation or file_path
         */
        private String type;

        /**
         * The text in the message content that needs to be replaced.
         */
        private String text;

        private FileCitation fileCitation;
        private FilePath filePath;
        private Integer start_index;
        private Integer end_index;
    }

    @Data
    public static class FileCitation {
        /**
         * The ID of the specific File the citation is from.
         */
        private String fileId;

        /**
         * The specific quote in the file.
         */
        private String quote;
    }

    @Data
    public static class FilePath {
        /**
         * The ID of the specific File the citation is from.
         */
        private String fileId;
    }

    public enum Type {
        IMAGE_FILE("image_file"),
        TEXT("text");

        private final String value;

        Type(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Type findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }

    }

}
