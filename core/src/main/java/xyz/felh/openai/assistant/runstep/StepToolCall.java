package xyz.felh.openai.assistant.runstep;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;
import java.util.Map;

@Data
public class StepToolCall implements IOpenAiBean {

    /**
     * The index of the tool call in the tool calls array.
     */
    @JSONField(name = "index")
    @JsonProperty("index")
    private Integer index;

    /**
     * The ID of the tool call object.
     */
    @JSONField(name = "id")
    @JsonProperty("id")
    private String id;

    /**
     * The type of tool call. This is always going to be code_interpreter/file_search/function for this type of tool call.
     */
    @JSONField(name = "type")
    @JsonProperty("type")
    private String type;

    /**
     * type = file_search
     * For now, this is always going to be an empty object.
     */
    @JSONField(name = "file_search")
    @JsonProperty("file_search")
    private Map<?, ?> fileSearch;

    /**
     * The Code Interpreter tool call definition.
     */
    @JSONField(name = "code_interpreter")
    @JsonProperty("code_interpreter")
    private CodeInterpreter codeInterpreter;

    /**
     * type = function
     * The definition of the function that was called.
     */
    @JSONField(name = "function")
    @JsonProperty("function")
    private Function function;

    @Data
    public static class CodeInterpreter implements IOpenAiBean {
        /**
         * The input to the Code Interpreter tool call.
         */
        @JSONField(name = "input")
        @JsonProperty("input")
        private String input;
        /**
         * The outputs from the Code Interpreter tool call. Code Interpreter can output one or more items, including text (logs) or images (image). Each of these are represented by a different object type.
         */
        @JSONField(name = "outputs")
        @JsonProperty("outputs")
        private List<CodeInterpreterOutput> outputs;
    }

    @Data
    public static class CodeInterpreterOutput implements IOpenAiBean {
        /**
         * logs or image
         */
        @JSONField(name = "type")
        @JsonProperty("type")
        private String type;

        /**
         * type = logs
         */
        @JSONField(name = "logs")
        @JsonProperty("logs")
        private String logs;

        /**
         * type = image
         */
        @JSONField(name = "image")
        @JsonProperty("image")
        private CodeInterpreterOutputImage image;
    }

    @Data
    public static class CodeInterpreterOutputImage implements IOpenAiBean {
        /**
         * The {@link xyz.felh.openai.file.File} ID of the image.
         */
        @JSONField(name = "file_id")
        @JsonProperty("file_id")
        private String fileId;
    }

    @Data
    public static class Function implements IOpenAiBean {
        /**
         * The name of the function.
         */
        @JSONField(name = "name")
        @JsonProperty("name")
        private String name;
        /**
         * The arguments passed to the function.
         */
        @JSONField(name = "arguments")
        @JsonProperty("arguments")
        private String arguments;
        /**
         * The output of the function. This will be null if the outputs have not been submitted yet.
         */
        @JSONField(name = "output")
        @JsonProperty("output")
        private String output;
    }

}
