package xyz.felh.openai;

import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;
import xyz.felh.openai.completion.chat.ChatCompletion;

@Slf4j
public abstract class StreamChatCompletionListener {

    private EventSource eventSource;

    public void setEventSource(EventSource eventSource) {
        this.eventSource = eventSource;
    }

    /**
     * Invoked when an event source has been accepted by the remote peer and may begin transmitting
     * events.
     *
     * @param requestId request ID
     * @param response  OK http response
     */
    public void onOpen(String requestId, Response response) {
        log.info("onOpen: {}", requestId);
    }

    /**
     * event line
     *
     * @param requestId      request ID
     * @param chatCompletion return chat completion
     */
    public void onEvent(String requestId, ChatCompletion chatCompletion) {
        log.info("onEvent: {}", requestId);
    }

    /**
     * event message finished
     *
     * @param requestId request ID
     */
    public void onEventDone(String requestId) {
        log.info("onEventDone: {}", requestId);
    }

    /**
     * <p>
     * No further calls to this listener will be made.
     *
     * @param requestId request ID
     */
    public void onClosed(String requestId) {
        log.info("onClosed: {}", requestId);
    }

    /**
     * Invoked when an event source has been closed due to an error reading from or writing to the
     * network. Incoming events may have been lost. No further calls to this listener will be made.
     *
     * @param requestId request ID
     * @param t         throwable
     * @param response  response
     */
    public void onFailure(String requestId, Throwable t, Response response) {
        log.error("onFailure: {}", requestId, t);
    }

    /**
     * cancel eventSource
     */
    public void close() {
        if (eventSource != null) {
            eventSource.cancel();
        }
    }

}
