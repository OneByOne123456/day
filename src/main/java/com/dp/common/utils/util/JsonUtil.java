package com.dp.common.utils.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JavaType;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.CollectionType;
import com.fasterxml.jackson.databind.type.MapType;
import com.fasterxml.jackson.databind.type.TypeFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class JsonUtil {
    public static final ObjectMapper mapper = new ObjectMapper();
    private JsonUtil() {

    }

    public static String getJson(Object obj) throws JsonProcessingException {
        return mapper.writeValueAsString(obj);
    }

    //{"id":"01","name":"招商银行"}
    public static <T> T getObject(String json, Class<T> clazz) throws IOException {
        return mapper.readValue(json, clazz);
    }

    //{"result":1,"msg":null,"data":{"list":[{"id":"01","name":"招商银行"},{"id":"02","name":"中国工商银行"}]}}
    public static <T> List<T> getList(String json, Class<T> clazz) throws IOException {
        CollectionType collectionType = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
        return mapper.readValue(json, collectionType);
    }

    //{"list1":{"id":"01","name":"招商银行"},"list2":{"id":"02","name":"中国工商银行"}
    public static <T> Map<String, T> getMap(String json, Class<T> clazz) throws IOException {
        MapType mapType = mapper.getTypeFactory().constructMapType(HashMap.class, String.class, clazz);
        return mapper.readValue(json, mapType);
    }

    //{"list1":[{"id":"01","name":"招商银行"},{"id":"02","name":"中国工商银行"}],"list2":[{"id":"03","name":"交通银行"},{"id":"04","name":"建设银行"}]}
    public static <T> List<Map<String, T>> getMapList(String json, Class<T> clazz) throws IOException {
        TypeFactory typeFactory = mapper.getTypeFactory();
        JavaType stringType = typeFactory.constructType(String.class);
        CollectionType collectionType = typeFactory.constructCollectionType(ArrayList.class, clazz);
        MapType mapType = typeFactory.constructMapType(HashMap.class, stringType, collectionType);
        return mapper.readValue(json, mapType);
    }
}
