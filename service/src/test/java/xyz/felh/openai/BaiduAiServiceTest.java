package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.StreamListener;
import xyz.felh.baidu.BaiduAiApi;
import xyz.felh.baidu.BaiduAiService;
import xyz.felh.baidu.chat.*;
import xyz.felh.baidu.interceptor.ExtractHeaderInterceptor;
import xyz.felh.baidu.tokenizer.CreateTokenizerRequest;

import java.time.Duration;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static xyz.felh.baidu.BaiduAiService.*;

@Slf4j
public class BaiduAiServiceTest {


    private BaiduAiService getBaiduAiService() {
        String ak = System.getenv("BAIDU_AK");
        String sk = System.getenv("BAIDU_SK");
        ObjectMapper mapper = defaultObjectMapper();
        OkHttpClient client = defaultClient(ak, sk, Duration.ofMillis(300000))
                .newBuilder()
                .addInterceptor(new ExtractHeaderInterceptor(responseHeaders -> log.debug("headers: {}", JSON.toJSONString(responseHeaders))))
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        BaiduAiApi api = retrofit.create(BaiduAiApi.class);
        return new BaiduAiService(api, client);
    }

    @Test
    public void chat() throws InterruptedException {
        BaiduAiService service = getBaiduAiService();

        CreateChatCompletionRequest request = CreateChatCompletionRequest.builder()
                .messages(
                        List.of(ChatMessage.builder()
                                .role(ChatMessageRole.USER)
                                .content("你好,请从1 数到20")
                                .build())
                )
                .build();
//        log.info(JSON.toJSONString(service.chat(ChatCompletion.Model.ERNIE_SPEED_128K, request)));

//        request.setStream(true);
//        service.createStreamChat("asdf", ModelType.ERNIE_SPEED_128K, request, new StreamListener<>() {
//            @Override
//            public void onEvent(String requestId, ChatCompletion t) {
//                log.info(JSON.toJSONString(t));
//            }
//        });
//
//        TimeUnit.MINUTES.sleep(5);
        log.info(JSONObject.toJSONString(service.tokenizer(CreateTokenizerRequest.builder()
                .prompt("asdfaksdlf哈哈哈哈")
                .model("ernie-4.0-8k")
                .build())));
    }

}
