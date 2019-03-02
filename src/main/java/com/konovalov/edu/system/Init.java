package com.konovalov.edu.system;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.konovalov.edu.controller.ConfigController;
import com.konovalov.edu.controller.RssFeedController;
import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.util.Defaults;
import com.konovalov.edu.util.FeedUtils;


import java.util.Date;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Init {
    public Init() {
    }

    public static ConcurrentHashMap<String, RssFeedController> initializeFeedList() {
        ConcurrentHashMap<String, RssFeedController> feedList = new ConcurrentHashMap<>();

        JsonArray feedsConfig = ConfigController.loadFeeds();

        feedsConfig.forEach(e -> {
            JsonObject feedConfig = e.getAsJsonObject();
            RssFeedConfiguration newFeedConfiguration = new RssFeedConfiguration();
            if (feedConfig.has("name")) {
                newFeedConfiguration.setName(feedConfig.get("name").getAsString());
            } else {
                return;
            }

            if (feedConfig.has("url")) {
                newFeedConfiguration.setURL(feedConfig.get("url").getAsString());
            } else {
                return;
            }

            if (feedConfig.has("updateTime")) {
                newFeedConfiguration.setUpdateTime(feedConfig.get("updateTime").getAsLong());
            } else {
                newFeedConfiguration.setUpdateTime(600L);
            }

            if (feedConfig.has("lastUpdateTime")) {
                newFeedConfiguration.setLastUpdateDate(new Date(feedConfig.get("lastUpdateTime").getAsString())); // date constructor is deprecated, so i had to cheer him up and make him believe in himself
            } else {
                newFeedConfiguration.setLastUpdateDate(Defaults.lastUpdateDate);
            }

            if (feedConfig.has("template")) {
                newFeedConfiguration.setTemplate(feedConfig.get("template").getAsString());
            } else {
                newFeedConfiguration.setTemplate(Defaults.template);
            }

            if (feedConfig.has("filename")) {
                newFeedConfiguration.setFilename(feedConfig.get("filename").getAsString());
            } else {
                newFeedConfiguration.setFilename(FeedUtils.convertUrlToFilename(newFeedConfiguration.getURL()));
            }
            RssFeedController rssFeedController = new RssFeedController(newFeedConfiguration);
            feedList.put(newFeedConfiguration.getName(), rssFeedController);

        });

        return feedList;
    }

    public static ScheduledThreadPoolExecutor initializeExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    public static FeedManager initFeedController(ConcurrentHashMap<String, RssFeedController> feedList, ScheduledThreadPoolExecutor executor) {
        return new FeedManager(feedList, executor);
    }
}
