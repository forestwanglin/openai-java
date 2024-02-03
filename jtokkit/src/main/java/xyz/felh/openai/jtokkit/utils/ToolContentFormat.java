package xyz.felh.openai.jtokkit.utils;

import com.alibaba.fastjson2.JSONObject;

public class ToolContentFormat {

    public static boolean isJSONString(String content) {
        try {
            JSONObject.parseObject(content);
            return true;
        } catch (Exception ignored) {
        }
        return false;
    }

    public static String format(Object content) {
        try {
            JSONObject.parseObject(content.toString());
            return ArgumentFormat.formatArguments(content.toString());
        } catch (Exception ex) {
            // error
        }
        return content.toString();
    }

}
