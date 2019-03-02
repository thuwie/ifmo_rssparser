package com.konovalov.edu.util;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndEntryImpl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Locale;

public class Defaults {
    public static final Date lastUpdateDate = new Date();
    public static final String template = "%title%, %description%";
    public static final String dateFormat = "EEE MMM dd HH:mm:ss z yyyy";
    public static final Integer postsLimit = 5;

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
