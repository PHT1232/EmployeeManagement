package com.example.employeemanagementapp;

import com.example.employeemanagementapp.Adapters.JsonObjectToMapAdapter;
import com.example.employeemanagementapp.Translators.Translator;
import com.example.employeemanagementapp.Translators.TranslatorImplements;
import com.example.employeemanagementapp.Utils.FileManipulator;
import org.json.JSONObject;

import java.util.Map;

public class ApplicationLanguageSetter {

    public static String getCurrentLanguage() {
        Map<String, String> currentLanguageMap = JsonObjectToMapAdapter.convert(FileManipulator.readJsonObject("current_language.json"));

        return currentLanguageMap.get("language");
    }

    public static Translator getTranslator() {
        if (getCurrentLanguage().equals("VN")) {
            return new TranslatorImplements("EnToVn.json");
        } else if (getCurrentLanguage().equals("JP")) {
            return new TranslatorImplements("EnToJp.json");
        }

        return null;
    }

    public static void setCurrentLanguage(String currentLanguage) {
        String jsonString = new JSONObject()
                .put("language", currentLanguage).toString();
        FileManipulator.writeString(jsonString, "current_language.json");
    }

}
