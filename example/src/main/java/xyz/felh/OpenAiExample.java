package xyz.felh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import okhttp3.OkHttpClient;
import retrofit2.Retrofit;
import xyz.felh.openai.DeleteResponse;
import xyz.felh.openai.OpenAiApi;
import xyz.felh.openai.OpenAiService;
import xyz.felh.openai.StreamChatCompletionListener;
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

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static xyz.felh.openai.OpenAiService.*;

/**
 * The class of example
 */
public class OpenAiExample {

    /**
     * Main interface
     *
     * @param args
     * @throws JsonProcessingException
     */
    public static void main(String[] args) throws IOException {

        String sk = System.getenv("OPENAI_TOKEN");

        ObjectMapper mapper = defaultObjectMapper();
        Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1086));
        OkHttpClient client = defaultClient(sk, Duration.ofMillis(300000))
                .newBuilder()
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);

        OpenAiService openAiService = new OpenAiService(api, client);


        if (false) {
            List<Model> models = openAiService.listModels();
            System.out.println("model size: " + models.size());
            // gpt-3.5-turbo

            Model model = openAiService.getModel("gpt-3.5-turbo");
            System.out.println("model gpt-3.5-turbo: " + toJSONString(model));

            CreateCompletionRequest completionRequest = CreateCompletionRequest.builder()
                    .prompt("Somebody once told me the world is gonna roll me")
                    .echo(true)
                    .model("ada")
                    .build();
            Completion completion = openAiService.createCompletion(completionRequest);
            System.out.println("completion: " + toJSONString(completion));

            CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                    .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello")))
                    .model("gpt-3.5-turbo")
                    .build();
            ChatCompletion chatCompletion = openAiService.createChatCompletion(chatCompletionRequest);
            System.out.println("chatCompletion: " + toJSONString(chatCompletion));

            CreateEditRequest editRequest = CreateEditRequest.builder()
                    .model("text-davinci-edit-001")
                    .input("What day of the wek is it?")
                    .instruction("Fix the spelling mistakes")
                    .build();
            Edit edit = openAiService.createEdit(editRequest);
            System.out.println("edit: " + toJSONString(edit));

            CreateImageRequest createImageRequest = CreateImageRequest.builder()
                    .prompt("A cute baby dea otter")
                    .n(1)
//                .responseFormat("b64_json") // or url
                    .build();
            ImageResponse imageResponse = openAiService.createImage(createImageRequest);
            System.out.println("imageResponse: " + toJSONString(imageResponse));

            CreateImageEditRequest createImageEditRequest = CreateImageEditRequest.builder()
                    .prompt("A cute baby sea otter wearing a beret")
                    .imagePath("/Users/forest/image_edit_original.png")
                    .maskPath("/Users/forest/image_edit_mask.png")
//                .responseFormat("b64_json") // or url
                    .build();
            ImageResponse imageEditResponse = openAiService.createImageEdit(createImageEditRequest);
            System.out.println("imageEditResponse: " + toJSONString(imageEditResponse));

            CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
//                    .image("/Users/forest/image_edit_original.png")
                    .n(2)
                    .size("256x256")
                    .build();
            ImageResponse imageVariationResponse = openAiService.createImageVariation(createImageVariationRequest);
            System.out.println("imageVariationResponse: " + toJSONString(imageVariationResponse));

            CreateEmbeddingRequest createEmbeddingRequest = CreateEmbeddingRequest.builder()
                    .input("The food was delicious and the waiter...")
                    .model("text-embedding-ada-002")
                    .build();
            CreateEmbeddingResponse createEmbeddingResponse = openAiService.createEmbeddings(createEmbeddingRequest);
            System.out.println("createEmbeddingResponse: " + toJSONString(createEmbeddingResponse));

            CreateModerationRequest createModerationRequest = CreateModerationRequest.builder()
                    .input("I want to kill them.")
                    .build();
            CreateModerationResponse createModerationResponse = openAiService.createModeration(createModerationRequest);
            System.out.println("createModerationResponse: " + toJSONString(createModerationResponse));

            CreateAudioTranscriptionRequest createAudioTranscriptionRequest = CreateAudioTranscriptionRequest.builder()
                    .model("whisper-1")
                    .file("/Users/forest/OpenAI.Playground_SampleData_micro-machines.mp3")
                    .build();
            AudioResponse audioResponse = openAiService.createAudioTranscription(createAudioTranscriptionRequest);
            System.out.println("audioResponse: " + toJSONString(audioResponse));

            CreateAudioTranslationRequest createAudioTranslationRequest = CreateAudioTranslationRequest.builder()
                    .model("whisper-1")
                    .file("/Users/forest/OpenAI.Playground_SampleData_micro-machines.mp3")
                    .build();
            AudioResponse audioResponse2 = openAiService.createAudioTranslation(createAudioTranslationRequest);
            System.out.println("audioResponse2: " + toJSONString(audioResponse2));

            File file = openAiService.uploadFile("/Users/forest/fineTuningSample.jsonl", "fine-tune");
            System.out.println("update file: " + toJSONString(file));

            DeleteResponse deleteFileResponse = openAiService.deleteFile("file-lq7ubCONViIIP0S2AAE2JIYW");
            System.out.println("delete file: " + toJSONString(deleteFileResponse));

            List<File> files = openAiService.listFiles();
            System.out.println("list files: " + toJSONString(files));

            File retrieveFile = openAiService.retrieveFile("file-eloVljhERlCO2qWNFeTlA0Az");
            System.out.println("retrieve file: " + toJSONString(retrieveFile));

            // not for free account
//            String fileContent = openAiService.retrieveFileContent("file-eloVljhERlCO2qWNFeTlA0Az");
//            System.out.println(fileContent);

            CreateFineTuneRequest createFineTuneRequest = CreateFineTuneRequest.builder()
                    .trainingFile("file-eloVljhERlCO2qWNFeTlA0Az")
                    .build();
            FineTune fineTune = openAiService.createFineTune(createFineTuneRequest);
            System.out.println("createFineTune: " + toJSONString(fineTune));

            List<FineTune> fineTunes = openAiService.listFineTunes();
            System.out.println("list fine tunes: " + toJSONString(fineTunes));

            FineTune fineTune1 = openAiService.retrieveFineTune("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
            System.out.println("retrieveFineTune: " + toJSONString(fineTune1));

            FineTune cancelFineTune = openAiService.cancelFineTune("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
            System.out.println("cancelFineTune: " + toJSONString(cancelFineTune));

            DeleteResponse deleteFineTuneResponse = openAiService.deleteFineTune("curie", "ft-4jF2VK5hYHFdwTRKsJe1PX9y");
            System.out.println("deleteFineTune: " + toJSONString(deleteFineTuneResponse));

            List<FineTuneEvent> fineTuneEvents = openAiService.listFineTuneEvents("ft-4jF2VK5hYHFdwTRKsJe1PX9y");
            System.out.println("listFineTuneEvents: " + toJSONString(fineTuneEvents));

        }

        // not for free account
//        RetrieveFileContentResponse fileContent = openAiService.retrieveFileContent("file-zuuXpPWYtGPlFjM2Z6coYy3h");
//        System.out.println("retrieveFileContent: " + toJSONString(fileContent));

//        Model model = openAiService.getModel("gpt-3.5-turbo-0301");
//        System.out.println("model gpt-3.5-turbo: " + toJSONString(model));

//        CreateCompletionRequest completionRequest = CreateCompletionRequest.builder()
//                .prompt("Somebody once told me the world is gonna roll me")
//                .echo(true)
//                .model("ada")
//                .build();
//        Completion completion = openAiService.createCompletion(completionRequest);
//        System.out.println("completion: " + toJSONString(completion));


        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(Collections.singletonList(new ChatMessage(ChatMessageRole.USER, "CWhat's 1+1? Answer in one word.")))
                .model("gpt-3.5-turbo")
                .build();
        openAiService.createSteamChatCompletion("1234", chatCompletionRequest);
        StreamChatCompletionListener listener = new StreamChatCompletionListener() {
        };
        listener.setClientId("cid");
        openAiService.addStreamChatCompletionListener(listener);

//        java.io.File image = new java.io.File("/Users/forest/image_edit_original.png");
//        java.io.File mask = new java.io.File("/Users/forest/image_edit_mask.png");
//        CreateImageEditRequest createImageEditRequest = CreateImageEditRequest.builder()
//                .prompt("A cute baby sea otter wearing a beret")
//                .image(Files.readAllBytes(image.toPath()))
//                .mask(Files.readAllBytes(mask.toPath()))
////                .responseFormat("b64_json") // or url
//                .build();
//        ImageResponse imageEditResponse = openAiService.createImageEdit(createImageEditRequest);
//        System.out.println("imageEditResponse: " + toJSONString(imageEditResponse));

//        java.io.File image = new java.io.File("/Users/forest/image_edit_original.png");
//
//        CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
//                .image(Files.readAllBytes(image.toPath()))
//                .n(1)
//                .size("256x256")
//                .build();
//        ImageResponse imageVariationResponse = openAiService.createImageVariation(createImageVariationRequest);
//        System.out.println("imageVariationResponse: " + toJSONString(imageVariationResponse));

    }

    private static String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        return ob.writeValueAsString(obj);
    }

}
