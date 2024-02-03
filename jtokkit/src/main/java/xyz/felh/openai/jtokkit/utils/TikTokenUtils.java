package xyz.felh.openai.jtokkit.utils;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.SerializationUtils;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.chat.ChatMessageRole;
import xyz.felh.openai.chat.CreateChatCompletionRequest;
import xyz.felh.openai.chat.tool.Tool;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.chat.tool.ToolChoice;
import xyz.felh.openai.chat.tool.Type;
import xyz.felh.openai.jtokkit.Encodings;
import xyz.felh.openai.jtokkit.api.Encoding;
import xyz.felh.openai.jtokkit.api.EncodingRegistry;
import xyz.felh.openai.jtokkit.api.EncodingType;
import xyz.felh.openai.jtokkit.api.ModelType;
import xyz.felh.openai.utils.ListUtils;
import xyz.felh.openai.utils.Preconditions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.ToIntBiFunction;
import java.util.function.ToIntFunction;

@Slf4j
public class TikTokenUtils {
    /**
     * 模型名称对应Encoding
     */
    private static final Map<String, Encoding> modelMap = new HashMap<>();
    /**
     * registry实例
     */
    private static final EncodingRegistry registry = Encodings.newDefaultEncodingRegistry();

    static {
        for (ModelType modelType : ModelType.values()) {
            modelMap.put(modelType.getName(), registry.getEncodingForModel(modelType));
        }
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_1106.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO_0125));
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_INSTRUCT.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO_0125));
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_0125.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO_0125));

        modelMap.put(ChatCompletion.Model.GPT_4_32K.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_1106_PREVIEW.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_VISION_PREVIEW.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_0125_PREVIEW.getName(), registry.getEncodingForModel(ModelType.GPT_4));
    }

    /**
     * 通过Encoding和text获取编码数组
     *
     * @param enc  Encoding类型
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(Encoding enc, String text) {
        return isBlank(text) ? new ArrayList<>() : enc.encode(text);
    }

    /**
     * 通过Encoding计算text信息的tokens
     *
     * @param enc  Encoding类型
     * @param text 文本信息
     * @return tokens数量
     */
    public static int tokens(Encoding enc, String text) {
        return encode(enc, text).size();
    }

    /**
     * 通过Encoding和encoded数组反推text信息
     *
     * @param enc     Encoding
     * @param encoded 编码数组
     * @return 编码数组对应的文本信息
     */
    public static String decode(Encoding enc, List<Integer> encoded) {
        return enc.decode(encoded);
    }

    /**
     * 获取一个Encoding对象，通过Encoding类型
     *
     * @param encodingType encodingType
     * @return Encoding
     */
    public static Encoding getEncoding(EncodingType encodingType) {
        return registry.getEncoding(encodingType);
    }

    /**
     * 获取encode的编码数组
     *
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(EncodingType encodingType, String text) {
        if (isBlank(text)) {
            return new ArrayList<>();
        }
        Encoding enc = getEncoding(encodingType);
        return enc.encode(text);
    }

    /**
     * 计算指定字符串的tokens，通过EncodingType
     *
     * @param encodingType encodingType
     * @param text         文本信息
     * @return tokens数量
     */
    public static int tokens(EncodingType encodingType, String text) {
        return encode(encodingType, text).size();
    }

    /**
     * 通过EncodingType和encoded编码数组，反推字符串文本
     *
     * @param encodingType encodingType
     * @param encoded      编码数组
     * @return 编码数组对应的字符串
     */
    public static String decode(EncodingType encodingType, List<Integer> encoded) {
        Encoding enc = getEncoding(encodingType);
        return enc.decode(encoded);
    }


    /**
     * 获取一个Encoding对象，通过模型名称
     *
     * @param modelName 模型名称
     * @return Encoding
     */
    public static Encoding getEncoding(String modelName) {
        Encoding encoding = modelMap.get(modelName);
        if (Preconditions.isBlank(encoding)) {
            if (modelName.toLowerCase().startsWith("ft:")) {
                String baseModel = modelName.split(":")[1];
                encoding = modelMap.get(baseModel);
                if (Preconditions.isBlank(encoding)) {
                    if (baseModel.toLowerCase().startsWith("gpt-3.5")) {
                        encoding = modelMap.get(ModelType.GPT_3_5_TURBO_0125.getName());
                    }
                    if (baseModel.toLowerCase().startsWith("gpt-4")) {
                        encoding = modelMap.get(ModelType.GPT_4.getName());
                    }
                }
            }
        }
        return encoding;
    }

    /**
     * 获取encode的编码数组，通过模型名称
     *
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(String modelName, String text) {
        if (isBlank(text)) {
            return new ArrayList<>();
        }
        Encoding enc = getEncoding(modelName);
        if (Objects.isNull(enc)) {
            log.warn("[{}]模型不存在或者暂不支持计算tokens，直接返回tokens==0", modelName);
            return new ArrayList<>();
        }
        return enc.encode(text);
    }

    /**
     * 通过模型名称, 计算指定字符串的tokens
     *
     * @param modelName 模型名称
     * @param text      文本信息
     * @return tokens数量
     */
    public static int tokens(String modelName, String text) {
        return encode(modelName, text).size();
    }

    /**
     * 计算request的token数量
     *
     * @param request CreateChatCompletionRequest
     * @return tokens count
     */
    public static int estimateTokens(CreateChatCompletionRequest request) {
        List<ChatMessage> messages = request.getMessages();
        List<Tool> tools = request.getTools();
        Object toolChoice = request.getToolChoice();
        String chatModel = request.getModel();

        int tokens = 0;
        tokens += estimateTokensInMessages(chatModel, messages, tools);

        // If there are tools, add the function definitions as they count towards token usage
        if (Preconditions.isNotBlank(tools)) {
            tokens += estimateTokensInTools(chatModel, tools);
        }

        // If there's a system message and tools are present, subtract four tokens
        if (Preconditions.isNotBlank(tools) && messages.stream().anyMatch(it -> it.getRole() == ChatMessageRole.SYSTEM)) {
            tokens -= 4;
        }

        // If function_call is 'none', add one token.
        // If it's a OpenAIFunctionCall object, add 4 + the number of tokens in the function name.
        // If it's undefined or 'auto', don't add anything.
        if (Preconditions.isNotBlank(toolChoice) && !"auto".equals(toolChoice.toString())) {
            if ("none".equals(toolChoice.toString())) {
                tokens += 1;
            } else {
                if (toolChoice instanceof ToolChoice tc) {
                    if (Preconditions.isNotBlank(tc.getFunction().getName())) {
                        tokens += tokens(chatModel, tc.getFunction().getName()) + 4;
                    }
                }
            }
        }
        return tokens;
    }

    public static int estimateTokensInTools(String modelName, List<Tool> tools) {
        Encoding encoding = getEncoding(modelName);
        int tokens = tokens(encoding, FunctionFormat.formatFunctionDefinitions(tools));
        tokens += 9; // Additional tokens for function definition of tools
        return tokens;
    }

    public static int estimateTokensInMessages(String modelName, List<ChatMessage> messages) {
        return estimateTokensInMessages(modelName, messages, null);
    }

    public static int estimateTokensInMessages(String modelName, List<ChatMessage> messages, List<Tool> tools) {
        int tokens = 0;
        int toolMessageSize = (int) messages.stream().filter(it -> it.getRole() == ChatMessageRole.TOOL).count();
        // size = 1, equal
        // size = 2 - 5; 3 - 7, 4 - 9
        if (toolMessageSize > 1) {
            tokens += toolMessageSize * 2 + 1;
        }
        boolean paddedSystem = false;
        for (ChatMessage message : messages) {
            ChatMessage msg = SerializationUtils.clone(message);
            if (msg.getRole() == ChatMessageRole.SYSTEM && Preconditions.isNotBlank(tools) && !paddedSystem) {
                if (Preconditions.isNotBlank(msg.getContent()) && msg.getContent() instanceof String) {
                    msg.setContent(msg.getContent() + "\n");
                }
                paddedSystem = true;
            }
            tokens += estimateTokensInMessage(modelName, msg, toolMessageSize);
        }
        // Each completion (vs message) seems to carry a 3-token overhead
        tokens += 3;
        return tokens;
    }

    /**
     * 通过模型名称计算messages获取编码数组
     * 参考官方的处理逻辑：<a href=https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb>https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb</a>
     *
     * @param modelName 模型名称
     * @param message   消息体
     * @return tokens数量
     */
    public static int estimateTokensInMessage(String modelName, ChatMessage message, int toolMessageSize) {
        Encoding encoding = getEncoding(modelName);
        int tokens = 0;
        // role
        tokens += tokens(encoding, message.getRole().value());

        // content
        if (message.getRole() == ChatMessageRole.TOOL) {
            if (toolMessageSize == 1) {
                tokens += tokens(encoding, message.getContent().toString());
            } else {
                tokens += tokens(encoding, ToolContentFormat.format(message.getContent()));
            }
        } else {
            if (message.getContent() instanceof String) {
                tokens += tokens(encoding, message.getContent().toString());
            } else {
                List<ChatMessage.ContentItem> items = ListUtils.castList(message.getContent(), ChatMessage.ContentItem.class);
                if (Preconditions.isNotBlank(items)) {
                    for (ChatMessage.ContentItem item : items) {
                        if (item.getType() == ChatMessage.ContentType.TEXT) {
                            // 不需要计算type
                            tokens += tokens(encoding, item.getText());
                        } else if (item.getType() == ChatMessage.ContentType.IMAGE_URL) {
                            ChatMessage.ImageUrl imageUrl = item.getImageUrl();
                            // https://openai.com/pricing
                            if (imageUrl.getDetail() == ChatMessage.ImageUrlDetail.LOW) {
                                tokens += 85;
                            } else if (imageUrl.getDetail() == ChatMessage.ImageUrlDetail.HIGH) {
                                tokens += 85;
                                int width = 0;
                                int height = 0;
                                if (imageUrl.getUrl().startsWith("f")) {
                                    // base64
                                    Base64.Decoder decoder = Base64.getDecoder();
                                    try {
                                        String b64 = imageUrl.getUrl();
                                        b64 = b64.substring(b64.indexOf(";base64,") + 8);
                                        b64 = b64.substring(0, b64.length() - 1);
                                        byte[] bytes = decoder.decode(b64);
                                        ByteArrayInputStream inputStream = new ByteArrayInputStream(bytes);
                                        BufferedImage bi = ImageIO.read(inputStream);
                                        if (Preconditions.isNotBlank(inputStream)) {
                                            inputStream.close();
                                        }
                                        width = bi.getWidth();
                                        height = bi.getHeight();
                                    } catch (Exception e) {
                                        log.error("image to base64 error", e);
                                    }
                                } else {
                                    // image url
                                    try {
                                        BufferedImage bi = ImageIO.read(new URL(imageUrl.getUrl()));
                                        width = bi.getWidth();
                                        height = bi.getHeight();
                                    } catch (IOException e) {
                                        throw new RuntimeException(e);
                                    }
                                }
                                // 1 per 512x512
                                int tiles = (int) Math.ceil(width / 512.0) * (int) Math.ceil(height / 512.0);
                                tokens += 170 * tiles;
                            }
                        }
                    }
                }
            }
        }

        // name 如果是 tool的时候不计算 name
        if (Preconditions.isNotBlank(message.getName()) && message.getRole() != ChatMessageRole.TOOL) {
            tokens += tokens(encoding, message.getName()) + 1; // +1 for the name
        }
        if (message.getRole() == ChatMessageRole.ASSISTANT && Preconditions.isNotBlank(message.getToolCalls())) {
            for (ToolCall toolCall : message.getToolCalls()) {
                tokens += 6;
                tokens += tokens(encoding, toolCall.getType().value());
                if (toolCall.getType() == Type.FUNCTION) {
                    if (Preconditions.isNotBlank(toolCall.getFunction().getName())) {
                        tokens += tokens(encoding, toolCall.getFunction().getName());
                    }
                    if (Preconditions.isNotBlank(toolCall.getFunction().getArguments())) {
                        tokens += tokens(encoding, ArgumentFormat.formatArguments(toolCall.getFunction().getArguments()));
                    }
                }
            }
            if (message.getToolCalls().size() > 1) {
                // s1, add delta when multi tools is added
                tokens += 15;
                // s2
                tokens -= (message.getToolCalls().size() - 1) * 5 - 1;
            } else {
                // s1
                // s2
                tokens -= 2;
            }
        }

        if (message.getRole() == ChatMessageRole.TOOL) {
            tokens += 2; // add 2 if role is "tool"
        } else {
            tokens += 3; // Add three per message
        }

        return tokens;
    }

    public static int tokens(String modelName, Object functionCall, List<Tool> tools) {
        Encoding encoding = getEncoding(modelName);
        int sum = 0;
        if (Preconditions.isNotBlank(functionCall)) {
            if (functionCall instanceof JSONObject) {
                sum += tokens(encoding, functionCall.toString());
            }
        }
        sum += tokens(encoding, FunctionFormat.formatFunctionDefinitions(tools));
        sum += 9; // Additional tokens for function definition
        return sum;
    }

    /**
     * 通过模型名称和encoded编码数组，反推字符串文本
     *
     * @param modelName
     * @param encoded
     * @return
     */
    public static String decode(String modelName, List<Integer> encoded) {
        Encoding enc = getEncoding(modelName);
        return enc.decode(encoded);
    }

    public static boolean isBlankChar(int c) {
        return Character.isWhitespace(c) || Character.isSpaceChar(c) || c == 65279 || c == 8234 || c == 0 || c == 12644 || c == 10240 || c == 6158;
    }

    public static boolean isBlankChar(char c) {
        return isBlankChar((int) c);
    }

    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    public static boolean isBlank(CharSequence str) {
        int length;
        if (str != null && (length = str.length()) != 0) {
            for (int i = 0; i < length; ++i) {
                if (!isBlankChar(str.charAt(i))) {
                    return false;
                }
            }

            return true;
        } else {
            return true;
        }
    }

}
