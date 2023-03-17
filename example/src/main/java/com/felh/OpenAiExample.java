package com.felh;

import com.felh.openai.OpenAiService;
import com.felh.openai.completion.chat.ChatCompletion;
import com.felh.openai.completion.chat.ChatMessage;
import com.felh.openai.completion.chat.CreateChatCompletionRequest;
import com.felh.openai.embedding.CreateEmbeddingRequest;
import com.felh.openai.embedding.CreateEmbeddingResponse;
import com.felh.openai.file.DeleteFileResponse;
import com.felh.openai.file.File;
import com.felh.openai.moderation.CreateModerationRequest;
import com.felh.openai.moderation.CreateModerationResponse;

import java.util.Arrays;
import java.util.List;

public class OpenAiExample {

    public static void main(String[] args) {
        String sk = System.getenv("OPENAI_TOKEN");

        OpenAiService openAiService = new OpenAiService(sk);
//        List<Model> model = openAiService.listModels();
//        System.out.println(model);
        // gpt-3.5-turbo

//        CompletionRequest completionRequest = CompletionRequest.builder()
//                .prompt("Somebody once told me the world is gonna roll me")
//                .echo(true)
//                .model("ada")
//                .build();
//        Completion completionResult = openAiService.createCompletion(completionRequest);
//        completionResult.getChoices().forEach(System.out::println);

//        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
//                .messages(Arrays.asList(new ChatMessage("user", "Hello")))
//                .model("gpt-3.5-turbo")
//                .build();
//        ChatCompletion chatCompletion = openAiService.createChatCompletion(chatCompletionRequest);
//        chatCompletion.getChoices().forEach(System.out::println);


//        CreateEditRequest editRequest = CreateEditRequest.builder()
//                .model("text-davinci-edit-001")
//                .input("What day of the wek is it?")
//                .instruction("Fix the spelling mistakes")
//                .build();
//        Edit edit = openAiService.createEdit(editRequest);
//        System.out.println(edit);

//        CreateImageRequest createImageRequest = CreateImageRequest.builder()
//                .prompt("A cute baby dea otter")
//                .n(1)
////                .responseFormat("b64_json") // or url
//                .build();
//        Image image = openAiService.generateImage(createImageRequest);
//        System.out.println(image);

//        CreateImageEditRequest createImageEditRequest = CreateImageEditRequest.builder()
//                .prompt("A cute baby sea otter wearing a beret")
//                .image("@otter.png")
//                .mask("@mask.png")
////                .responseFormat("b64_json") // or url
//                .build();
//        CreateImageResponse image = openAiService.createImageEdit(createImageEditRequest);
//        System.out.println(image);

//        CreateEmbeddingRequest createEmbeddingRequest = CreateEmbeddingRequest.builder()
//                .input("The food was delicious and the waiter...")
//                .model("text-embedding-ada-002")
//                .build();
//        CreateEmbeddingResponse createEmbeddingResponse = openAiService.createEmbeddings(createEmbeddingRequest);
//        System.out.println(createEmbeddingResponse);

//        CreateModerationRequest createModerationRequest = CreateModerationRequest.builder()
//                .input("I want to kill them.")
//                .build();
//        CreateModerationResponse createModerationResponse = openAiService.createModeration(createModerationRequest);
//        System.out.println(createModerationResponse);


//      File file=  openAiService.uploadFile("/Users/forest/f", "fine-tune");
//        System.out.println(file);

//        File file = openAiService.retrieveFile("file-aI4BqkIctAkk7ZVzzuQwUdnn");
//        System.out.println(file);
//
//        String fileContent = openAiService.retrieveFileContent("file-aI4BqkIctAkk7ZVzzuQwUdnn");
//        System.out.println(fileContent);

//        List<File> files = openAiService.listFiles();
//        System.out.println(files);
//
//        DeleteFileResponse deleteFileResponse = openAiService.deleteFile("file-aI4BqkIctAkk7ZVzzuQwUdnn");
//        System.out.println(deleteFileResponse);
//
//        files = openAiService.listFiles();
//        System.out.println(files);
    }

}
