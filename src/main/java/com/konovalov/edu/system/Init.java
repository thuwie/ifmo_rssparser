package com.konovalov.edu.system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.konovalov.edu.controller.ConfigController;
import com.konovalov.edu.controller.FeedController;
import com.konovalov.edu.model.RssFeed;
import com.konovalov.edu.util.Defaults;
import com.konovalov.edu.util.FeedUtils;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Init {
    public Init() {
    }

    public static ConcurrentHashMap<String, RssFeed> initializeFeedList() {
        ConcurrentHashMap<String, RssFeed> feedList = new ConcurrentHashMap<>();

        JsonArray feedsConfig = ConfigController.loadFeeds();

        feedsConfig.forEach(e -> {
            JsonObject feedConfig = e.getAsJsonObject();
            RssFeed newFeed = new RssFeed();
            if (feedConfig.has("name")) {
                newFeed.setName(feedConfig.get("name").getAsString());
            } else {
                return;
            }

            if (feedConfig.has("url")) {
                newFeed.setURL(feedConfig.get("url").getAsString());
            } else {
                return;
            }

            if (feedConfig.has("updateTime")) {
                newFeed.setUpdateTime(feedConfig.get("updateTime").getAsLong());
            } else {
                newFeed.setUpdateTime(600L);
            }

            if (feedConfig.has("lastUpdateTime")) {
                newFeed.setLastUpdateDate(new Date(feedConfig.get("lastUpdateTime").getAsString())); // date constructor is deprecated, so i had to cheer him up and make him believe in himself
            } else {
                newFeed.setLastUpdateDate(Defaults.lastUpdateDate);
            }

            if (feedConfig.has("template")) {
                newFeed.setTemplate(feedConfig.get("template").getAsString());
            } else {
                newFeed.setTemplate(Defaults.template);
            }

            if (feedConfig.has("filename")) {
                newFeed.setFilename(feedConfig.get("filename").getAsString());
            } else {
                newFeed.setFilename(FeedUtils.convertUrlToFilename(newFeed.getURL()));
            }

            feedList.put(newFeed.getName(), newFeed);

        });

        return feedList;
    }

    public static ScheduledThreadPoolExecutor initializeExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    public static FeedController initFeedController(ConcurrentHashMap<String, RssFeed> feedList, ScheduledThreadPoolExecutor executor) {
        return new FeedController(feedList, executor);
    }
}
