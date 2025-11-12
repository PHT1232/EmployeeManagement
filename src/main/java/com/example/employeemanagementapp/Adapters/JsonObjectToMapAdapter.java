package com.example.employeemanagementapp.Adapters;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class JsonObjectToMapAdapter {
    public static Map<String, String> convert(JSONObject object) {
        Map<String, String> map = new HashMap<>();
        for (Object key : object.keySet()) {
            String currentKey = key.toString();
            map.put(currentKey, object.getString(currentKey));
        }

        return map;
    }
}
