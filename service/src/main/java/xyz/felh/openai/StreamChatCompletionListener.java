package xyz.felh.openai;

import lombok.Data;
import okhttp3.Response;
import xyz.felh.openai.completion.chat.ChatCompletion;

@Data
public abstract class StreamChatCompletionListener {

    private String clientId;

    @Override
    public int hashCode() {
        return this.clientId.hashCode();
    }

    @Override
    public boolean equals(Object obj) {
        return ((StreamChatCompletionListener) obj).getClientId().equals(this.clientId);
    }

    /**
     * Invoked when an event source has been accepted by the remote peer and may begin transmitting
     * events.
     *
     * @param requestId request ID
     * @param response  OK http response
     */
    public void onOpen(String requestId, Response response) {
        System.out.println("onOpen:" + requestId);
    }

    /**
     * event line
     *
     * @param requestId      request ID
     * @param chatCompletion return chat completion
     */
    public void onEvent(String requestId, ChatCompletion chatCompletion) {
        System.out.println("onEvent:" + requestId);
    }

    /**
     * event message finished
     *
     * @param requestId request ID
     */
    public void onEventDone(String requestId) {
        System.out.println("onEventDone:" + requestId);
    }

    /**
     * <p>
     * No further calls to this listener will be made.
     *
     * @param requestId request ID
     */
    public void onClosed(String requestId) {
        System.out.println("onClosed:" + requestId);
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
        System.out.println("onFailure:" + requestId);
    }

}
