package com.code4copy.example.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public class TestUtils {
    private static ObjectMapper mapper_ = new ObjectMapper();

    public static <T> T convertJsonStringToObject(final String jsonStr,
                                                  final Class<T> classType) throws IOException {
        return mapper_.readValue(jsonStr, classType);
    }

    public static byte[] convertObjectToJsonStream(final Object object)
            throws JsonProcessingException {
        return mapper_.writeValueAsBytes(object);
    }

    public static String convertObjectToJsonString(final Object object)
            throws JsonProcessingException {
        return mapper_.writeValueAsString(object);
    }

    public static ObjectMapper getMapper() {
        return mapper_;
    }
}
