package xyz.felh.openai;

import lombok.Data;
import okhttp3.Response;
import xyz.felh.openai.completion.chat.ChatCompletion;

@Data
public abstract class StreamChatCompletionListener {

    private String id;

    public StreamChatCompletionListener(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        return this.id.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((StreamChatCompletionListener) obj).getId().equals(this.id);
    }

    /**
     * Invoked when an event source has been accepted by the remote peer and may begin transmitting
     * events.
     *
     * @param requestId request ID
     * @param response  OK http response
     */
    void onOpen(String requestId, Response response) {
        System.out.println("onOpen:" + System.currentTimeMillis() + ":" + requestId);
    }

    /**
     * event line
     *
     * @param requestId      request ID
     * @param chatCompletion return chat completion
     */
    void onEvent(String requestId, ChatCompletion chatCompletion) {
        System.out.println("onEvent:" + System.currentTimeMillis() + ":" + requestId);
    }

    /**
     * event message finished
     *
     * @param requestId request ID
     */
    void onEventDone(String requestId) {
        System.out.println("onEventDone:" + System.currentTimeMillis() + ":" + requestId);
    }

    /**
     * <p>
     * No further calls to this listener will be made.
     *
     * @param requestId request ID
     */
    void onClosed(String requestId) {
        System.out.println("onClosed:" + System.currentTimeMillis() + ":" + requestId);
    }

    /**
     * Invoked when an event source has been closed due to an error reading from or writing to the
     * network. Incoming events may have been lost. No further calls to this listener will be made.
     *
     * @param requestId request ID
     * @param t         throwable
     * @param response  response
     */
    void onFailure(String requestId, Throwable t, Response response) {
        System.out.println("onFailure:" + System.currentTimeMillis() + ":" + requestId);
    }

}
