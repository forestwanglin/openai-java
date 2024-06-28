package xyz.felh.baidu.chat;


import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.baidu.IBaiduBean;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ChatMessage implements IBaiduBean {

    public ChatMessage(@NonNull ChatMessageRole role, String content) {
        this(role, content, null);
    }

    /**
     * 当前支持以下：
     * user: 表示用户
     * assistant: 表示对话助手
     */
    @NonNull
    @JSONField(name = "role")
    @JsonProperty("role")
    private ChatMessageRole role;

    /**
     * 对话内容
     */
    @NonNull
    @JSONField(name = "content")
    @JsonProperty("content")
    private String content;

    /**
     * message作者
     */
    @JSONField(name = "name")
    @JsonProperty("name")
    private String name;

}
