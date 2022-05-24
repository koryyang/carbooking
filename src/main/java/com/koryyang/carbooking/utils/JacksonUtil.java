package com.koryyang.carbooking.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.SneakyThrows;

import java.text.SimpleDateFormat;

/**
* @author yanglingyu
* @date 2022/5/25
*/
public class JacksonUtil {

    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    private static final String DATE_TIME_PATTERN = "yyyy-MM-dd HH:mm:ss";

    static {
        //非null字段才会序列化
        OBJECT_MAPPER.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        //忽略序列化时空bean转json的错误
        OBJECT_MAPPER.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        //忽略反序列化时在json字符串中存在，但是在java对象中不存在的字段转化错误
        OBJECT_MAPPER.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        //设置java.util.Date时间类的序列化和反序列化格式
        OBJECT_MAPPER.setDateFormat(new SimpleDateFormat(DATE_TIME_PATTERN));
        OBJECT_MAPPER.registerModule(new ParameterNamesModule())
                .registerModule(new Jdk8Module())
                .registerModule(new JavaTimeModule());
    }

    @SneakyThrows
    public static String toJson(Object o) {
        return OBJECT_MAPPER.writeValueAsString(o);
    }

    @SneakyThrows
    public static <T> T toBean(String json, Class<T> cls) {
        return OBJECT_MAPPER.readValue(json, cls);
    }

    @SneakyThrows
    public static <T> T toBean(String json, TypeReference<T> type) {
        return OBJECT_MAPPER.readValue(json, type);
    }

    public static ObjectNode createObjectNode() {
        return OBJECT_MAPPER.createObjectNode();
    }

    @SneakyThrows
    public static JsonNode toJsonNode(String json) {
        return OBJECT_MAPPER.readTree(json);
    }
}
