package xyz.felh.openai.jtokkit.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ToolContentFormat {

    public static JSONObject tryFormat(String content) {
        try {
            return JSONObject.parseObject(content);
        } catch (Exception ignored) {
        }
        return null;
    }

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
            return formatArguments(content.toString());
        } catch (Exception ex) {
            // error
        }
        return content.toString();
    }

    public static String formatArguments(String arguments) {
        List<String> lines = new ArrayList<>();
        lines.add("{");
        JSONObject jsonObject = JSONObject.parseObject(arguments);
        List<String> properties = new ArrayList<>();
        for (String fieldName : jsonObject.keySet()) {
            properties.add(String.format("\"%s\":%s", fieldName, formatValue(jsonObject.get(fieldName))));
        }
        lines.add(String.join(",\n", properties));
        lines.add("}");
        return String.join("\n", lines);
    }

    private static String formatValue(Object value) {
        switch (value) {
            case String str -> {
                return String.format("\"%s\"", str);
            }
            case Number num -> {
                return String.format("%s", num);
            }
            case JSONObject jsonObject -> {
                return String.format("%s", formatArguments(jsonObject.toString()));
            }
            case JSONArray array -> {
                String result = "[";
                if (!array.isEmpty()) {
                    result += array.stream().map(ToolContentFormat::formatValue)
                            .collect(Collectors.joining(","));
                }
                result += "]";
                return result;
            }
            case null, default -> {
                return "\"\"";
            }
        }
    }

}
