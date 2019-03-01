package com.konovalov.edu.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.io.*;

@Data
@Slf4j
public class ConfigController {
    public static final String configPath = "/config.json";

    public ConfigController() {
    }

    public static JsonArray loadFeeds() {
        JsonArray feedConfig = new JsonArray();
        try {
            String configContent = readFile(System.getProperty("user.dir") + configPath);
            JsonObject feeds = new JsonParser().parse(configContent).getAsJsonObject();
            feedConfig = feeds.getAsJsonArray("feeds");
        } catch (IOException e) {
            log.error(e.getMessage());
        }
        return feedConfig;
    }

    private static String readFile(String file) throws IOException {
        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            StringBuilder stringBuilder = new StringBuilder();
            String ls = System.getProperty("line.separator");
            while ((line = reader.readLine()) != null) {
                stringBuilder.append(line);
                stringBuilder.append(ls);
            }
            return stringBuilder.toString();
        }
    }


}
