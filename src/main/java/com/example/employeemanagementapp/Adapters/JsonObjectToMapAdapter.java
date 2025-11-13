package com.example.employeemanagementapp.Adapters;

import org.json.JSONObject;

import java.nio.ByteBuffer;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class JsonObjectToMapAdapter {
    private static String convertToUtf8(String original) {
        byte[] bytes = original.getBytes(StandardCharsets.UTF_8);

        return new String(bytes, StandardCharsets.UTF_8);
    }

    public static Map<String, String> convert(JSONObject object) {
        Map<String, String> map = new HashMap<>();
        for (Object key : object.keySet()) {
            String currentKey = convertToUtf8(key.toString());
            map.put(currentKey, object.getString(currentKey));
        }

        return map;
    }
}
