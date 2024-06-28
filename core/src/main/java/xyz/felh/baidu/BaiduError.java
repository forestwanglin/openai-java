package xyz.felh.baidu;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BaiduError implements IBaiduApiObject {

    @JSONField(name = "error_code")
    @JsonProperty("error_code")
    private String errorCode;

    @JSONField(name = "error_msg")
    @JsonProperty("error_msg")
    private String errorMsg;

}
