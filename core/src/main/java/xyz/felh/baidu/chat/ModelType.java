package xyz.felh.baidu.chat;

import lombok.Getter;

@Getter
public enum ModelType {

    ERNIE_4_0_TURBO_8K("ERNIE-4.0-Turbo-8K", "ernie-4.0-turbo-8k", 20000, 5120, 2048),
    ERNIE_4_0_8K("ERNIE-4.0-8K", "completions_pro", 20000, 5120, 2048),
    YI_34B_CHAT("Yi-34B-Chat", "yi_34b_chat", 8000, 0, 0),
    ERNIE_SPEED_8K("ERNIE-Speed-8K", "ernie_speed", 24000, 6144, 2048),
    ERNIE_SPEED_128K("ERNIE-Speed-128K", "ernie-speed-128k", 516096, 126976, 4096),
    ;

    private final String name;
    private final String path;
    private final int maxContextLength;
    private final int maxInputToken;
    private final int maxOutputToken;

    // 价格参考 https://cloud.baidu.com/doc/WENXINWORKSHOP/s/hlrk4akp7?feedback=1
    // 以下系列免费
    // ERNIE Speed系列
    // ERNIE Lite系列
    // ERNIE Tiny系列

    ModelType(String name, String path, int maxContextLength, int maxInputToken, int maxOutputToken) {
        this.name = name;
        this.path = path;
        this.maxContextLength = maxContextLength;
        this.maxInputToken = maxInputToken;
        this.maxOutputToken = maxOutputToken;
    }

}
