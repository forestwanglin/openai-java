package xyz.felh.baidu.chat;

import com.alibaba.fastjson2.annotation.JSONField;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import xyz.felh.baidu.IBaiduBean;

@Data
@Builder
public class SearchInfo implements IBaiduBean {

    /**
     * 序号
     */
    @JSONField(name = "index")
    @JsonProperty("index")
    private Integer index;

    /**
     * 搜索结果URL
     */
    @JSONField(name = "url")
    @JsonProperty("url")
    private String url;

    /**
     * 搜索结果标题
     */
    @JSONField(name = "title")
    @JsonProperty("title")
    private String title;

}
