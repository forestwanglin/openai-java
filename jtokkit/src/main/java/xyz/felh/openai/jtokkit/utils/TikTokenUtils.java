package xyz.felh.openai.jtokkit.utils;


import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import xyz.felh.openai.completion.chat.ChatCompletion;
import xyz.felh.openai.completion.chat.ChatMessage;
import xyz.felh.openai.completion.chat.func.Function;
import xyz.felh.openai.jtokkit.Encodings;
import xyz.felh.openai.jtokkit.api.Encoding;
import xyz.felh.openai.jtokkit.api.EncodingRegistry;
import xyz.felh.openai.jtokkit.api.EncodingType;
import xyz.felh.openai.jtokkit.api.ModelType;
import xyz.felh.openai.utils.Preconditions;

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
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_0301.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO));
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_0613.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO));
        modelMap.put(ChatCompletion.Model.GPT_3_5_TURBO_16K.getName(), registry.getEncodingForModel(ModelType.GPT_3_5_TURBO));
        modelMap.put(ChatCompletion.Model.GPT_4_32K.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_32K_0314.getName(), registry.getEncodingForModel(ModelType.GPT_4));
        modelMap.put(ChatCompletion.Model.GPT_4_0314.getName(), registry.getEncodingForModel(ModelType.GPT_4));
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
        int tokensPerMessage = 0;
        int tokensPerName = 0;
        //3.5统一处理
        if (modelName.equals(ChatCompletion.Model.GPT_3_5_TURBO_0301.getName())
                || modelName.equals(ChatCompletion.Model.GPT_3_5_TURBO.getName())) {
            tokensPerMessage = 4;
            tokensPerName = -1;
        }
        // 0613新加的模型
        if (modelName.equals(ChatCompletion.Model.GPT_3_5_TURBO_16K.getName())
                || modelName.equals(ChatCompletion.Model.GPT_3_5_TURBO_0613.getName())) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        }
        //4.0统一处理
        if (modelName.equals(ChatCompletion.Model.GPT_4.getName())
                || modelName.equals(ChatCompletion.Model.GPT_4_0314.getName())
        ) {
            tokensPerMessage = 3;
            tokensPerName = 1;
        }
        if (modelName.equals(ChatCompletion.Model.GPT_4_32K_0314.getName())
                || modelName.equals(ChatCompletion.Model.GPT_4_32K.getName())) {
            // TODO
        }
        int sum = 0;
        for (ChatMessage msg : messages) {
            sum += tokensPerMessage;
            sum += tokens(encoding, msg.getContent());
            sum += tokens(encoding, msg.getRole().value());
            sum += tokens(encoding, msg.getName());
            if (Preconditions.isNotBlank(msg.getName())) {
                sum += tokensPerName;
            }
            if (Preconditions.isNotBlank(msg.getFunctionCall())) {
                sum += 1;
                sum += tokens(encoding, msg.getFunctionCall().getName());
                if (Preconditions.isNotBlank(msg.getFunctionCall().getArguments())) {
                    sum += tokens(encoding, msg.getFunctionCall().getArguments());
                }
            }
        }
        sum += 3;
        return sum;
    }

    public static int tokens(String modelName, Object functionCall, List<Function> functions) {
        Encoding encoding = getEncoding(modelName);
        int sum = 0;
        if (Preconditions.isNotBlank(functionCall)) {
            if (functionCall instanceof JSONObject) {
                sum += tokens(encoding, functionCall.toString());
            }
        }
        for (Function function : functions) {
            sum += tokens(encoding, function.getName());
            sum += tokens(encoding, function.getDescription());
            if (Preconditions.isNotBlank(function.getParameters())) {
                JSONObject jsonObject = (JSONObject) function.getParameters();
                if (jsonObject.containsKey("properties")) {
                    sum -= jsonObject.getJSONObject("properties").keySet().size() * 4;
                }
                String parameters = jsonObject.toString();
                log.info("parameters: {}", parameters);
                sum += tokens(encoding, parameters);
            }
        }
        sum += 16;
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
        if (ChatCompletion.Model.GPT_3_5_TURBO_0301.getName().equals(name)
                || ChatCompletion.Model.GPT_3_5_TURBO_0613.getName().equals(name)
                || ChatCompletion.Model.GPT_3_5_TURBO_16K.getName().equals(name)) {
            return ModelType.GPT_3_5_TURBO;
        }
        if (ChatCompletion.Model.GPT_4.getName().equals(name)
                || ChatCompletion.Model.GPT_4_32K.getName().equals(name)
                || ChatCompletion.Model.GPT_4_32K_0314.getName().equals(name)
                || ChatCompletion.Model.GPT_4_0314.getName().equals(name)) {
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
