package com.konovalov.edu.util;

import java.util.Date;
import java.util.HashMap;

/**
 * The type Defaults.
 */
public class Defaults {
    /**
     * The constant lastUpdateDate.
     */
    public static final Date lastUpdateDate = new Date();
    /**
     * The constant template.
     */
    public static final String template = "%title%, %description%";
    /**
     * The constant dateFormat.
     */
    public static final String dateFormat = "EEE MMM dd HH:mm:ss z yyyy";
    /**
     * The constant postsLimit.
     */
    public static final Integer postsLimit = 5;

    /**
     * Synd template hash map.
     *
     * @return the hash map
     */
    public static HashMap<String, String> syndTemplate() {
        HashMap<String, String> commandMap = new HashMap<>();
        commandMap.put("title", "getTitle");
        commandMap.put("description", "getDescription");
        commandMap.put("author", "getAuthor");
        commandMap.put("comments", "getComments");
        commandMap.put("link", "getLink");
        commandMap.put("publishedDate", "getPublishedDate");
        return commandMap;
    }
}
