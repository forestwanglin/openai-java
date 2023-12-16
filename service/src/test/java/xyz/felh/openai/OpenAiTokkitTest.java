package xyz.felh.openai;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import okhttp3.OkHttpClient;
import org.junit.jupiter.api.Test;
import retrofit2.Retrofit;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.chat.ChatMessageRole;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.chat.tool.Function;
import xyz.felh.openai.chat.tool.Tool;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.chat.tool.Type;
import xyz.felh.openai.interceptor.ExtractHeaderInterceptor;
import xyz.felh.openai.jtokkit.api.ModelType;
import xyz.felh.openai.jtokkit.utils.TikTokenUtils;
import xyz.felh.openai.utils.Preconditions;

import java.net.InetSocketAddress;
import java.net.Proxy;
import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static xyz.felh.openai.OpenAiService.*;

@Slf4j
public class OpenAiTokkitTest {

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

    @Data
    public static class GetWeatherParam {
        @JsonPropertyDescription("The city and state, e.g. San Francisco, CA")
        @JsonProperty(value = "location", required = true)
        private String location;
//        @JsonPropertyDescription("The unit of temperature")
//        @JsonProperty(value = "unit")
//        private OpenAiServiceTest.Unit unit;
//        @JsonProperty(value = "low", required = true)
//        private L low;
    }

    @Data
    public static class GetLocationParam {
        @JsonPropertyDescription("The longitude, e.g. 130.5555")
        @JsonProperty(value = "longitude")
        private String longitude;
        @JsonPropertyDescription("The latitude")
        @JsonProperty("latitude")
        private String latitude;
//        @JsonProperty("long_longs")
//        private List<L> longLongs;
    }

    @Data
    public static class L {
        @JsonPropertyDescription("The X value")
        @JsonProperty(value = "x", required = true)
        private String x;
        @JsonPropertyDescription("The Y value")
        @JsonProperty(value = "y", required = true)
        private String y;
//        @JsonProperty(value = "z", required = true)
//        private Integer z;
    }

    @Test
    public void chatCompletion() {
        String modelName = ModelType.GPT_3_5_TURBO_1106.getName();
        List<ChatMessage> chatMessages = new ArrayList<>();

//        chatMessages.add(new ChatMessage(ChatMessageRole.SYSTEM, "You are a helpful assistant. Do not include pleasantries in your responses. Mark code language tag if there is code."));
        chatMessages.add(new ChatMessage(ChatMessageRole.USER, "FU0000002342304230234003",
                List.of(ChatMessage.ContentItem.buildText("Hi, what weather is in 广州和深圳?"))));

        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_7, OptionPreset.PLAIN_JSON)
                .with(new JacksonModule(JacksonOption.RESPECT_JSONPROPERTY_REQUIRED));
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);

        List<Tool> tools = Arrays.asList(
                Tool.builder()
                        .type(Type.FUNCTION)
                        .function(Function.builder()
                                .name("get_weather")
                                .description("Get the current weather in a given location")
                                .parameters(JSONObject.parseObject(generator.generateSchema(GetWeatherParam.class).toString()))
                                .build()).build()
                , Tool.builder()
                        .type(Type.FUNCTION)
                        .function(Function.builder()
                                .name("get_location")
                                .description("Get the location from longitude and latitude")
                                .parameters(JSONObject.parseObject(generator.generateSchema(GetLocationParam.class).toString()))
                                .build()).build()
        );

        log.info("tools tokens: {}", TikTokenUtils.estimateTokensInTools(modelName, tools));

        CreateChatCompletionRequest chatCompletionRequest = CreateChatCompletionRequest.builder()
                .messages(chatMessages)
                .model(modelName)
                .tools(tools)
                .build();
        log.info("total tokens: {}", TikTokenUtils.estimateTokens(chatCompletionRequest));
        ChatCompletion chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
        log.info("chatCompletion: " + toJSONString(chatCompletion));

        List<ToolCall> toolCalls = chatCompletion.getChoices().get(0).getMessage().getToolCalls();
        if (Preconditions.isNotBlank(toolCalls)) {
            // add response message to new request
            ChatMessage chatMessage = chatCompletion.getChoices().get(0).getMessage();
            chatMessage.setContent("");
            chatMessages.add(chatMessage);
            // You can change to call your own function to get weather in parallel
            for (ToolCall toolCall : toolCalls) {
//                log.info("fc: {}", toolCall.getFunction());
                ChatMessage cm = new ChatMessage(ChatMessageRole.TOOL, "Sunny");
                cm.setToolCallId(toolCall.getId());
                chatMessages.add(cm);
            }
            chatCompletionRequest.setToolChoice(null);
            chatCompletionRequest.setTools(null);
            log.info("prompts: {}", TikTokenUtils.estimateTokens(chatCompletionRequest));
            chatCompletion = getOpenAiService().createChatCompletion(chatCompletionRequest);
            log.info("request: " + toJSONString(chatCompletionRequest));
            log.info("chatCompletion: " + toJSONString(chatCompletion));
        }

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
    public void mock() throws JsonProcessingException {
//        String json = """
//                {"model":"gpt-3.5-turbo-1106","messages":[{"role":"user","content":[{"type":"text","text":"Hi, what weather is in Shanghai?","image_url":null}],"name":"FU0000002342304230234003","tool_calls":null,"tool_call_id":null},{"role":"assistant","content":"","name":null,"tool_calls":[{"id":"call_WW3uyZgruLEGV5FfFqEHJdWP","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\":\\"Shanghai\\"}"}}],"tool_call_id":null},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_WW3uyZgruLEGV5FfFqEHJdWP"}],"frequency_penalty":null,"logit_bias":null,"max_tokens":null,"n":null,"presence_penalty":null,"response_format":null,"seed":null,"stop":null,"stream":false,"temperature":null,"top_p":null,"tools":null,"tool_choice":null,"user":null}
//                """;
//        String json = """
//                {"model":"gpt-3.5-turbo-1106","messages":[{"role":"user","content":[{"type":"text","text":"Hi, what weather is in Shanghai and Beijing?","image_url":null}],"name":"FU0000002342304230234003","tool_calls":null,"tool_call_id":null},{"role":"assistant","content":"","name":null,"tool_calls":[{"id":"call_6E7nDJMUTsKq9V83bOsgTEt3","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Shanghai\\", \\"unit\\": \\"celsius\\"}"}},{"id":"call_0FeVYss3MPCT5FJ8fYhWQsBd","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Beijing\\", \\"unit\\": \\"celsius\\"}"}}],"tool_call_id":null},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_6E7nDJMUTsKq9V83bOsgTEt3"},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_0FeVYss3MPCT5FJ8fYhWQsBd"}],"frequency_penalty":null,"logit_bias":null,"max_tokens":null,"n":null,"presence_penalty":null,"response_format":null,"seed":null,"stop":null,"stream":false,"temperature":null,"top_p":null,"tools":null,"tool_choice":null,"user":null}
//                 """;
//        String json = """
//                {"model":"gpt-3.5-turbo-1106","messages":[{"role":"user","content":[{"type":"text","text":"Hi, what weather is in Shanghai, Suzhou and Nanjing?","image_url":null}],"name":"FU0000002342304230234003","tool_calls":null,"tool_call_id":null},{"role":"assistant","content":"","name":null,"tool_calls":[{"id":"call_Zq8cBotKsktF3QC1dM6LF0sT","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Shanghai\\", \\"unit\\": \\"celsius\\"}"}},{"id":"call_yBj8onLAbfVYuZkZBTEeu3Lr","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Suzhou\\", \\"unit\\": \\"celsius\\"}"}},{"id":"call_WzdayZPO2GNYUOJmSXLHWo8N","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Nanjing\\", \\"unit\\": \\"celsius\\"}"}}],"tool_call_id":null},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_Zq8cBotKsktF3QC1dM6LF0sT"},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_yBj8onLAbfVYuZkZBTEeu3Lr"},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_WzdayZPO2GNYUOJmSXLHWo8N"}],"frequency_penalty":null,"logit_bias":null,"max_tokens":null,"n":null,"presence_penalty":null,"response_format":null,"seed":null,"stop":null,"stream":false,"temperature":null,"top_p":null,"tools":null,"tool_choice":null,"user":null}
//
//                """;
//        String json = """
//                 {"model":"gpt-3.5-turbo-1106","messages":[{"role":"user","content":[{"type":"text","text":"Hi, what weather is in Shanghai and 苏州?","image_url":null}],"name":"FU0000002342304230234003","tool_calls":null,"tool_call_id":null},{"role":"assistant","content":"","name":null,"tool_calls":[{"id":"call_8RjeAxqP0MSHzzvREXlj2WiX","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Shanghai\\", \\"unit\\": \\"celsius\\"}"}},{"id":"call_OBV3MpNAdHKWbeEpzUgFG9jm","type":"function","function":{"name":"get_weather","arguments":"{\\"location\\": \\"Suzhou\\", \\"unit\\": \\"celsius\\"}"}}],"tool_call_id":null},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_8RjeAxqP0MSHzzvREXlj2WiX"},{"role":"tool","content":"Sunny","name":null,"tool_calls":null,"tool_call_id":"call_OBV3MpNAdHKWbeEpzUgFG9jm"}],"frequency_penalty":null,"logit_bias":null,"max_tokens":null,"n":null,"presence_penalty":null,"response_format":null,"seed":null,"stop":null,"stream":false,"temperature":null,"top_p":null,"tools":null,"tool_choice":null,"user":null}
//                """;
        String json = """
                 {
                  "max_tokens": 4096,
                  "messages": [
                    {
                      "content": "You are a helpful assistant. Do not include pleasantries in your responses. Mark code language tag if there is code.",
                      "role": "system"
                    },
                    {
                      "content": "你好啊",
                      "role": "user"
                    }
                  ],
                  "model": "gpt-3.5-turbo-1106",
                  "seed": 2645,
                  "stream": true,
                  "temperature": 0.7,
                  "tools": [
                    {
                      "function": {
                        "description": "Get the current weather in a given location",
                        "name": "get_weather",
                        "parameters": {
                          "$schema": "http://json-schema.org/draft-07/schema#",
                          "type": "object",
                          "properties": {
                            "live": {
                              "type": "boolean",
                              "description": "Whether live or forecast."
                            },
                            "location": {
                              "type": "string",
                              "description": "The city and state with Chinese, e.g. San Francisco, CA."
                            },
                            "unit": {
                              "type": "string",
                              "enum": [
                                "celsius",
                                "fahrenheit"
                              ]
                            }
                          }
                        }
                      },
                      "type": "function"
                    }
                  ],
                  "user": "FU9834501019003831"
                }
                """;
        ObjectMapper objectMapper = new ObjectMapper();
        CreateChatCompletionRequest request = objectMapper.readValue(json, CreateChatCompletionRequest.class);
        log.info("request: {}", toJSONString(request));
        for (ChatMessage message : request.getMessages()) {
            if (!(message.getContent() instanceof String)) {
                List<ChatMessage.ContentItem> ci = objectMapper.readValue(JSONObject.toJSONString(message.getContent()), new TypeReference<>() {
                });
                message.setContent(ci);
            }
        }
        log.info("total tokens: {}", TikTokenUtils.estimateTokens(request));
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