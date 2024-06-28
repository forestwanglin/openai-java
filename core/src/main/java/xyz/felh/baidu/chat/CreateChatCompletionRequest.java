package xyz.felh.baidu.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.baidu.IBaiduApiRequest;

import java.util.List;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateChatCompletionRequest implements IBaiduApiRequest {

    /**
     * 聊天上下文信息。说明：
     * （1）messages成员不能为空，1个成员表示单轮对话，多个成员表示多轮对话，例如：
     * · 1个成员示例，"messages": [ {"role": "user","content": "你好"}]
     * · 3个成员示例，"messages": [ {"role": "user","content": "你好"},{"role":"assistant","content":"需要什么帮助"},{"role":"user","content":"自我介绍下"}]
     * （2）最后一个message为当前请求的信息，前面的message为历史对话信息
     * （3）成员数目必须为奇数，成员中message的role值说明如下：奇数位message的role值必须为user，偶数位message的role值为assistant。例如：
     * 示例中message中的role值分别为user、assistant、user、assistant、user；奇数位（红框）message中的role值为user，即第1、3、5个message中的role值为user；偶数位（蓝框）值为assistant，即第2、4个message中的role值为assistant
     * image.png
     * （4）message中的content总长度和system字段总内容不能超过20000个字符，且不能超过5120 tokens
     */
    @NonNull
    @JSONField(name = "messages")
    @JsonProperty("messages")
    private List<ChatMessage> messages;

    /**
     * （1）较高的数值会使输出更加随机，而较低的数值会使其更加集中和确定
     * （2）默认0.8，范围 (0, 1.0]，不能为0
     */
    @JSONField(name = "temperature")
    @JsonProperty("temperature")
    private Double temperature;

    /**
     * 说明：
     * （1）影响输出文本的多样性，取值越大，生成文本的多样性越强
     * （2）默认0.8，取值范围 [0, 1.0]
     */
    @JSONField(name = "top_p")
    @JsonProperty("top_p")
    private Double topP;

    /**
     * 是否以流式接口的形式返回数据，默认false
     */
    @JSONField(name = "stream")
    @JsonProperty("stream")
    private Boolean stream;

    /**
     * 通过对已生成的token增加惩罚，减少重复生成的现象。说明：
     * （1）值越大表示惩罚越大
     * （2）默认1.0，取值范围：[1.0, 2.0]
     */
    @JSONField(name = "penalty_score")
    @JsonProperty("penalty_score")
    private Double penaltyScore;

    /**
     * 是否开启系统记忆，说明：
     * （1）false：未开启，默认false
     * （2）true：表示开启，开启后，system_memory_id字段必填
     */
    @JSONField(name = "enable_system_memory")
    @JsonProperty("enable_system_memory")
    private Boolean enableSystemMemory;

    /**
     * 系统记忆ID，用于读取对应ID下的系统记忆，读取到的记忆文本内容会拼接message参与请求推理
     */
    @JSONField(name = "system_memory_id")
    @JsonProperty("system_memory_id")
    private String systemMemoryId;

    /**
     * 模型人设，主要用于人设设定，例如，你是xxx公司制作的AI助手，说明：
     * （1）长度限制，message中的content总长度和system字段总内容不能超过20000个字符，且不能超过5120 tokens
     */
    @JSONField(name = "system")
    @JsonProperty("system")
    private String system;

    /**
     * 生成停止标识，当模型生成结果以stop中某个元素结尾时，停止文本生成。说明：
     * （1）每个元素长度不超过20字符
     * （2）最多4个元素
     */
    @JSONField(name = "stop")
    @JsonProperty("stop")
    private List<String> stop;

    /**
     * 是否强制关闭实时搜索功能，默认false，表示不关闭
     */
    @JSONField(name = "disable_search")
    @JsonProperty("disable_search")
    private Boolean disableSearch;

    /**
     * 是否开启上角标返回，说明：
     * （1）开启后，有概率触发搜索溯源信息search_info，search_info内容见响应参数介绍
     * （2）默认false，不开启
     */
    @JSONField(name = "enable_citation")
    @JsonProperty("enable_citation")
    private Boolean enableCitation;

    /**
     * 是否返回搜索溯源信息，说明：
     * （1）如果开启，在触发了搜索增强的场景下，会返回搜索溯源信息search_info，search_info内容见响应参数介绍
     * （2）默认false，表示不开启
     */
    @JSONField(name = "enable_trace")
    @JsonProperty("enable_trace")
    private Boolean enableTrace;

    /**
     * 指定模型最大输出token数，说明：
     * （1）如果设置此参数，范围[2, 2048]
     * （2）如果不设置此参数，最大输出token数为1024
     */
    @JSONField(name = "max_output_tokens")
    @JsonProperty("max_output_tokens")
    private Integer maxOutputTokens;

    /**
     * 指定响应内容的格式，说明：
     * （1）可选值：
     * · json_object：以json格式返回，可能出现不满足效果情况
     * · text：以文本格式返回
     * （2）如果不填写参数response_format值，默认为text
     */
    @JSONField(name = "response_format")
    @JsonProperty("response_format")
    private String responseFormat;

    /**
     * 表示最终用户的唯一标识符
     */
    @JSONField(name = "user_id")
    @JsonProperty("user_id")
    private String userId;

}
