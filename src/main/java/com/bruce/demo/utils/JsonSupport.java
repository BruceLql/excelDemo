package com.bruce.demo.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * @author: lql
 * @version: 2020/10/28 12:27 上午
 */
public interface JsonSupport {

    TypeReference<Map<String, Object>> MAP_STRING_OBJECT = new TypeReference<Map<String, Object>>() {
    };

    TypeReference<Map<String, String>> MAP_OF_STRING = new TypeReference<Map<String, String>>() {
    };

    TypeReference<List<String>> LIST_OF_STRING = new TypeReference<List<String>>() {
    };

    TypeReference<List<Integer>> LIST_OF_INTEGER = new TypeReference<List<Integer>>() {
    };

    TypeReference<List<Long>> LIST_OF_LONG = new TypeReference<List<Long>>() {
    };

    TypeReference<Map<String, Long>> MAP_OF_LONG = new TypeReference<Map<String, Long>>() {
    };

    TypeReference<Map<Long, Integer>> MAP_OF_LONG_INTEGER = new TypeReference<Map<Long, Integer>>() {
    };

    /**
     * json转对象
     *
     * @param json          序列化后的json
     * @param typeReference 类型引用
     * @param supplier      默认值获取方法
     * @param error         错误语句
     * @param <T>           返回类型的泛型
     * @return 对象
     */
    default <T> T json2object(String json, TypeReference<T> typeReference, Supplier<T> supplier, String error) {
        if (json == null) {
            return null;
        }

        if (StringUtils.isEmpty(json)) {
            return supplier.get();
        } else {
            try {
                return JSON.objectMapper.readValue(json, typeReference);
            } catch (IOException e) {
                throw new IllegalArgumentException(error);
            }
        }
    }

    /**
     * 对象转json
     *
     * @param object 对象
     * @param error  错误语句
     * @param <T>    对象的类型
     * @return 序列化语句
     */
    default <T> String object2json(T object, String error) {
        if (object == null) {
            return null;
        }

        try {
            return JSON.objectMapper.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new IllegalArgumentException(error);
        }
    }

    /**
     * 从json读取值对象
     *
     * @param json  json
     * @param clazz 对象class
     * @param <T>   对象类型
     * @return 反序列化对象
     */
    default <T> T readValue(String json, Class<T> clazz) {
        if (StringUtils.isEmpty(json)) {
            return null;
        }

        try {
            return JSON.objectMapper.readValue(json, clazz);
        } catch (IOException e) {
            throw new IllegalArgumentException("json.deserialization.fail");
        }
    }

    class JSON {
        public static final ObjectMapper objectMapper = new ObjectMapper()
                .setSerializationInclusion(JsonInclude.Include.NON_EMPTY)
                .setSerializationInclusion(JsonInclude.Include.NON_NULL);
    }
}

