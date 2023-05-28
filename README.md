# openai-java

OpenAi GPT API for Java

## How to use

### Maven

```xml

<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>openai-service</artifactId>
    <version>1.2.2</version>
</dependency>
```

```xml
<!--  add it if need calculate tokens when using gpt3 -->
<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>gpt3-tokenizer</artifactId>
    <version>1.2.2</version>
</dependency>
```

### Gradle

```yaml
implementation group: 'xyz.felh', name: 'openai-service', version: '1.2.2'
implementation group: 'xyz.felh', name: 'gpt3-tokenizer', version: '1.2.2'
```

### sbt

```javascript
libraryDependencies += "xyz.felh" % "openai-service" % "1.2.2"
libraryDependencies += "xyz.felh" % "gpt3-tokenizer" % "1.2.2"
```

## Supported APIs

- [Models](https://platform.openai.com/docs/api-reference/models)
- [Completions](https://platform.openai.com/docs/api-reference/completions)
- [Chat Completions(2023-04-18 support stream)](https://platform.openai.com/docs/api-reference/chat/create)
- [Edits](https://platform.openai.com/docs/api-reference/edits)
- [Embeddings](https://platform.openai.com/docs/api-reference/embeddings)
- [Images](https://platform.openai.com/docs/api-reference/images)
- [Audio](https://platform.openai.com/docs/api-reference/audio)
- [File](https://platform.openai.com/docs/api-reference/files)
- [Moderations](https://platform.openai.com/docs/api-reference/moderations)
- [Fine-tunes](https://platform.openai.com/docs/api-reference/fine-tunes)


## Example (Spring Boot 3)

- ### 1. Add maven dependency

```xml
<dependency>
    <groupId>xyz.felh</groupId>
    <artifactId>openai-service</artifactId>
    <version>1.2.2</version>
</dependency>
```

- ### 2. Init openAIService

There are multiple ways to init openAIService. Create OpenAiService by passing token, or you can init it with your own OkHttpClient settings.


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
                .proxy(proxy)
                .build();
        Retrofit retrofit = defaultRetrofit(client, mapper);
        OpenAiApi api = retrofit.create(OpenAiApi.class);
        return new OpenAiService(api, client);
    }
}
```
Below is the settings in yml file.
```yaml
openai:
  token: OPEN_AI_API_TOKEN
  org-id: OPEN_AI_ORG_ID
  timeout: 60000
```

### 3. Call API

#### 3.1.a Create Chat Completion (Without stream)

```java
CreateChatCompletionRequest request = CreateChatCompletionRequest.builder()
        .messages(Arrays.asList(new ChatMessage(ChatMessageRole.USER, "Hello, Please count 1 to 10")))
        .model("gpt-3.5-turbo")
        .maxTokens(2048) 
        .temperature(0.8)
        .stream(false)
        .user("LOGIC_USER_KEY")
        .build();
log.info("chatCompletion Request:\n{}", JsonUtils.toPrettyJSONString(request));
xyz.felh.openai.completion.chat.ChatCompletion completionResult = openAiService.createChatCompletion(request);
log.info("chatCompletion Response:\n{}", JsonUtils.toPrettyJSONString(completionResult));
``` 

#### 3.1.b Create Chat Completion (With stream)

##### 3.1.b.1 Register listener for stream (use Flux to serve server-sent events)

```java
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
        }
        
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
```

#### 3.2 Create Completion

```java
CreateCompletionRequest request = CreateCompletionRequest.builder()
               .prompt("Somebody once told me the world is gonna roll me")
               .model("ada")
               .echo(true)
               .n(1)
               .user("LOGIC_USER_KEY")
               .build();
log.info("completion Request:\n{}", JsonUtils.toPrettyJSONString(request));
xyz.felh.openai.completion.Completion completionResult = openAiService.createCompletion(request);
log.info("completionResult:\n{}", JsonUtils.toPrettyJSONString(completionResult));
```

You can find more examples in [OpenAiServiceTest.java](https://github.com/forestwanglin/openai-java/blob/main/service/src/test/java/xyz/felh/openai/OpenAiServiceTest.java)

## License

Published under the MIT License (https://github.com/forestwanglin/openai-java/blob/main/LICENSE)

