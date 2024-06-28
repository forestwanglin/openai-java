package xyz.felh;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import okhttp3.sse.EventSource;

@Data
@Slf4j
public abstract class StreamListener<T extends IAPIObject> {

    private EventSource eventSource;

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
     * @param requestId request ID
     * @param t         return IOpenAiApiObject
     */
    public void onEvent(String requestId, T t) {
        log.info("onEvent: {}", requestId);
    }

    /**
     * event message finished, get payload [DONE]
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
