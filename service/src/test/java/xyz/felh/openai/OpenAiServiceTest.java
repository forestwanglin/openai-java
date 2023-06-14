package xyz.felh.openai;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
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
import xyz.felh.openai.edit.CreateEditRequest;
import xyz.felh.openai.edit.Edit;
import xyz.felh.openai.embedding.CreateEmbeddingRequest;
import xyz.felh.openai.embedding.CreateEmbeddingResponse;
import xyz.felh.openai.file.File;
import xyz.felh.openai.file.RetrieveFileContentResponse;
import xyz.felh.openai.finetune.CreateFineTuneRequest;
import xyz.felh.openai.finetune.FineTune;
import xyz.felh.openai.finetune.FineTuneEvent;
import xyz.felh.openai.image.CreateImageRequest;
import xyz.felh.openai.image.ImageResponse;
import xyz.felh.openai.image.edit.CreateImageEditRequest;
import xyz.felh.openai.image.variation.CreateImageVariationRequest;
import xyz.felh.openai.model.Model;
import xyz.felh.openai.moderation.CreateModerationRequest;
import xyz.felh.openai.moderation.CreateModerationResponse;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.Arrays;
import java.util.List;

import static xyz.felh.openai.OpenAiService.*;

@Slf4j
public class OpenAiServiceTest {

    private OpenAiService getOpenAiService() {
        String sk = System.getenv("OPENAI_TOKEN");
        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1086));
        OkHttpClient client = defaultClient(sk, Duration.ofMillis(300000))
                .newBuilder()
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
                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello", "u1"),
                        new ChatMessage(ChatMessageRole.ASSISTANT, "Hi there! How may I assist you today?"),
                        new ChatMessage(ChatMessageRole.USER, "Count 1 to 3", "u123423423423423423423234")))
                .model("gpt-3.5-turbo")
                .build();
        ChatCompletion chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion: " + toJSONString(chatCompletion));
    }

    @Test
    public void createEdit() {
        CreateEditRequest editRequest = CreateEditRequest.builder()
                .model("text-davinci-edit-001")
                .input("What day of the wek is it?")
                .instruction("Fix the spelling mistakes")
                .build();
        Edit edit = getOpenAiService().createEdit(editRequest);
        log.info("edit: {}", toJSONString(edit));
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
        File file = getOpenAiService().uploadFile("/Users/forest/fineTuningSample.jsonl", "fine-tune");
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
        File retrieveFile = getOpenAiService().retrieveFile("file-lq7ubCONViIIP0S2AAE2JIYW");
        log.info("retrieve file: " + toJSONString(retrieveFile));
    }

    @Test
    public void retrieveFileContent() {
        RetrieveFileContentResponse fileContent = getOpenAiService().retrieveFileContent("file-lq7ubCONViIIP0S2AAE2JIYW");
        log.info("retrieve file content: {}", toJSONString(fileContent));
    }


    @Test
    public void createFineTune() {
        CreateFineTuneRequest createFineTuneRequest = CreateFineTuneRequest.builder()
                .trainingFile("file-eloVljhERlCO2qWNFeTlA0Az")
                .build();
        FineTune fineTune = getOpenAiService().createFineTune(createFineTuneRequest);
        log.info("createFineTune: " + toJSONString(fineTune));
    }

    @Test
    public void listFineTunes() {
        List<FineTune> fineTunes = getOpenAiService().listFineTunes();
        log.info("list fine tunes: " + toJSONString(fineTunes));
    }

    @Test
    public void retrieveFineTune() {
        FineTune fineTune = getOpenAiService().retrieveFineTune("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
        log.info("retrieveFineTune: " + toJSONString(fineTune));
    }

    @Test
    public void cancelFineTune() {
        FineTune cancelFineTune = getOpenAiService().cancelFineTune("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
        log.info("cancelFineTune: " + toJSONString(cancelFineTune));
    }

    @Test
    public void deleteFineTune() {
        DeleteResponse deleteFineTuneResponse = getOpenAiService().deleteFineTune("curie", "ft-4jF2VK5hYHFdwTRKsJe1PX9y");
        log.info("deleteFineTune: " + toJSONString(deleteFineTuneResponse));
    }

    @Test
    public void listFineTuneEvents() {
        List<FineTuneEvent> fineTuneEvents = getOpenAiService().listFineTuneEvents("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
        log.info("listFineTuneEvents: " + toJSONString(fineTuneEvents));
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
