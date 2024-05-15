package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.Response;
import xyz.felh.openai.bean.StreamToolCallsRequest;
import xyz.felh.openai.chat.*;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.utils.Preconditions;

import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.function.BiFunction;

@Data
@Slf4j
public class StreamToolCallsReceiver {

    private ChatCompletion toolCallChatCompletion;
    // null 未初始化
    private Boolean active;
    private BiFunction<String, ChatCompletion, StreamToolCallsRequest> toolCallsHandler;
    private CountDownLatch countDownLatch;
    private final OpenAiService openAiService;
    private StreamListener<ChatCompletion> listener;

    private String originalRequestId;

    // new request params
    private String requestId;
    private boolean failure = false;
    private Throwable t;
    private Response response;

    public StreamToolCallsReceiver(OpenAiService openAiService,
                                   String originalRequestId,
                                   BiFunction<String, ChatCompletion, StreamToolCallsRequest> toolCallsHandler,
                                   StreamListener<ChatCompletion> listener,
                                   CountDownLatch countDownLatch) {
        this.toolCallChatCompletion = null;
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
                        && Preconditions.isNotBlank(chatCompletion.getChoices().getFirst().getDelta())) {
                    ChatMessage delta = chatCompletion.getChoices().getFirst().getDelta();
                    if (Preconditions.isNotBlank(delta.getToolCalls())) {
                        active = true;
                        toolCallChatCompletion = JSON.parseObject(JSON.toJSONString(chatCompletion), ChatCompletion.class);
                        toolCallChatCompletion.getChoices().getFirst().getDelta().getToolCalls().removeFirst();
                    }
                }
            } else {
                active = toolCallChatCompletion != null;
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
            if (active && Preconditions.isNotBlank(toolCallChatCompletion)) {
                List<ToolCall> toolCalls = toolCallChatCompletion.getChoices().getFirst().getDelta().getToolCalls();
                if (Preconditions.isNotBlank(chatCompletion.getChoices())) {
                    ChatCompletionChoice chatCompletionChoice = chatCompletion.getChoices().getFirst();
                    if (Preconditions.isNotBlank(chatCompletionChoice.getDelta())) {
                        ChatMessage chatMessage = chatCompletionChoice.getDelta();
                        if (Preconditions.isNotBlank(chatMessage.getToolCalls())) {
                            ToolCall toolCall = chatMessage.getToolCalls().getFirst();
                            if (Preconditions.isNotBlank(toolCall.getId())) {
                                // new function
                                toolCalls.add(toolCall);
                            } else {
                                // appending arguments
                                String appendArgs = toolCall.getFunction().getArguments();
                                if (Preconditions.isNotBlank(appendArgs)) {
                                    ToolCall tc = toolCalls.getLast();
                                    tc.getFunction().setArguments(tc.getFunction().getArguments() + appendArgs);
                                }
                            }
                        }
                    } else {
                        // stop reason
                        log.info("tool call stop: {}", JSON.toJSONString(chatCompletionChoice.getDelta()));
                    }
                } else {
                    // usage
                    toolCallChatCompletion.setUsage(chatCompletion.getUsage());
                }
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
            if (active && Preconditions.isNotBlank(toolCallChatCompletion)) {
                StreamToolCallsRequest request = toolCallsHandler.apply(requestId, toolCallChatCompletion);
                this.requestId = request.getRequestId();
                createChatCompletion(request.getRequest());
                return true;
            }
        }
        return false;
    }

    private void createChatCompletion(CreateChatCompletionRequest request) {
        openAiService.createSteamChatCompletion(requestId, request, new StreamListener<>() {
            @Override
            public void onOpen(String requestId, Response response) {
                log.debug("stream tool calls receiver on open {}", requestId);
                listener.onOpen(requestId, response);
            }

            @Override
            public void onEvent(String requestId, ChatCompletion chatCompletion) {
                log.debug("stream tool calls receiver requestId: {}, chatCompletion {}",
                        requestId, JSON.toJSONString(chatCompletion));
                listener.onEvent(requestId, chatCompletion);
            }

            @Override
            public void onEventDone(String requestId) {
                log.debug("stream tool calls receiver event done {}", requestId);
                listener.onEventDone(requestId);
            }

            @Override
            public void onClosed(String requestId) {
                log.debug("stream tool calls receiver event closed {}", requestId);
                listener.onClosed(requestId);
                countDownLatch.countDown();
            }

            @Override
            public void onFailure(String requestId, Throwable t, Response response) {
                log.debug("stream tool calls receiver event failure {} {} {}", requestId, t, response);
                listener.onFailure(requestId, t, response);
                countDownLatch.countDown();
            }
        });
    }

}
