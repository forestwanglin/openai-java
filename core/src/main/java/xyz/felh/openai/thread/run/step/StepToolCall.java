package xyz.felh.openai.thread.run.step;

import lombok.Data;
import xyz.felh.openai.IOpenAiBean;

import java.util.List;
import java.util.Map;

@Data
public class StepToolCall implements IOpenAiBean {

    /**
     * The ID of the tool call object.
     */
    private String id;

    /**
     * The type of tool call. This is always going to be code_interpreter/retrieval/function for this type of tool call.
     */
    private String type;

    /**
     * type = retrieval
     */
    private Map<?, ?> retrieval;

    /**
     * The Code Interpreter tool call definition.
     */
    private CodeInterpreter codeInterpreter;

    /**
     * type = function
     * The definition of the function that was called.
     */
    private Function function;

    @Data
    public static class CodeInterpreter implements IOpenAiBean {
        /**
         * The input to the Code Interpreter tool call.
         */
        private String input;
        /**
         * The outputs from the Code Interpreter tool call. Code Interpreter can output one or more items, including text (logs) or images (image). Each of these are represented by a different object type.
         */
        private List<CodeInterpreterOutput> outputs;
    }

    @Data
    public static class CodeInterpreterOutput implements IOpenAiBean {
        /**
         * logs or image
         */
        private String type;

        /**
         * type = logs
         */
        private String logs;

        /**
         * type = image
         */
        private CodeInterpreterOutputImage image;
    }

    @Data
    public static class CodeInterpreterOutputImage implements IOpenAiBean {
        /**
         * The {@link xyz.felh.openai.file.File} ID of the image.
         */
        private String fileId;
    }

    @Data
    public static class Function implements IOpenAiBean {
        /**
         * The name of the function.
         */
        private String name;
        /**
         * The arguments passed to the function.
         */
        private String arguments;
        /**
         * The output of the function. This will be null if the outputs have not been submitted yet.
         */
        private String output;
    }

}
