package xyz.felh.openai.jtokkit.utils;

import com.alibaba.fastjson2.JSONArray;
import com.alibaba.fastjson2.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ArgumentFormat {

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
        if (value instanceof String || value instanceof Number) {
            return String.format("\"%s\"", value);
        }
        if (value instanceof JSONArray array) {
            String result = "[";
            if (!array.isEmpty()) {
                result += array.stream().map(it -> {
                    if (it instanceof Number || it instanceof String) {
                        return String.format("\"%s\"", it);
                    } else {
                        return "\"\"";
                    }
                }).collect(Collectors.joining(","));
            }
            result += "]";
            return result;
        }
        return "\"\"";
    }

}
