package xyz.felh.openai.assistant.stream;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import xyz.felh.openai.IOpenAiApiObject;

/**
 * thread.created
 * data is a thread
 * <p>
 * Occurs when a new thread is created.
 * <p>
 * thread.run.created
 * data is a run
 * <p>
 * Occurs when a new run is created.
 * <p>
 * thread.run.queued
 * data is a run
 * <p>
 * Occurs when a run moves to a queued status.
 * <p>
 * thread.run.in_progress
 * data is a run
 * <p>
 * Occurs when a run moves to an in_progress status.
 * <p>
 * thread.run.requires_action
 * data is a run
 * <p>
 * Occurs when a run moves to a requires_action status.
 * <p>
 * thread.run.completed
 * data is a run
 * <p>
 * Occurs when a run is completed.
 * <p>
 * thread.run.failed
 * data is a run
 * <p>
 * Occurs when a run fails.
 * <p>
 * thread.run.cancelling
 * data is a run
 * <p>
 * Occurs when a run moves to a cancelling status.
 * <p>
 * thread.run.cancelled
 * data is a run
 * <p>
 * Occurs when a run is cancelled.
 * <p>
 * thread.run.expired
 * data is a run
 * <p>
 * Occurs when a run expires.
 * <p>
 * thread.run.step.created
 * data is a run step
 * <p>
 * Occurs when a run step is created.
 * <p>
 * thread.run.step.in_progress
 * data is a run step
 * <p>
 * Occurs when a run step moves to an in_progress state.
 * <p>
 * thread.run.step.delta
 * data is a run step delta
 * <p>
 * Occurs when parts of a run step are being streamed.
 * <p>
 * thread.run.step.completed
 * data is a run step
 * <p>
 * Occurs when a run step is completed.
 * <p>
 * thread.run.step.failed
 * data is a run step
 * <p>
 * Occurs when a run step fails.
 * <p>
 * thread.run.step.cancelled
 * data is a run step
 * <p>
 * Occurs when a run step is cancelled.
 * <p>
 * thread.run.step.expired
 * data is a run step
 * <p>
 * Occurs when a run step expires.
 * <p>
 * thread.message.created
 * data is a message
 * <p>
 * Occurs when a message is created.
 * <p>
 * thread.message.in_progress
 * data is a message
 * <p>
 * Occurs when a message moves to an in_progress state.
 * <p>
 * thread.message.delta
 * data is a message delta
 * <p>
 * Occurs when parts of a Message are being streamed.
 * <p>
 * thread.message.completed
 * data is a message
 * <p>
 * Occurs when a message is completed.
 * <p>
 * thread.message.incomplete
 * data is a message
 * <p>
 * Occurs when a message ends before it is completed.
 * <p>
 * error
 * data is an error
 * <p>
 * Occurs when an error occurs. This can happen due to an internal server error or a timeout.
 * <p>
 * done
 * data is [DONE]
 * <p>
 * Occurs when a stream ends.
 */
@Data
public class StreamEvent implements IOpenAiApiObject {

    @JSONField(name = "event")
    @JsonProperty("event")
    private String event;

    @JSONField(name = "data")
    @JsonProperty("data")
    private IOpenAiApiObject data;

}
