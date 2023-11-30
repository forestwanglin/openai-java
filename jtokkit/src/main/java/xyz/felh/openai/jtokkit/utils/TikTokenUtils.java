package xyz.felh.openai.jtokkit.utils;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import xyz.felh.openai.chat.ChatCompletion;
import xyz.felh.openai.chat.ChatMessage;
import xyz.felh.openai.chat.tool.Function;
import xyz.felh.openai.chat.tool.Tool;
import xyz.felh.openai.chat.tool.ToolCall;
import xyz.felh.openai.jtokkit.Encodings;
import xyz.felh.openai.jtokkit.api.Encoding;
import xyz.felh.openai.jtokkit.api.EncodingRegistry;
import xyz.felh.openai.jtokkit.api.EncodingType;
import xyz.felh.openai.jtokkit.api.ModelType;
import xyz.felh.openai.utils.Preconditions;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.*;

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
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_1106.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO));
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_INSTRUCT.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO));
        modelMap.put(ChatCompletion.Model.GPT_4_32K.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_1106_PREVIEW.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_VISION_PREVIEW.getName(), registry.getEncodingForModel(ModelType.GPT_4));
    }

    /**
     * 通过Encoding和text获取编码数组
     *
     * @param enc  Encoding类型
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(Encoding enc, String text) {
        return Preconditions.isBlank(text) ? new ArrayList<>() : enc.encode(text);
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
        Encoding enc = registry.getEncoding(encodingType);
        return enc;
    }

    /**
     * 获取encode的编码数组
     *
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(EncodingType encodingType, String text) {
        if (Preconditions.isBlank(text)) {
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
        return modelMap.get(modelName);
    }

    /**
     * 获取encode的编码数组，通过模型名称
     *
     * @param text 文本信息
     * @return 编码数组
     */
    public static List<Integer> encode(String modelName, String text) {
        if (Preconditions.isBlank(text)) {
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
     * 通过模型名称计算messages获取编码数组
     * 参考官方的处理逻辑：
     * <a href=https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb>https://github.com/openai/openai-cookbook/blob/main/examples/How_to_count_tokens_with_tiktoken.ipynb</a>
     *
     * @param modelName 模型名称
     * @param messages  消息体
     * @return tokens数量
     */
    public static int tokens(String modelName, List<ChatMessage> messages) {
        Encoding encoding = getEncoding(modelName);
        int sum = 0;
        for (ChatMessage msg : messages) {
            sum += 3;
            String content;
            if (msg.getContent() instanceof String) {
                content = msg.getContent().toString();
                sum += tokens(encoding, content);
            } else {
                List<ChatMessage.ContentItem> items = (List<ChatMessage.ContentItem>) msg.getContent();
                for (ChatMessage.ContentItem item : items) {
                    if (item.getType() == ChatMessage.ContentType.TEXT) {
                        // 不需要计算type
                        sum += tokens(encoding, item.getText());
                    } else if (item.getType() == ChatMessage.ContentType.IMAGE_URL) {
                        ChatMessage.ImageUrl imageUrl = item.getImageUrl();
                        // https://openai.com/pricing
                        if (imageUrl.getDetail() == ChatMessage.ImageUrlDetail.LOW) {
                            sum += 85;
                        } else if (imageUrl.getDetail() == ChatMessage.ImageUrlDetail.HIGH) {
                            sum += 85;
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
                                    ByteArrayInputStream bais = new ByteArrayInputStream(bytes);
                                    BufferedImage bi = ImageIO.read(bais);
                                    if (Preconditions.isNotBlank(bais)) {
                                        bais.close();
                                    }
                                    width = bi.getWidth();
                                    height = bi.getHeight();
                                } catch (Exception e) {
                                    e.printStackTrace();
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
                            log.info("tiles {}", tiles);
                            sum += 170 * tiles;
                        }
                    }
                }
            }
            sum += tokens(encoding, msg.getRole().value());
            if (Preconditions.isNotBlank(msg.getToolCalls())) {
                for (ToolCall toolCall : msg.getToolCalls()) {
                    sum += 1;
                    sum += tokens(encoding, toolCall.getFunction().getName());
                    if (Preconditions.isNotBlank(toolCall.getFunction().getArguments())) {
                        sum += tokens(encoding, toolCall.getFunction().getArguments());
                    }
                }
            }
        }
        sum += 3;  // every reply is primed with <|start|>assistant<|message|>
        return sum;
    }

    public static int tokens(String modelName, Object functionCall, List<Tool> tools) {
        Encoding encoding = getEncoding(modelName);
        int sum = 0;
        if (Preconditions.isNotBlank(functionCall)) {
            if (functionCall instanceof JSONObject) {
                sum += tokens(encoding, functionCall.toString());
            }
        }
        for (Tool tool : tools) {
            sum += tokens(encoding, tool.getType().value());
            Function function = tool.getFunction();
            sum += tokens(encoding, function.getName());
            sum += tokens(encoding, function.getDescription());
            if (Preconditions.isNotBlank(function.getParameters())) {
                JSONObject jsonObject = (JSONObject) function.getParameters();
                sum += 11;//tokens(encoding, jsonObject.getString("$schema"));
                if (jsonObject.containsKey("properties")) {
                    JSONObject properties = jsonObject.getJSONObject("properties");
                    for (String propertyKey : properties.keySet()) {
                        sum += tokens(encoding, propertyKey);
                        JSONObject v = properties.getJSONObject(propertyKey);
                        for (String field : v.keySet()) {
                            if ("type".equals(field)) {
                                sum += 2;
                                sum += tokens(encoding, v.getString("type"));
                            } else if ("description".equals(field)) {
                                sum += 1;
                                sum += tokens(encoding, v.getString("description"));
                            } else if ("enum".equals(field)) {
                                for (Object o : v.getJSONArray(field)) {
                                    sum += 3;
                                    sum += tokens(encoding, o.toString());
                                }
                                sum -= 3;
                            } else {
                                log.warn("not supported field {}", field);
                            }
                        }
                    }
                }
            }
        }
        sum += 12;
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


    /**
     * 获取modelType
     *
     * @param name
     * @return
     */
    public static ModelType getModelTypeByName(String name) {
        if (ChatCompletion.Model.GPT_3_5_TURBO.getName().equals(name)
                || ChatCompletion.Model.GPT_3_5_TURBO_INSTRUCT.getName().equals(name)
                || ChatCompletion.Model.GPT_3_5_TURBO_1106.getName().equals(name)) {
            return ModelType.GPT_3_5_TURBO;
        }
        if (ChatCompletion.Model.GPT_4.getName().equals(name)
                || ChatCompletion.Model.GPT_4_32K.getName().equals(name)
                || ChatCompletion.Model.GPT_4_1106_PREVIEW.getName().equals(name)
                || ChatCompletion.Model.GPT_4_VISION_PREVIEW.getName().equals(name)) {
            return ModelType.GPT_4;
        }

        for (ModelType modelType : ModelType.values()) {
            if (modelType.getName().equals(name)) {
                return modelType;
            }
        }
        log.warn("[{}]模型不存在或者暂不支持计算tokens", name);
        return null;
    }

}
