package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.vertx.json.schema.common.dsl.SchemaBuilder;
import io.vertx.json.schema.common.dsl.SchemaType;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import okhttp3.Response;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.openai.audio.AudioResponse;
import xyz.felh.openai.audio.CreateAudioTranscriptionRequest;
import xyz.felh.openai.audio.CreateAudioTranslationRequest;
import xyz.felh.openai.completion.Completion;
import xyz.felh.openai.completion.CreateCompletionRequest;
import xyz.felh.openai.completion.chat.ChatCompletion;
import xyz.felh.openai.completion.chat.ChatMessage;
import xyz.felh.openai.completion.chat.ChatMessageRole;
import xyz.felh.openai.completion.chat.CreateChatCompletionRequest;
import xyz.felh.openai.completion.chat.func.Function;
import xyz.felh.openai.embedding.CreateEmbeddingRequest;
import xyz.felh.openai.embedding.CreateEmbeddingResponse;
import xyz.felh.openai.file.File;
import xyz.felh.openai.file.RetrieveFileContentResponse;
import xyz.felh.openai.fineTuning.CreateFineTuningJobRequest;
import xyz.felh.openai.fineTuning.FineTuningJob;
import xyz.felh.openai.fineTuning.FineTuningJobEvent;
import xyz.felh.openai.image.CreateImageRequest;
import xyz.felh.openai.image.ImageResponse;
import xyz.felh.openai.image.edit.CreateImageEditRequest;
import xyz.felh.openai.image.variation.CreateImageVariationRequest;
import xyz.felh.openai.interceptor.ExtractHeaderInterceptor;
import xyz.felh.openai.jtokkit.utils.TikTokenUtils;
import xyz.felh.openai.model.Model;
import xyz.felh.openai.moderation.CreateModerationRequest;
import xyz.felh.openai.moderation.CreateModerationResponse;
import xyz.felh.openai.utils.Preconditions;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static io.vertx.json.schema.common.dsl.Schemas.*;
import static xyz.felh.openai.OpenAiService.*;

@Slf4j
public class OpenAiServiceTest {

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
    public void listModels() {
        List<Model> models = getOpenAiService().listModels();
        log.info("model size: " + models.size());
    }

    @Test
    public void getModel() {
        Model model = getOpenAiService().getModel("gpt-3.5-turbo-0301");
        log.info("model gpt-3.5-turbo: {}", toJSONString(model));
    }

    @Test
    public void createStreamChatCompletion() {
        StreamChatCompletionListener listener = new StreamChatCompletionListener() {
            @Override
            public void onEvent(String requestId, ChatCompletion chatCompletion) {
                log.info("model gpt-3.5-turbo: {}", chatCompletion.getChoices().get(0).getDelta().getContent());
            }

            @Override
            public void onFailure(String requestId, Throwable t, Response response) {
                t.printStackTrace();
            }
        };
        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(Arrays.asList(
                        new ChatMessage(ChatMessageRole.SYSTEM, "You are a helpful assistant. Do not include pleasantries in your responses."),
                        new ChatMessage(ChatMessageRole.USER, "你好，讲个笑话", "FU0837801026829335"),
                        new ChatMessage(ChatMessageRole.ASSISTANT, "抱歉，我是一名助手，不会开玩笑。有什么需要我帮忙的吗?"),
                        new ChatMessage(ChatMessageRole.USER, "真的不会吗?", "FU0837801026829335")))
                .model("gpt-3.5-turbo")
                .build();
        getOpenAiService().createSteamChatCompletion("1234", chatCompletionRequest, listener);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createCompletion() {
        CreateCompletionRequest completionRequest = CreateCompletionRequest.builder()
                .prompt("Somebody once told me the world is gonna roll me")
                .echo(true)
                .model("ada")
                .build();
        Completion completion = getOpenAiService().createCompletion(completionRequest);
        log.info("completion: {}", toJSONString(completion));
    }

    @Test
    public void createChatCompletion() {
        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(Arrays.asList(
//                        new ChatMessage(ChatMessageRole.USER, "Hello", "u1"),
//                        new ChatMessage(ChatMessageRole.ASSISTANT, "Hi there! How may I assist you today?"),
                        new ChatMessage(ChatMessageRole.USER, "Count 1 to 3", "u123")))
                .model("gpt-3.5-turbo")
                .build();
        ChatCompletion chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion: " + toJSONString(chatCompletion));
    }

    @Data
    class GetWeather {
        private String location;
        private int hour;
        private String height;
        private boolean good;
    }

    private void removeId(Object obj) {
        if (Preconditions.isNotBlank(obj)) {
            if (obj instanceof JSONObject) {
                JSONObject jsonObject = (JSONObject) obj;
                jsonObject.remove("$id");
                for (String key : jsonObject.keySet()) {
                    removeId(jsonObject.get(key));
                }
            } else if (obj instanceof JSONArray) {
                JSONArray jsonArray = ((JSONArray) obj);
                for (Object o : jsonArray) {
                    removeId(o);
                }
            }
        }
    }

    private String get_current_weather_of_the_world(String location, String unit) {
        log.info("a {}, b {}", location, unit);
        return "10-20度,多云，大风";
    }

    @Test
    public void createFunctionChatCompletion() {
        SchemaBuilder objectSchemaBuilder = objectSchema()
                .property("location", stringSchema()
                        .withKeyword("description", "The city and state, e.g. San Francisco, CA"))
                .property("unit", enumSchema("celsius", "fahrenheit").type(SchemaType.STRING))
                .property("age", intSchema());
        JSONObject jsonObject = JSON.parseObject(objectSchemaBuilder.toJson().toString());
        removeId(jsonObject);

        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM, "You are an assistant."));
        messages.add(new ChatMessage(ChatMessageRole.USER, "How many miles from here to Beijing?", "forest"));

        SchemaBuilder objectSchemaBuilder2 = objectSchema()
                .property("location", stringSchema()
                        .withKeyword("description", "The city and state, e.g. San Francisco, CA"))
                .property("city", stringSchema().withKeyword("description", "The city or state of the location"))
                .property("longitude", intSchema().withKeyword("description", "The longitude of the location"))
                .property("latitude", intSchema().withKeyword("description", "The latitude of the location"));
        JSONObject jsonObject2 = JSON.parseObject(objectSchemaBuilder2.toJson().toString());
        removeId(jsonObject2);


        List<Function> functions = Arrays.asList(
                Function.builder()
                        .name("get_current_weather")
                        .description("Get the current weather in a given location")
                        .parameters(jsonObject)
                        .build(),
                Function.builder()
                        .name("get_location")
                        .description("Get the current user's location")
                        .parameters(jsonObject2)
                        .build()

        );

        JSONObject fc = new JSONObject();
        fc.put("name", "get_location");
        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo-0613")
                .functions(functions)
                .functionCall(fc)
//                .maxTokens(1)
                .stream(false)
                .build();
        log.info("prompts: {} {} {}", TikTokenUtils.tokens("gpt-3.5-turbo-0613", messages),
                TikTokenUtils.tokens("gpt-3.5-turbo-0613", fc, functions)
                , TikTokenUtils.tokens("gpt-3.5-turbo-0613", messages) + TikTokenUtils.tokens("gpt-3.5-turbo-0613", fc, functions));
//        ChatCompletion chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
//        log.info("request: " + toJSONString(chatCompletionRequest));
//        log.info("chatCompletion: " + toJSONString(chatCompletion));
//
//        FunctionCall functionCall = chatCompletion.getChoices().get(0).getMessage().getFunctionCall();
//        if (Preconditions.isNotBlank(functionCall)) {
//            log.info("fc: {}", chatCompletion.getChoices().get(0).getMessage().getFunctionCall());
//
//            ChatMessage chatMessage = chatCompletion.getChoices().get(0).getMessage();
//            chatMessage.setContent("");
//            messages.add(chatMessage);
//            messages.add(new ChatMessage(ChatMessageRole.FUNCTION, "shanghai", functionCall.getName()));
//
//            log.info("prompts: {}", TikTokenUtils.tokens("gpt-3.5-turbo-0613", messages));
//            chatCompletionRequest.setFunctions(null);
//            chatCompletionRequest.setFunctionCall(null);
//            chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
//            log.info("request: " + toJSONString(chatCompletionRequest));
//            log.info("chatCompletion: " + toJSONString(chatCompletion));
//
//        }
//
//        List<ChatMessage> messages1 = new ArrayList<>();
//        messages1.add(new ChatMessage(ChatMessageRole.USER, "What's the weather like in Shanghai?", "u12323"));
//        messages1.add(new ChatMessage(ChatMessageRole.ASSISTANT, "", null, FunctionCall.builder()
//                .name("get_current_weather_of_the_world")
//                .arguments("{\n  \"location\": \"Shanghai\"\n}")
//                .build()));
//        messages1.add(new ChatMessage(ChatMessageRole.FUNCTION, "10-20度", "get_current_weather_of_the_world"));
//        log.info("prompts: {}", TikTokenUtils.tokens("gpt-3.5-turbo-0613", messages1));

    }


    @Test
    public void createFunctionCallStreamChatCompletion() {
        StreamChatCompletionListener listener = new StreamChatCompletionListener() {
            @Override
            public void onEvent(String requestId, ChatCompletion chatCompletion) {
                log.info("chatCompletion: {}", JSON.toJSONString(chatCompletion));
                log.info("content: {}", chatCompletion.getChoices().get(0).getDelta().getContent());
            }

            @Override
            public void onFailure(String requestId, Throwable t, Response response) {
                log.info("on failure {}", JSON.toJSONString(response));
                t.printStackTrace();
            }
        };
        List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM, "You are an assistant."));
        messages.add(new ChatMessage(ChatMessageRole.USER, "What is weather now in Beijing?", "forest"));

        SchemaBuilder objectSchemaBuilder = objectSchema()
                .property("location", stringSchema()
                        .withKeyword("description", "The city and state, e.g. San Francisco, CA"));
        JSONObject jsonObject = JSON.parseObject(objectSchemaBuilder.toJson().toString());
        removeId(jsonObject);
        List<Function> functions = Arrays.asList(
                Function.builder()
                        .name("get_current_weather")
                        .description("Get the current weather in a given location")
                        .parameters(jsonObject)
                        .build()

        );

        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo-0613")
                .functions(functions)
                .functionCall("auto")
                .build();
        getOpenAiService().createSteamChatCompletion("1234", chatCompletionRequest, listener);

        try {
            Thread.sleep(10000L);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    public void createImage() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("A cute baby dea otter")
                .n(1)
//                .responseFormat("b64_json") // or url
                .build();
        ImageResponse imageResponse = getOpenAiService().createImage(createImageRequest);
        log.info("imageResponse: {}", toJSONString(imageResponse));
    }

    @Test
    public void createImageEdit() {
        CreateImageEditRequest createImageEditRequest = CreateImageEditRequest.builder()
                .prompt("A cute baby sea otter wearing a beret")
                .imagePath("/Users/forest/image_edit_original.png")
                .maskPath("/Users/forest/image_edit_mask.png")
//                .responseFormat("b64_json") // or url
                .build();
        ImageResponse imageEditResponse = getOpenAiService().createImageEdit(createImageEditRequest);
        log.info("imageEditResponse: {}", toJSONString(imageEditResponse));
    }

    @Test
    public void createImageVariation() {
        CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
//                    .image("/Users/forest/image_edit_original.png")
                .n(2)
                .size("256x256")
                .build();
        ImageResponse imageVariationResponse = getOpenAiService().createImageVariation(createImageVariationRequest);
        log.info("imageVariationResponse: {} ", toJSONString(imageVariationResponse));

    }

    @Test
    public void createEmbedding() {
        CreateEmbeddingRequest createEmbeddingRequest = CreateEmbeddingRequest.builder()
                .input("The food was delicious and the waiter...")
                .model("text-embedding-ada-002")
                .build();
        CreateEmbeddingResponse createEmbeddingResponse = getOpenAiService().createEmbeddings(createEmbeddingRequest);
        log.info("createEmbeddingResponse:  {}", toJSONString(createEmbeddingResponse));
    }

    @Test
    public void createModeration() {
        CreateModerationRequest createModerationRequest = CreateModerationRequest.builder()
                .input("I want to kill them.")
                .build();
        CreateModerationResponse createModerationResponse = getOpenAiService().createModeration(createModerationRequest);
        log.info("createModerationResponse: {}", toJSONString(createModerationResponse));
    }

    @Test
    public void createAudioTranscription() {
        CreateAudioTranscriptionRequest createAudioTranscriptionRequest = CreateAudioTranscriptionRequest.builder()
                .model("whisper-1")
                .filePath("/Users/forest/OpenAI.Playground_SampleData_micro-machines.mp3")
                .build();
        AudioResponse audioResponse = getOpenAiService().createAudioTranscription(createAudioTranscriptionRequest);
        log.info("audioResponse: {}", toJSONString(audioResponse));
    }

    @Test
    public void createAudioTranslation() {
        CreateAudioTranslationRequest createAudioTranslationRequest = CreateAudioTranslationRequest.builder()
                .model("whisper-1")
                .filePath("/Users/forest/OpenAI.Playground_SampleData_micro-machines.mp3")
                .build();
        AudioResponse audioResponse2 = getOpenAiService().createAudioTranslation(createAudioTranslationRequest);
        log.info("audioResponse2: {}", toJSONString(audioResponse2));
    }

    @Test
    public void uploadFile() {
        File file = getOpenAiService().uploadFile("/Users/forest/ft.jsonl", "fine-tune");
        log.info("update file: " + toJSONString(file));
    }

    @Test
    public void deleteFile() {
        DeleteResponse deleteFileResponse = getOpenAiService().deleteFile("file-lq7ubCONViIIP0S2AAE2JIYW");
        log.info("delete file: " + toJSONString(deleteFileResponse));
    }

    @Test
    public void listFiles() {
        List<File> files = getOpenAiService().listFiles();
        log.info("list files: " + toJSONString(files));
    }

    @Test
    public void retrieveFile() {
        File retrieveFile = getOpenAiService().retrieveFile("file-x2URJppDcP6GpvKnyWP8S16g");
        log.info("retrieve file: " + toJSONString(retrieveFile));
    }

    @Test
    public void retrieveFileContent() {
        RetrieveFileContentResponse fileContent = getOpenAiService().retrieveFileContent("file-x2URJppDcP6GpvKnyWP8S16g");
        log.info("retrieve file content: {}", toJSONString(fileContent));
    }

    @Test
    public void createFineTuning() {
        CreateFineTuningJobRequest request = new CreateFineTuningJobRequest();
        request.setTrainingFile("file-9cx96z03TZfw6x9PPtgwthMI");
        request.setModel("gpt-3.5-turbo");
        request.setSuffix("felh");
        FineTuningJob fineTuningJob = getOpenAiService().createFineTuningJob(request);
        log.info("createFineTuning: {}", toJSONString(fineTuningJob));
    }

    @Test
    public void retrieveFineTuning() {
        FineTuningJob fineTuningJob = getOpenAiService().retrieveFineTuningJob("ftjob-7uMofI4pJtLAuBmi1CxWQvCa");
        log.info("retrieveFineTuning: {}", toJSONString(fineTuningJob));
    }

    @Test
    public void cancelFineTuning() {
        FineTuningJob fineTuningJob = getOpenAiService().cancelFineTuningJob("ftjob-7uMofI4pJtLAuBmi1CxWQvCa");
        log.info("cancelFineTuning: {}", toJSONString(fineTuningJob));
    }

    @Test
    public void listFineTuningEvents() {
        List<FineTuningJobEvent> fineTuningJobEvents = getOpenAiService().listFineTuningEvents("ftjob-7uMofI4pJtLAuBmi1CxWQvCa", null, null);
        log.info("listFineTuningEvents: {}", toJSONString(fineTuningJobEvents));
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
