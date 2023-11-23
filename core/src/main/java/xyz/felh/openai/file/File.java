package xyz.felh.openai.file;

import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.chat.ChatMessage;

import java.util.Arrays;

@EqualsAndHashCode(callSuper = true)
@Data
@ToString(callSuper = true)
public class File extends OpenAiApiObjectWithId {

    public static String OBJECT = "file";

    /**
     * File size in bytes.
     */
    private Long bytes;

    /**
     * The creation time in epoch seconds.
     */
    private Long createdAt;

    /**
     * The name of the file.
     */
    private String filename;

    /**
     * The intended purpose of the file. Supported values are fine-tune, fine-tune-results, assistants, and assistants_output.
     * <p>
     * See {@link Purpose}
     * <p>
     * assistants -  Supported formats: ['c', 'cpp', 'csv', 'docx', 'html', 'java', 'json', 'md', 'pdf', 'php', 'pptx', 'py', 'rb', 'tex', 'txt', 'css', 'jpeg', 'jpg', 'js', 'gif', 'png', 'tar', 'ts', 'xlsx', 'xml', 'zip']
     */
    private Purpose purpose;


    @Getter
    public enum Purpose {

        FINE_TUNE("fine-tune"),
        FINE_TUNE_RESULTS("fine-tune-results"),
        ASSISTANTS("assistants"),
        ASSISTANTS_OUTPUT("assistants_output");

        private final String value;

        Purpose(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Purpose findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }
    }

}
