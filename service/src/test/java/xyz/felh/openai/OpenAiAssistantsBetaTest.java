package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.openai.assistant.*;
import xyz.felh.openai.assistant.file.AssistantFile;
import xyz.felh.openai.assistant.file.CreateAssistantFileRequest;
import xyz.felh.openai.interceptor.ExtractHeaderInterceptor;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.List;

import static xyz.felh.openai.OpenAiService.*;

@Slf4j
public class OpenAiAssistantsBetaTest {

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
        Assistant assistant = getOpenAiService().modifyAssistant("asst_U9F4fKQyomAUgdibQpM5D2bs",
                ModifyAssistantRequest.builder().name("test-name")
                        .tools(List.of(AssistantTool.builder().type(AssistantTool.Type.CODE_INTERPRETER.value()).build()))
                        .fileIds(List.of("file-8gJUEdEOixA2pjsbN8NDDWvg"))
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
    public void createAssistantFile() {
        AssistantFile assistantFile = getOpenAiService().createAssistantFile("asst_U9F4fKQyomAUgdibQpM5D2bs",
                CreateAssistantFileRequest.builder()
                .fileId("file-8gJUEdEOixA2pjsbN8NDDWvg")
                .build());
        log.info("assistantFile: {} ", JSON.toJSONString(assistantFile));
    }

    @Test
    public void retrieveAssistantFile() {
        AssistantFile assistantFile = getOpenAiService().retrieveAssistantFile(
                "asst_U9F4fKQyomAUgdibQpM5D2bs",
                "file-8gJUEdEOixA2pjsbN8NDDWvg");
        log.info("assistantFile: {} ", JSON.toJSONString(assistantFile));
    }

    @Test
    public void deleteAssistantFile() {
        DeleteResponse deleteResponse = getOpenAiService().deleteAssistantFile("asst_U9F4fKQyomAUgdibQpM5D2bs",
                "file-8gJUEdEOixA2pjsbN8NDDWvg");
        log.info("deleteResponse: {} ", JSON.toJSONString(deleteResponse));
    }

    @Test
    public void listAssistantFiles() {
        OpenAiApiListResponse<AssistantFile> rsp = getOpenAiService().listAssistantFiles("asst_U9F4fKQyomAUgdibQpM5D2bs");
        log.info("assistant files: {} ", toJSONString(rsp));
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
