package com.example.employeemanagementapp.Translators;

import com.example.employeemanagementapp.Adapters.JsonObjectToMapAdapter;
import com.example.employeemanagementapp.Utils.FileManipulator;

import java.util.Map;

public class TranslatorImplements implements Translator {
    private final Map<String, String> dictionary;

    public TranslatorImplements(String fileName) {
        dictionary = JsonObjectToMapAdapter.convert(FileManipulator.readJsonObject(fileName));
    }

    @Override
    public String translate(String input) {
        return dictionary.getOrDefault(input, input);
    }
}
