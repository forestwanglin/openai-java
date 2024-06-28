package xyz.felh.baidu.chat;

import lombok.*;
import xyz.felh.baidu.IBaiduBean;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ChatModel implements IBaiduBean {

    private String name;
    private String pathName;

    // 价格参考 https://cloud.baidu.com/doc/WENXINWORKSHOP/s/hlrk4akp7?feedback=1
    // 以下系列免费
    // ERNIE Speed系列
    // ERNIE Lite系列
    // ERNIE Tiny系列

    public static final ChatModel YI_34B_CHAT = new ChatModel("Yi-34B-Chat", "yi_34b_chat");
    public static final ChatModel ERNIE_4_0_8K = new ChatModel("ERNIE-4.0-8K", "completions_pro");
    public static final ChatModel ERNIE_SPEED_8K = new ChatModel("ERNIE-Speed-8K", "ernie_speed");
    public static final ChatModel ERNIE_SPEED_128K = new ChatModel("ERNIE-Speed-128K", "ernie-speed-128k");
    public static final ChatModel ERNIE_4_0_TURBO_8K = new ChatModel("ERNIE-4.0-Turbo-8K", "ernie-4.0-turbo-8k");

}
