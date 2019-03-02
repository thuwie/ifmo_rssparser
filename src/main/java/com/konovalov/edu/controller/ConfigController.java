package com.konovalov.edu.controller;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import lombok.extern.slf4j.Slf4j;
import lombok.Data;

import java.io.*;

import static java.util.Objects.isNull;

/**
 * The type Config controller.
 */
@Data
@Slf4j
public class ConfigController {
    /**
     * The constant configPath.
     */
    public static final String configPath = "/config.json";

    /**
     * Instantiates a new Config controller.
     */
    public ConfigController() {
    }

    /**
     * Load feeds json settings array.
     *
     * @return the json settings array
     */
    public static JsonArray loadFeeds() {
        JsonArray feedConfig = new JsonArray();
        JsonObject feeds = new JsonObject();
        try {
            String configContent = readFile(System.getProperty("user.dir") + configPath);
            if (!configContent.isEmpty()) {
                feeds = new JsonParser().parse(configContent).getAsJsonObject();
            }
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
