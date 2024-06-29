package xyz.felh.baidu.chat;

import lombok.Getter;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

@Getter
public enum ModelType {

    // 价格参考 https://cloud.baidu.com/doc/WENXINWORKSHOP/s/hlrk4akp7?feedback=1
    // 以下系列免费
    // ERNIE Speed系列, ERNIE Lite系列, ERNIE Tiny系列
    ERNIE_4_0_TURBO_8K("ERNIE-4.0-Turbo-8K", "ernie-4.0-turbo-8k", 20000, 5120, 2048),
    ERNIE_4_0_8K("ERNIE-4.0-8K", "completions_pro", 20000, 5120, 2048),
    ERNIE_4_0_8K_LATEST("ERNIE-4.0-8K-Latest", "ernie-4.0-8k-latest", 20000, 5120, 2048),
    ERNIE_4_0_8K_PREVIEW("ERNIE-4.0-8K-Preview", "ernie-4.0-8k-preview", 20000, 5120, 2048),
    ERNIE_3_5_8K("ERNIE-3.5-8K", "completions", 20000, 5120, 2048),
    ERNIE_3_5_128K("ERNIE-3.5-128K", "ernie-3.5-128k", 516096, 126976, 4096),
    YI_34B_CHAT("Yi-34B-Chat", "yi_34b_chat", 8000, 0, 0),
    ERNIE_SPEED_8K("ERNIE-Speed-8K", "ernie_speed", 24000, 6144, 2048),
    ERNIE_SPEED_128K("ERNIE-Speed-128K", "ernie-speed-128k", 516096, 126976, 4096),
    ERNIE_LITE_8K("ERNIE-Lite-8K", "ernie-lite-8k", 24000, 6144, 2048),
    ERNIE_LITE_8K_0922("ERNIE-Lite-8K-0922", "eb-instant", 11200, 7168, 1024),
    ERNIE_TINY_8K("ERNIE-Tiny-8K", "ERNIE_LITE_8K", 24000, 6144, 2048),
    ;
    private static final Map<String, ModelType> nameToModelType = Arrays.stream(values())
            .collect(Collectors.toMap(ModelType::getName, Function.identity()));

    private final String name;
    private final String path;
    private final int maxContextLength;
    private final int maxInputToken;
    private final int maxOutputToken;

    ModelType(String name, String path, int maxContextLength, int maxInputToken, int maxOutputToken) {
        this.name = name;
        this.path = path;
        this.maxContextLength = maxContextLength;
        this.maxInputToken = maxInputToken;
        this.maxOutputToken = maxOutputToken;
    }

    public static Optional<ModelType> fromName(final String name) {
        return Optional.ofNullable(nameToModelType.get(name));
    }

}
