package xyz.felh.openai.jtokkit.utils;

import com.alibaba.fastjson2.JSONObject;
import lombok.extern.slf4j.Slf4j;
import xyz.felh.openai.chat.tool.Function;
import xyz.felh.openai.chat.tool.Tool;
import xyz.felh.openai.utils.Preconditions;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
public class FunctionFormat {

    public static String formatFunctionDefinitions(List<Tool> tools) {
        List<String> lines = new ArrayList<>();
        lines.add("namespace functions {");
        lines.add("");
        for (Tool tool : tools) {
            Function function = tool.getFunction();
            if (Preconditions.isNotBlank(function.getDescription())) {
                lines.add(String.format("// %s", function.getDescription()));
            }
            JSONObject p = JSONObject.parseObject(JSONObject.toJSONString(function.getParameters()));
            JSONObject properties = p.getJSONObject("properties");
            if (Preconditions.isNotBlank(properties) && Preconditions.isNotBlank(properties.keySet())) {
                lines.add(String.format("type %s = (_: {", function.getName()));
                lines.add(formatObjectProperties(p, 0));
                lines.add("}) => any;");
            } else {
                lines.add(String.format("type %s = () => any;", function.getName()));
            }
            lines.add("");
        }
        lines.add("} // namespace functions");
        log.info("\n" + String.join("\n", lines));
        return String.join("\n", lines);
    }

    private static String formatObjectProperties(JSONObject p, int indent) {
        JSONObject properties = p.getJSONObject("properties");
        if (Preconditions.isBlank(properties)) {
            return "";
        }
        List<String> requiredParams = p.getList("required", String.class);
        List<String> lines = new ArrayList<>();
        for (String key : properties.keySet()) {
            JSONObject props = properties.getJSONObject(key);
            String description = props.getString("description");
            if (Preconditions.isNotBlank(description)) {
                lines.add(String.format("// %s", description));
            }
            String question = "?";
            if (Preconditions.isNotBlank(requiredParams) && requiredParams.contains(key)) {
                question = "";
            }
            lines.add(String.format("%s%s: %s,", key, question, formatType(props, indent)));
        }

        return lines.stream().map(it -> " ".repeat(Math.max(0, indent)) + it).collect(Collectors.joining("\n"));
    }

    private static String formatType(JSONObject props, int indent) {
        String type = props.getString("type");
        return switch (type) {
            case "string" -> {
                if (props.containsKey("enum")) {
                    yield props.getList("enum", String.class).stream().map(it ->
                            String.format("\"%s\"", it)).collect(Collectors.joining(" | "));
                }
                yield "string";
            }
            case "array" -> {
                if (props.containsKey("items")) {
                    yield String.format("%s[]", formatType(props.getJSONObject("items"), indent));
                }
                yield "any[]";
            }
            case "object" -> String.format("{\n%s\n}", formatObjectProperties(props, indent + 2));
            case "integer", "number" -> {
                if (props.containsKey("enum")) {
                    yield String.join(" | ", props.getList("enum", String.class));
                }
                yield "number";
            }
            case "boolean" -> "boolean";
            case "null" -> "null";
            default -> "";
        };
    }

}
