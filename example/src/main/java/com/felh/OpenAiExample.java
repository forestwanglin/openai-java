package com.felh;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.felh.openai.DeleteResponse;
import com.felh.openai.OpenAiService;
import com.felh.openai.audio.AudioResponse;
import com.felh.openai.audio.CreateAudioTranscriptionRequest;
import com.felh.openai.audio.CreateAudioTranslationRequest;
import com.felh.openai.completion.Completion;
import com.felh.openai.completion.CreateCompletionRequest;
import com.felh.openai.completion.chat.ChatCompletion;
import com.felh.openai.completion.chat.ChatMessage;
import com.felh.openai.completion.chat.ChatMessageRole;
import com.felh.openai.completion.chat.CreateChatCompletionRequest;
import com.felh.openai.edit.CreateEditRequest;
import com.felh.openai.edit.Edit;
import com.felh.openai.embedding.CreateEmbeddingRequest;
import com.felh.openai.embedding.CreateEmbeddingResponse;
import com.felh.openai.file.File;
import com.felh.openai.file.RetrieveFileContentResponse;
import com.felh.openai.finetune.CreateFineTuneRequest;
import com.felh.openai.finetune.FineTune;
import com.felh.openai.finetune.FineTuneEvent;
import com.felh.openai.image.CreateImageRequest;
import com.felh.openai.image.ImageResponse;
import com.felh.openai.image.edit.CreateImageEditRequest;
import com.felh.openai.image.variation.CreateImageVariationRequest;
import com.felh.openai.model.Model;
import com.felh.openai.moderation.CreateModerationRequest;
import com.felh.openai.moderation.CreateModerationResponse;

import java.util.Arrays;
import java.util.List;

public class OpenAiExample {

    public static void main(String[] args) throws JsonProcessingException {
        String sk = System.getenv("OPENAI_TOKEN");

        OpenAiService openAiService = new OpenAiService(sk);

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
                    .image("/Users/forest/image_edit_original.png")
                    .mask("/Users/forest/image_edit_mask.png")
//                .responseFormat("b64_json") // or url
                    .build();
            ImageResponse imageEditResponse = openAiService.createImageEdit(createImageEditRequest);
            System.out.println("imageEditResponse: " + toJSONString(imageEditResponse));

            CreateImageVariationRequest createImageVariationRequest = CreateImageVariationRequest.builder()
                    .image("/Users/forest/image_edit_original.png")
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
//
//        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
//                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello")))
//                .model("gpt-3.5-turbo")
//                .build();
//        ChatCompletion chatCompletion = openAiService.createChatCompletion(chatCompletionRequest);
//        System.out.println("chatCompletion: " + toJSONString(chatCompletion));


    }

    private static String toJSONString(Object obj) throws JsonProcessingException {
        ObjectMapper ob = new ObjectMapper();
        return ob.writeValueAsString(obj);
    }

}
