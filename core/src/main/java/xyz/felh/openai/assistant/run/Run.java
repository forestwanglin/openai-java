package xyz.felh.openai.assistant.run;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonValue;
import lombok.Data;
import lombok.EqualsAndHashCode;
import xyz.felh.openai.LastError;
import xyz.felh.openai.OpenAiApiObjectWithId;
import xyz.felh.openai.Usage;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.assistant.IncompleteDetails;
import xyz.felh.openai.assistant.thread.Thread;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
public class Run extends OpenAiApiObjectWithId {

    public static String OBJECT = "thread.run";

    /**
     * The Unix timestamp (in seconds) for when the run was created.
     */
    @JSONField(name = "created_at")
    @JsonProperty("created_at")
    private Integer createdAt;

    /**
     * The ID of the {@link Thread} that was executed on as a part of this run.
     */
    @JSONField(name = "thread_id")
    @JsonProperty("thread_id")
    private String threadId;

    /**
     * The ID of the {@link xyz.felh.openai.assistant.Assistant} used for execution of this run.
     */
    @JSONField(name = "assistant_id")
    @JsonProperty("assistant_id")
    private String assistantId;

    /**
     * The status of the run, which can be either queued, in_progress, requires_action, cancelling, cancelled, failed, completed, or expired.
     * See {@link Status}
     */
    @JSONField(name = "status")
    @JsonProperty("status")
    private Status status;

    /**
     * Details on the action required to continue the run. Will be null if no action is required.
     * <p>
     * See {@link RequiredAction}
     */
    @JSONField(name = "required_action")
    @JsonProperty("required_action")
    private RequiredAction requiredAction;

    /**
     * The last error associated with this run. Will be null if there are no errors.
     * <p>
     * See {@link LastError}
     */
    @JSONField(name = "last_error")
    @JsonProperty("last_error")
    private LastError lastError;

    /**
     * The Unix timestamp (in seconds) for when the run will expire.
     */
    @JSONField(name = "expires_at")
    @JsonProperty("expires_at")
    private Integer expiresAt;

    /**
     * The Unix timestamp (in seconds) for when the run was started.
     */
    @JSONField(name = "started_at")
    @JsonProperty("started_at")
    private Integer startedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was cancelled.
     */
    @JSONField(name = "cancelled_at")
    @JsonProperty("cancelled_at")
    private Integer cancelledAt;

    /**
     * The Unix timestamp (in seconds) for when the run failed.
     */
    @JSONField(name = "failed_at")
    @JsonProperty("failed_at")
    private Integer failedAt;

    /**
     * The Unix timestamp (in seconds) for when the run was completed.
     */
    @JSONField(name = "completed_at")
    @JsonProperty("completed_at")
    private Integer completedAt;

    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

    /**
     * Details on why the run is incomplete. Will be null if the run is not incomplete.
     */
    @JSONField(name = "incomplete_details")
    @JsonProperty("incomplete_details")
    private IncompleteDetails incompleteDetails;

    /**
     * The instructions that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     */
    @JSONField(name = "instructions")
    @JsonProperty("instructions")
    private String instructions;

    /**
     * The list of tools that the {@link xyz.felh.openai.assistant.Assistant} used for this run.
     * <p>
     * See {@link AssistantTool}
     */
    @JSONField(name = "tools")
    @JsonProperty("tools")
    private List<AssistantTool> tools;

    /**
     * Set of 16 key-value pairs that can be attached to an object. This can be useful for storing additional information about the object in a structured format. Keys can be a maximum of 64 characters long and values can be a maxium of 512 characters long.
     */
    @JSONField(name = "metadata")
    @JsonProperty("metadata")
    private Map<String, String> metadata;

    /**
     * Usage statistics related to the run. This value will be null if the run is not in a terminal state (i.e. in_progress, queued, etc.).
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

    /**
     * The sampling temperature used for this run. If not set, defaults to 1.
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    /**
     * An alternative to sampling with temperature, called nucleus sampling, where the model considers the results of the tokens with top_p probability mass. So 0.1 means only the tokens comprising the top 10% probability mass are considered.
     * <p>
     * We generally recommend altering this or temperature but not both.
     */
    @JSONField(name = "top_p")
    @JsonProperty("top_p")
    private Double topP;

    /**
     * The maximum number of prompt tokens that may be used over the course of the run. The run will make a best effort to use only the number of prompt tokens specified, across multiple turns of the run. If the run exceeds the number of prompt tokens specified, the run will end with status incomplete. See incomplete_details for more info.
     */
    @JSONField(name = "max_prompt_tokens")
    @JsonProperty("max_prompt_tokens")
    private Integer maxPromptTokens;

    /**
     * The maximum number of completion tokens that may be used over the course of the run. The run will make a best effort to use only the number of completion tokens specified, across multiple turns of the run. If the run exceeds the number of completion tokens specified, the run will end with status incomplete. See incomplete_details for more info.
     */
    @JSONField(name = "max_completion_tokens")
    @JsonProperty("max_completion_tokens")
    private Integer maxCompletionTokens;

    /**
     * Controls for how a thread will be truncated prior to the run. Use this to control the intial context window of the run.
     */
    @JSONField(name = "truncation_strategy")
    @JsonProperty("truncation_strategy")
    private TruncationStrategy truncationStrategy;

    /**
     * Controls which (if any) tool is called by the model. none means the model will not call any tools and instead generates a message. auto is the default value and means the model can pick between generating a message or calling a tool. Specifying a particular tool like {"type": "file_search"} or {"type": "function", "function": {"name": "my_function"}} forces the model to call that tool.
     * See {@link ToolChoice}
     */
    @JSONField(name = "tool_choice")
    @JsonProperty("tool_choice")
    private Object toolChoice;

    /**
     * Specifies the format that the model must output. Compatible with GPT-4 Turbo and all GPT-3.5 Turbo models since gpt-3.5-turbo-1106.
     * <p>
     * Setting to { "type": "json_object" } enables JSON mode, which guarantees the message the model generates is valid JSON.
     * <p>
     * Important: when using JSON mode, you must also instruct the model to produce JSON yourself via a system or user message. Without this, the model may generate an unending stream of whitespace until the generation reaches the token limit, resulting in a long-running and seemingly "stuck" request. Also note that the message content may be partially cut off if finish_reason="length", which indicates the generation exceeded max_tokens or the conversation exceeded the max context length.
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private Object responseFormat;

    public enum Status {
        QUEUED("queued"),
        IN_PROGRESS("in_progress"),
        REQUIRES_ACTION("requires_action"),
        CANCELLING("cancelling"),
        CANCELLED("cancelled"),
        FAILED("failed"),
        COMPLETED("completed"),
        EXPIRED("expired"),
        ;

        private final String value;

        Status(final String value) {
            this.value = value;
        }

        @JsonValue
        public String value() {
            return value;
        }

        public static Status findByValue(String value) {
            return Arrays.stream(values()).filter(it ->
                    it.value.equals(value)).findFirst().orElse(null);
        }

    }

}