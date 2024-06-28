package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.StreamListener;
import xyz.felh.openai.assistant.Assistant;
import xyz.felh.openai.assistant.AssistantTool;
import xyz.felh.openai.assistant.CreateAssistantRequest;
import xyz.felh.openai.assistant.ModifyAssistantRequest;
import xyz.felh.openai.assistant.message.CreateMessageRequest;
import xyz.felh.openai.assistant.message.Message;
import xyz.felh.openai.assistant.run.CreateRunRequest;
import xyz.felh.openai.assistant.run.Run;
import xyz.felh.openai.assistant.thread.CreateThreadRequest;
import xyz.felh.openai.assistant.thread.Thread;
import xyz.felh.openai.interceptor.ExtractHeaderInterceptor;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static xyz.felh.openai.OpenAiService.*;

@Slf4j
public class OpenAiAssistantsTest {

    private OpenAiService getOpenAiService() {
        String sk = System.getenv("OPENAI_TOKEN");
        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 7890));
        OkHttpClient client = defaultClient(sk, Duration.ofMillis(300000))
                .newBuilder()
                .addInterceptor(new ExtractHeaderInterceptor(responseHeaders -> log.info("headers: {}", JSON.toJSONString(responseHeaders))))
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api, client);
    }

    @Test
    public void createAssistant() {
        //  you can specify any GPT-3.5 or GPT-4 models, including fine-tuned models. The Retrieval tool requires gpt-3.5-turbo-1106 and gpt-4-1106-preview models.
        Assistant assistant = getOpenAiService().createAssistant(CreateAssistantRequest.builder()
                .model("gpt-3.5-turbo-1106")
                .name("new-one2")
                .build());
        log.info("assistant: {} ", JSON.toJSONString(assistant));
    }

    @Test
    public void retrieveAssistant() {
        Assistant assistant = getOpenAiService().retrieveAssistant("asst_U9F4fKQyomAUgdibQpM5D2bs");
        log.info("assistant: {} ", JSON.toJSONString(assistant));
    }

    @Test
    public void modifyAssistant() {
        Assistant assistant = getOpenAiService().modifyAssistant("asst_U9F4fKQyomAUgdibQpM5D2bs",
                ModifyAssistantRequest.builder().name("test-name")
                        .tools(List.of(AssistantTool.builder().type(AssistantTool.Type.CODE_INTERPRETER).build()))
//                        .fileIds(List.of("file-8gJUEdEOixA2pjsbN8NDDWvg"))
                        .build());
        log.info("assistant: {} ", JSON.toJSONString(assistant));
    }

    @Test
    public void deleteAssistant() {
        DeleteResponse deleteResponse = getOpenAiService().deleteAssistant("asst_FvKu7nYBXL5pmdKF8FEklRop");
        log.info("deleteResponse: {} ", JSON.toJSONString(deleteResponse));
    }

    @Test
    public void listAssistants() {
        OpenAiApiListResponse<Assistant> rsp = getOpenAiService().listAssistants();
        log.info("assistants: {} ", toJSONString(rsp));
    }

    @Test
    public void createThread() {
        Thread thread = getOpenAiService().createThread(
                CreateThreadRequest.builder().build());
        log.info("thread: {} ", JSON.toJSONString(thread));
    }

    @Test
    public void retrieveThread() {
        Thread thread = getOpenAiService().retrieveThread("thread_lx8VJee28W7vGxMo8gcSBtvJ");
        log.info("thread: {} ", JSON.toJSONString(thread));
    }

    @Test
    public void createMessage() {
        Message message = getOpenAiService().createThreadMessage("thread_lx8VJee28W7vGxMo8gcSBtvJ",
                CreateMessageRequest.builder()
                        .role(Message.Role.USER)
                        .content("请问北京到纽约有多远?")
                        .build());
        log.info("message: {} ", JSON.toJSONString(message));
    }

    @Test
    public void retrieveThreadMessage() {
        Message message = getOpenAiService().retrieveThreadMessage("thread_lx8VJee28W7vGxMo8gcSBtvJ",
                "msg_Hhvr2Nb6QfqKxPOyr3lnK7ar");
        log.info("message: {} ", JSON.toJSONString(message));
    }

    @Test
    public void createThreadRun() {
        Run run = getOpenAiService().createThreadRun("thread_lx8VJee28W7vGxMo8gcSBtvJ",
                CreateRunRequest.builder()
                        .assistantId("asst_U9F4fKQyomAUgdibQpM5D2bs")
                        .build());
        log.info("run: {} ", JSON.toJSONString(run));
    }

    @SneakyThrows
    @Test
    public void createStreamThreadRun() {
        getOpenAiService().createThreadRun(
                "test234",
                "thread_60WzghwUWEY8yk8Uhqb7WKOr",
                CreateRunRequest.builder()
                        .assistantId("asst_LH23VSIMJAuVOAQ4vM4CuBhM")
                        .stream(true)
                        .build(), new StreamListener<>() {
                    @Override
                    public void onEvent(String requestId, IOpenAiApiObject iOpenAiApiObject) {
                        log.info("onEvent: {} ", JSON.toJSONString(iOpenAiApiObject));
                    }

                    @Override
                    public void onFailure(String requestId, Throwable t, Response response) {
                        log.info("onFailure: {} ", JSON.toJSONString(t));
                    }
                });
        TimeUnit.MINUTES.sleep(5L);
    }


    @Test
    public void retrieveThreadRun() {
        Run run = getOpenAiService().retrieveThreadRun("thread_lx8VJee28W7vGxMo8gcSBtvJ",
                "run_Jt191oxKEH0h6evQArDd2Sah");
        log.info("run: {} ", JSON.toJSONString(run));
    }

    private String toJSONString(Object obj) {
        ObjectMapper ob = new ObjectMapper();
        try {
            return ob.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

}
