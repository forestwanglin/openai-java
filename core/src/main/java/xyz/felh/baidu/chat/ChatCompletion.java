package xyz.felh.baidu.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import xyz.felh.baidu.Usage;
import xyz.felh.openai.OpenAiApiObjectWithId;

@EqualsAndHashCode(callSuper = true)
@Data
public class ChatCompletion extends OpenAiApiObjectWithId {

    public static String OBJECT = "chat.completion";
    public static String CHUNK_OBJECT = "chat.completion.chunk";

    /**
     * 时间戳
     */
    @JSONField(name = "created")
    @JsonProperty("created")
    private Long created;

    /**
     * 表示当前子句的序号。只有在流式接口模式下会返回该字段
     */
    @JSONField(name = "sentence_id")
    @JsonProperty("sentence_id")
    private Integer sentenceId;

    /**
     * 表示当前子句是否是最后一句。只有在流式接口模式下会返回该字段
     */
    @JSONField(name = "is_end")
    @JsonProperty("is_end")
    private Boolean isEnd;

    /**
     * 当前生成的结果是否被截断
     */
    @JSONField(name = "is_truncated")
    @JsonProperty("is_truncated")
    private Boolean isTruncated;

    /**
     * 输出内容标识，说明：
     * · normal：输出内容完全由大模型生成，未触发截断、替换
     * · stop：输出结果命中入参stop中指定的字段后被截断
     * · length：达到了最大的token数，根据EB返回结果is_truncated来截断
     * · content_filter：输出内容被截断、兜底、替换为**等
     */
    @JSONField(name = "finish_reason")
    @JsonProperty("finish_reason")
    private String finishReason;

    /**
     * 搜索数据，当请求参数enable_citation或enable_trace为true，并且触发搜索时，会返回该字段
     */
    @JSONField(name = "search_info")
    @JsonProperty("search_info")
    private SearchInfo searchInfo;

    /**
     * 对话返回结果
     */
    @JSONField(name = "result")
    @JsonProperty("result")
    private String result;

    /**
     * 表示用户输入是否存在安全风险，是否关闭当前会话，清理历史会话信息
     * true：是，表示用户输入存在安全风险，建议关闭当前会话，清理历史会话信息
     * false：否，表示用户输入无安全风险
     */
    @JSONField(name = "need_clear_history")
    @JsonProperty("need_clear_history")
    private Boolean needClearHistory;

    /**
     * 说明：返回flag表示触发安全
     */
    @JSONField(name = "flag")
    @JsonProperty("flag")
    private Integer flag;

    /**
     * 当need_clear_history为true时，此字段会告知第几轮对话有敏感信息，如果是当前问题，ban_round=-1
     */
    @JSONField(name = "ban_round")
    @JsonProperty("ban_round")
    private Integer banRound;

    /**
     * token统计信息
     */
    @JSONField(name = "usage")
    @JsonProperty("usage")
    private Usage usage;

    @Getter
    @AllArgsConstructor
    public enum Model {
        // 价格参考 https://cloud.baidu.com/doc/WENXINWORKSHOP/s/hlrk4akp7?feedback=1
        // 以下系列免费
        // ERNIE Speed系列
        // ERNIE Lite系列
        // ERNIE Tiny系列

        YI_34B_CHAT("yi_34b_chat"), // FREE
        ERNIE_4_0_8K("completions_pro"),
        ERNIE_SPEED_8K("ernie_speed"), // FREE
        ERNIE_SPEED_128K("ernie-speed-128k"), // FREE
        ;

        private final String name;

    }

}
