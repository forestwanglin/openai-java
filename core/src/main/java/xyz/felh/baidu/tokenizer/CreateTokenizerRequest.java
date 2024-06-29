package xyz.felh.baidu.tokenizer;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.baidu.IBaiduApiRequest;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor(force = true)
public class CreateTokenizerRequest implements IBaiduApiRequest {

    /**
     * prompt内容，最大 512000 字符
     */
    @NonNull
    @JSONField(name = "prompt")
    @JsonProperty("prompt")
    private String prompt;

    /**
     * （1）如果填写此参数，模型可选值如下：
     * · ERNIE 4.0系列：ernie-4.0-8k
     * · ERNIE 3.5系列：ernie-3.5-8k
     * · ERNIE Speed系列： ernie-speed-8k、ernie-speed-128k
     * · ERNIE Lite系列：ernie-lite-8k
     * · 其他，可选值为 ernie-tiny-8k、ernie-char-8k
     * （2）如果值不在上述范围，model入参可以为空
     */
    @JSONField(name = "model")
    @JsonProperty("model")
    private String model;

}
