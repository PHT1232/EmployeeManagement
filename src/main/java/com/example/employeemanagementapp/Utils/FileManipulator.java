package com.example.employeemanagementapp.Utils;
import com.example.employeemanagementapp.MainApplication;
import org.json.JSONObject;

import java.io.*;

public class FileManipulator {
    public static void writeString(String input, String fileName) {
        File file = new File(MainApplication.class.getResource(fileName).getFile());

        try (BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(file))) {
            bufferedWriter.write(input);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static JSONObject readJsonObjectFromFile(String fileName) {
        JSONObject obj;
        StringBuilder jsonString = new StringBuilder();
        String line;
        File file = new File(MainApplication.class.getResource(fileName).getFile());

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            while ((line = reader.readLine()) != null) {
               jsonString.append(line);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        obj = new JSONObject(jsonString.toString());

        return obj;
    }
}
