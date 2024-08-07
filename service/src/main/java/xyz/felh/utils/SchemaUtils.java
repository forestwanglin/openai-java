package xyz.felh.utils;

import com.alibaba.fastjson2.JSONObject;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyDescription;
import com.fasterxml.jackson.databind.JsonNode;
import com.github.victools.jsonschema.generator.*;
import com.github.victools.jsonschema.module.jackson.JacksonModule;
import com.github.victools.jsonschema.module.jackson.JacksonOption;
import lombok.Data;

/**
 * @author Forest Wang
 * @package xyz.felh.utils
 * @class SchemaUtils
 * @email forestwanglin@gmail.cn
 * @date 2024/8/7
 */
public class SchemaUtils {

    public static JSONObject convert2Schema(Class<?> clazz) {
        SchemaGeneratorConfigBuilder configBuilder = new SchemaGeneratorConfigBuilder(SchemaVersion.DRAFT_2020_12,
                OptionPreset.PLAIN_JSON)
                .without(Option.SCHEMA_VERSION_INDICATOR)
                .with(Option.FORBIDDEN_ADDITIONAL_PROPERTIES_BY_DEFAULT)
                .with(new JacksonModule(JacksonOption.RESPECT_JSONPROPERTY_REQUIRED));
        SchemaGeneratorConfig config = configBuilder.build();
        SchemaGenerator generator = new SchemaGenerator(config);
        JsonNode jsonSchema = generator.generateSchema(clazz);
        return JSONObject.parseObject(jsonSchema.toString());
    }

    public static void main(String[] args) {
        System.out.println(convert2Schema(GetWeatherParam.class));
    }

    @Data
    public static class GetWeatherParam {
        @JsonPropertyDescription("The city and state, e.g. San Francisco, CA")
        @JsonProperty(value = "location", required = true)
        private String location;
        @JsonProperty("unit")
        private Unit unit;
        @JsonProperty("age")
        private int age;
    }

    public enum Unit {
        celsius, fahrenheit
    }

}
