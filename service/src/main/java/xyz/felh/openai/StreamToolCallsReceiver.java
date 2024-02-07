package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import xyz.felh.openai.bean.StreamToolCallsRequest;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

@Data
@Slf4j
public class StreamToolCallsReceiver {

    private List<ChatCompletion> chatCompletions;
    // null 未初始化
    private Boolean active;
    private BiFunction<String, ChatCompletion, StreamToolCallsRequest> toolCallsHandler;
    private CountDownLatch countDownLatch;
    private final OpenAiService openAiService;
    private StreamChatCompletionListener listener;

    private long completionTokens = 0;

    private String originalRequestId;

    // new request params
    private String requestId;
    private boolean failure = false;
    private Throwable t;
    private Response response;

    public StreamToolCallsReceiver(OpenAiService openAiService,
                                   String originalRequestId,
                                   BiFunction<String, ChatCompletion, StreamToolCallsRequest> toolCallsHandler,
                                   StreamChatCompletionListener listener,
                                   CountDownLatch countDownLatch) {
        this.chatCompletions = new ArrayList<>();
        this.originalRequestId = originalRequestId;
        this.openAiService = openAiService;
        this.toolCallsHandler = toolCallsHandler;
        this.listener = listener;
        this.countDownLatch = countDownLatch;
    }

    private boolean init(ChatCompletion chatCompletion) {
        if (Preconditions.isBlank(active)) {
            if (Preconditions.isNotBlank(chatCompletion)) {
                if (Preconditions.isNotBlank(chatCompletion.getChoices())
                        && Preconditions.isNotBlank(chatCompletion.getChoices().get(0).getDelta())) {
                    ChatMessage delta = chatCompletion.getChoices().get(0).getDelta();
                    if (Preconditions.isNotBlank(delta.getContent()) || "".equals(delta.getContent())) {
                        active = false;
                    } else {
                        active = true;
                    }
                }
            } else {
                active = Preconditions.isNotBlank(chatCompletions);
            }
        }
        return active != null;
    }

    /**
     * @param chatCompletion last response from openAI api
     * @return true if response is tool calls
     */
    public boolean receive(ChatCompletion chatCompletion) {
        if (init(chatCompletion)) {
            if (active) {
                chatCompletions.add(chatCompletion);
                completionTokens++;
                return true;
            }
        }
        return false;
    }

    /**
     * @param requestId request ID
     * @return true if response is tool calls
     */
    public boolean receiveDone(String requestId) {
        if (init(null)) {
            if (active) {
                ChatCompletion result = chatCompletions.get(0);
                List<ToolCall> toolCalls = new ArrayList<>();
                for (ChatCompletion chatCompletion : chatCompletions) {
                    if (Preconditions.isNotBlank(chatCompletion.getChoices())
                            && Preconditions.isNotBlank(chatCompletion.getChoices().get(0).getDelta())
                            && Preconditions.isNotBlank(chatCompletion.getChoices().get(0).getDelta().getToolCalls())) {
                        ToolCall toolCall = chatCompletion.getChoices().get(0).getDelta().getToolCalls().get(0);
                        if (Preconditions.isNotBlank(toolCall.getId())) {
                            // new function start
                            toolCalls.add(toolCall);
                        } else {
                            // appending part
                            String appendArgs = toolCall.getFunction().getArguments();
                            if (Preconditions.isNotBlank(appendArgs)) {
                                ToolCall tc = toolCalls.get(toolCalls.size() - 1);
                                tc.getFunction().setArguments(tc.getFunction().getArguments() + appendArgs);
                            }
                        }
                    }
                }
                result.getChoices().get(0).getDelta().setToolCalls(toolCalls);
                StreamToolCallsRequest request = toolCallsHandler.apply(requestId, result);
                this.requestId = request.getRequestId();
                createChatCompletion(request.getRequest());
                return true;
            }
        }
        return false;
    }

    private void createChatCompletion(CreateChatCompletionRequest request) {
        openAiService.createSteamChatCompletion(requestId, request, new StreamChatCompletionListener() {
            @Override
            public void onOpen(String requestId, Response response) {
                log.debug("on open {}", requestId);
                listener.onOpen(requestId, response);
            }

            @Override
            public void onEvent(String requestId, ChatCompletion chatCompletion) {
                log.debug("chatCompletion {}", JSON.toJSONString(chatCompletion));
                listener.onEvent(requestId, chatCompletion);
            }

            @Override
            public void onEventDone(String requestId) {
                log.debug("event done {}", requestId);
                listener.onEventDone(requestId);
            }

            @Override
            public void onClosed(String requestId) {
                log.debug("event done {}", requestId);
                listener.onClosed(requestId);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(String requestId, Throwable t, Response response) {
                log.debug("event failure {} {} {}", requestId, t, response);
                listener.onFailure(requestId, t, response);
                countDownLatch.countDown();
            }
        });
    }

}
