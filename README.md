# openai-java

OpenAi API for Java. Including all API from OpenAI official document, and the counting token method.

[![GitHub version](https://img.shields.io/static/v1?label=version&message=v4.0.2024062901&color=blue)](https://github.com/forestwanglin/openai-java)
[![License](https://img.shields.io/static/v1?label=license&message=MIT&color=orange)](https://github.com/forestwanglin/openai-java/blob/main/LICENSE)

## Example Application

- Mobile/PC: https://web.felh.xyz

  <img decoding="async" src="https://raw.githubusercontent.com/forestwanglin/openai-java/main/web.felh.xyz.png" width="30%" />

## Supported APIs

- ENDPOINTS
  - [Models](https://platform.openai.com/docs/api-reference/models)
  - [Chat Completions](https://platform.openai.com/docs/api-reference/chat/create)
  - [Embeddings](https://platform.openai.com/docs/api-reference/embeddings)
  - [Images](https://platform.openai.com/docs/api-reference/images)
  - [Audio](https://platform.openai.com/docs/api-reference/audio)
  - [Files](https://platform.openai.com/docs/api-reference/files)
  - [Moderations](https://platform.openai.com/docs/api-reference/moderations)
  - [Fine-tuning](https://platform.openai.com/docs/api-reference/fine-tuning)
  - [Batch](https://platform.openai.com/docs/api-reference/batch)
- ASSISTANTS
  - [Assistants](https://platform.openai.com/docs/api-reference/assistants)
  - [Threads](https://platform.openai.com/docs/api-reference/threads)
  - [Messages](https://platform.openai.com/docs/api-reference/messages)
  - [Runs](https://platform.openai.com/docs/api-reference/runs)
  - [Run Steps](https://platform.openai.com/docs/api-reference/run-steps)
  - [Vector Stores](https://platform.openai.com/docs/api-reference/vector-stores)
  - [Vector Store Files](https://platform.openai.com/docs/api-reference/vector-stores-files)
  - [Vector Store File Batches](https://platform.openai.com/docs/api-reference/vector-stores-file-batches)
  - [Streaming](https://platform.openai.com/docs/api-reference/assistants-streaming)

## Important update

- [2023-06-18] Support function call by API, and update the method to count tokens for functions after 0613 update by
  OpenAI
- [2023-07-25] Return model limit information by passing consumer to OpenAiService instructor.
- [2023-08-23] Remove api for [Fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes)
  and [Edits](https://platform.openai.com/docs/api-reference/edits)
- [2023-08-24] Support Fine-tuning
- [2023-11-08] Add Model Type gpt-4-1106-preview/gpt-4-vision-preview/gpt-3.5-turbo-instruct. Add param tools and
  tool_call instead of functions and function_call when send create completion request.
- [2023-11-10] Reconstruct Completion and ChatCompletion
- [2023-11-11] Update Image API to support dall-e-3
- [2023-11-13] Update model with latest API
- [2023-11-14] Add API support for Assistants, Threads, Messages and Runs, all of these are Beta version.
- [2023-11-24] Add create speech api - Generates audio from the input text.
- [2023-11-28] Remove api for [Completions](https://platform.openai.com/docs/api-reference/completions)
- [2023-12-04] Remove ModeType.GPT_3_5_TURBO_16K stead of GPT_3_5_TURBO_1106 which is the same length but cheaper.
- [2024-02-02] Add model `gpt-3.5-turbo-0125`, ```gpt-4-0125-preview`, `text-embedding-3-small`, `text-embedding-3-large`.
- [2024-02-07] Support running `tool_calls` in background which means that client needn't handle the `tool_calls` at the first response.
- [2024-04-10] Support stream event for Assistant. 
- [2024-04-17] Add model `gpt-4-turbo-2024-04-09`. Remove `gpt-3-turbo-1106`, `gpt-4-vision-preview`, `gpt-4-1106-preview`, `gpt-4-0125-preview`.
- [2024-04-21] Add api for [Batch](https://platform.openai.com/docs/api-reference/batch) from version `3.9.2024042101`
- [2024-04-21] Replace Assistants, Thread, Messages, Runs with new version on Apr 17th, 2024 in version `4.0.2024062901`
- [2024-05-13] Add model `gpt-4o-2024-05-13` and its new tokenizer `o200k_base`. Add parameter `stream_options`, `logprobs`, `top_logprobs` for `createChatCompletion`.
- [2024-06-08] Add parameter `parallel_tool_calls` when calling create chat completion.
--------
- [2024-06-28] Support Baidu AI API, chat(include stream) only for now.  see [Baidu AI API](https://cloud.baidu.com/doc/WENXINWORKSHOP/s/7ltgucw50).

## How to use

### Maven

```xml

<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>service</artifactId>
    <version>4.0.2024062901</version>
</dependency>
```

```xml
<!-- get tokens count -->
<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>jtokkit</artifactId>
    <version>4.0.2024062901</version>
</dependency>
```
 
### Gradle

```yaml
implementation group: 'xyz.felh', name: 'service', version: '4.0.2024062901'
implementation group: 'xyz.felh', name: 'jtokkit', version: '4.0.2024062901'
```

### sbt

```javascript
libraryDependencies += "xyz.felh" % "service" % "4.0.2024062901"
libraryDependencies += "xyz.felh" % "jtokkit" % "4.0.2024062901"
```

## Example (Spring Boot 3)

- ### 1. Add maven dependency

```xml

<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>service</artifactId>
    <version>4.0.2024062901</version>
</dependency>
```

- ### 2. Init openAIService

There are multiple ways to init openAIService. Create OpenAiService by passing token, or you can init it with your own
OkHttpClient settings.

```java

@Data
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAiApiConfig {
    // OpenAI API token
    private String token;

    // Init directly with token only
    @Bean(name = "openAiService")
    public OpenAiService openAiService() {
        return new OpenAiService(token);
    }
}
```

```java

@Data
@Component
@ConfigurationProperties(prefix = "openai")
public class OpenAiApiConfig {
    // OpenAI API token
    private String token;
    // OpenAI API orgId
    private String orgId;
    // OpenAI API timeout
    private Long timeout;

    // Init with OkHttpClient settings
    @Bean(name = "openAiService")
    public OpenAiService openAiService() {
        ObjectMapper mapper = defaultObjectMapper();
        // Add proxy if need
        // Proxy proxy = new Proxy(Proxy.Type.SOCKS, new InetSocketAddress("127.0.0.1", 1086));
        OkHttpClient client = defaultClient(token, orgId, Duration.ofMillis(timeout))
                .newBuilder()
                .addInterceptor(new ExtractHeaderInterceptor(responseHeaders -> log.info("headers: {}", JSON.toJSONString(responseHeaders))))
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api, client);
    }
}
```

Here is the settings in yml file.

```yaml
openai:
  token: OPEN_AI_API_TOKEN
  org-id: OPEN_AI_ORG_ID
  timeout: 60000
```

### 3. Call API

#### 3.1.a Create Chat Completion (Without stream)

```java
public class OpenAiService {

    public void createStreamChatCompletion() {
        CreateChatCompletionRequest request = CreateChatCompletionRequest.builder()
                .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello, Please count 1 to 10")))
                .model("gpt-3.5-turbo")
                .maxTokens(2048)
                .temperature(0.6)
                .stream(false)
                .build();
        log.info("chatCompletion Request:\n{}", JsonUtils.toPrettyJSONString(request));
        ChatCompletion completionResult = openAiService.createChatCompletion(request);
        log.info("chatCompletion Response:\n{}", JsonUtils.toPrettyJSONString(completionResult));
    }
}
``` 

#### 3.1.b Create Chat Completion (With stream)

##### 3.1.b.1 Register listener for stream (use Flux to serve server-sent events)

```java
public class OpenAiService {

    private Flux<ServerSentEvent<List<String>>> buildFlux(String messageId) {
        Flux<ServerSentEvent<List<String>>> flux = Flux.create(fluxSink -> {

            StreamChatCompletionListener listener = new StreamChatCompletionListener() {
                @Override
                public void onOpen(String requestId, Response response) {
                    log.debug("onOpen {}", requestId);
                }

                @Override
                public void onEvent(String requestId, xyz.felh.openai.completion.chat.ChatCompletion chatCompletion) {
                    ChatCompletionChoice chatCompletionChoice = chatCompletion.getChoices().get(0);
                    if ("stop".equalsIgnoreCase(chatCompletionChoice.getFinishReason())) {
                        log.info("chatCompletion stream is stopped");
                        // send stop signature to client
                        fluxSink.next(ServerSentEvent.<List<String>>builder()
                                .id(requestId)
                                .event("stop")
                                .data(Collections.singletonList("stop"))
                                .build());
                    } else {
                        if (chatCompletionChoice.getDelta() != null && chatCompletionChoice.getDelta().getContent() != null) {
                            // send delta message to client
                            fluxSink.next(ServerSentEvent.<List<String>>builder()
                                    .id(requestId)
                                    .event("message")
                                    .data(Collections.singletonList(chatCompletionChoice.getDelta().getContent()))
                                    .build());
                        }
                    }
                }
            };

            // create stream chat message
            CreateChatCompletionRequest request = CreateChatCompletionRequest.builder()
                    .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello, Please count 1 to 10")))
                    .model("gpt-3.5-turbo")
                    .maxTokens(2048)
                    .temperature(0.8)
                    .stream(true)
                    .build();
            log.info("chatCompletion Request:\n{}", JsonUtils.toPrettyJSONString(request));
            openAiService.createSteamChatCompletion(messageId, request, listener);

            // unsubscribe when user disconnect
            fluxSink.onCancel(() -> {
                log.info("flux cancel {}", messageId);
                listener.close();
            });
        }, FluxSink.OverflowStrategy.LATEST);
        return flux;
    }
}
```

#### 3.2 Create Image

```java
public class OpenAiService {
    public void createImage() {
        CreateImageRequest createImageRequest = CreateImageRequest.builder()
                .prompt("A cute baby dea otter")
                .n(1)
                .size(ImageSize.R_1024X1024)
                .responseFormat(ImageResponseFormat.URL)
                .model(ImageModelType.DALL_E_2.value())
                .build();
        ImageResponse imageResponse = getOpenAiService().createImage(createImageRequest);
        log.info("imageResponse: {}", toJSONString(imageResponse));
    }
}
```

#### 3.3 Create Embedding
```java
public class OpenAiService {
  
    public void createEmbedding() {
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < 4095; i++) {
            sb.append("AGI ");
        }

        log.info("f:"+ TikTokenUtils.tokens(EncodingType.CL100K_BASE, sb.toString().trim()));

        List<String> inputs = new ArrayList<>();
        inputs.add(sb.toString().trim());
        CreateEmbeddingRequest createEmbeddingRequest = CreateEmbeddingRequest.builder()
            .input(inputs)
            .encodingFormat(CreateEmbeddingRequest.EncodingFormat.FLOAT)
            .model("text-embedding-ada-002")
            .build();
        CreateEmbeddingResponse createEmbeddingResponse = getOpenAiService().createEmbeddings(createEmbeddingRequest);
        log.info("createEmbeddingResponse:  {}", toJSONString(createEmbeddingResponse));
    }
}
```

#### 3.4 Create Chat Completion with GPT_4_TURBO with Input Image

```java
public class OpenAiService {

    public void createChatCompletionWithImage() {
        String model = "gpt-4-turbo-2024-04-09";
        ChatMessage chatMessage = new ChatMessage();
        chatMessage.setRole(ChatMessageRole.USER);
        chatMessage.addTextToContent("描述一下图片的内容");
        chatMessage.addImageUrlToContent("https://qn.felh.xyz/ai1.jpg", ChatMessage.ImageUrlDetail.LOW);
        // you can also set image with base64 format
        // chatMessage.addImageWithBase642ContentItem("data:image/jpeg;base64,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", ChatMessage.IMG_DETAIL_HIGH);
        List<ChatMessage> chatMessages = Arrays.asList(
            ChatMessage.builder()
                    .role(ChatMessageRole.SYSTEM)
                    .content("You are a helpful assistant. Do not include pleasantries in your responses. Mark code language tag if there is code.")
                    .build(),
            chatMessage);
        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
            .messages(chatMessages)
            .maxTokens(4096)
            .temperature(0.6)
            .stream(false)
            .user("FU92834923849328943824")
            .model(model)
            .build();
        ChatCompletion chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion: {}", toJSONString(chatCompletion));
    }
}
```

#### 3.5 Create Chat Completion with Tool Calls in background. 

```java
public class OpenAiService {
    
    public void createFunctionCallStreamChatCompletion() {
        final List<ChatMessage> messages = new ArrayList<>();
        messages.add(new ChatMessage(ChatMessageRole.SYSTEM, "You are an assistant."));
        messages.add(new ChatMessage(ChatMessageRole.USER, "What is weather now in Shanghai?"));

        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_7, OptionPreset.PLAIN_JSON)
                .with(new JacksonModule());
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(GetWeatherParam.class);
        JSONObject jsonObject = JSONObject.parseObject(jsonSchema.toString());

        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(messages)
                .model("gpt-3.5-turbo-0125")
                .tools(List.of(Tool.builder()
                        .type(Type.FUNCTION)
                        .function(Function.builder()
                                .name("get_weather")
                                .description("Get the current weather in a given location")
                                .parameters(jsonObject)
                                .build()).build()))
                .toolChoice("auto")
                .build();
        StreamChatCompletionListener listener = new StreamChatCompletionListener() {
            @Override
            public void onOpen(String requestId, Response response) {log.info("on onOpen {}", requestId);
            }

            @Override
            public void onEvent(String requestId, ChatCompletion chatCompletion) {log.info("chatCompletion: {}", JSON.toJSONString(chatCompletion));
            }

            @Override
            public void onEventDone(String requestId) {log.info("on onEventDone {}", requestId);
            }

            @Override
            public void onClosed(String requestId) {log.info("on onClosed {}", requestId);
            }

            @Override
            public void onFailure(String requestId, Throwable t, Response response) {log.info("on failure {} {}", requestId, JSON.toJSONString(response));
            }
        };
        getOpenAiService().createSteamChatCompletion("1234", chatCompletionRequest, listener,
                (requestId, chatCompletion) -> {
            log.info("request Id {}", requestId);
            log.info("chatCompletion {}", chatCompletion);
            if (Preconditions.isNotBlank(chatCompletion)
                    && Preconditions.isNotBlank(chatCompletion.getChoices())
                    && Preconditions.isNotBlank(chatCompletion.getChoices().get(0).getDelta())
                    && Preconditions.isNotBlank(chatCompletion.getChoices().get(0).getDelta().getToolCalls())) {
                List<ToolCall> toolCalls = chatCompletion.getChoices().get(0).getDelta().getToolCalls();
                messages.add(chatCompletion.getChoices().get(0).getDelta());
                for (ToolCall toolCall : toolCalls) {
                    ChatMessage chatMessage = new ChatMessage(ChatMessageRole.TOOL, "晴");
                    chatMessage.setToolCallId(toolCall.getId());
                    messages.add(chatMessage);
                }
            }
            return StreamToolCallsRequest.builder().request(CreateChatCompletionRequest.builder()
                            .messages(messages)
                            .model("gpt-3.5-turbo-0125")
                            .build())
                    .requestId("3444444").build();
        });
    }
}
```

You can find more examples in

- [OpenAiServiceTest.java](https://github.com/forestwanglin/openai-java/blob/main/service/src/test/java/xyz/felh/openai/OpenAiServiceTest.java)
- [OpenAiAssistantsBetaTest.java](https://github.com/forestwanglin/openai-java/blob/main/service/src/test/java/xyz/felh/openai/OpenAiAssistantsBetaTest.java)
- [OpenAiTokkitTest.java](https://github.com/forestwanglin/openai-java/blob/main/service/src/test/java/xyz/felh/openai/OpenAiTokkitTest.java)

## License

Published under the MIT License (https://github.com/forestwanglin/openai-java/blob/main/LICENSE)

