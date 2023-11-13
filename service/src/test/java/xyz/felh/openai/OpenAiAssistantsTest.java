package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.openai.assistant.Assistant;
import xyz.felh.openai.assistant.CreateAssistantRequest;
import xyz.felh.openai.assistant.ModifyAssistantRequest;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.interceptor.ExtractHeaderInterceptor;
import xyz.felh.openai.jtokkit.api.ModelType;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;

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
        Assistant assistant = getOpenAiService().retrieveAssistant("asst_FvKu7nYBXL5pmdKF8FEklRop");
        log.info("assistant: {} ", JSON.toJSONString(assistant));
    }

    @Test
    public void modifyAssistant() {
        Assistant assistant = getOpenAiService().modifyAssistant("asst_FvKu7nYBXL5pmdKF8FEklRop",
                ModifyAssistantRequest.builder().name("test-name").build());
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
        log.info("assistants: {} ", JSON.toJSONString(rsp));
    }

}
