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

import static java.util.Objects.isNull;

/**
 * The type Init.
 */
public class Init {
    /**
     * Instantiates a new Init.
     */
    public Init() {
    }

    /**
     * Initialize feed list concurrent hash map.
     *
     * @return the concurrent hash map
     */
    public static ConcurrentHashMap<String, RssFeedController> initializeFeedList() {
        ConcurrentHashMap<String, RssFeedController> feedList = new ConcurrentHashMap<>();

        JsonArray feedsConfig = ConfigController.loadFeeds();

        if (isNull(feedsConfig)) return feedList;

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
                newFeedConfiguration.setUpdateTime(feedConfig.get("updateTime").getAsInt());
            } else {
                newFeedConfiguration.setUpdateTime(600);
            }

            if (feedConfig.has("lastUpdateDate")) {
                Date lastUpdateTime = FeedUtils.extractDate(feedConfig.get("lastUpdateDate").getAsString());
                if (!isNull(lastUpdateTime)) {
                    newFeedConfiguration.setLastUpdateDate(lastUpdateTime);
                } else
                    newFeedConfiguration.setLastUpdateDate(Defaults.lastUpdateDate);
            } else {
                newFeedConfiguration.setLastUpdateDate(Defaults.lastUpdateDate);
            }

            if (feedConfig.has("postsLimit")) {
                newFeedConfiguration.setPostsLimit(feedConfig.get("postsLimit").getAsInt());
            } else {
                newFeedConfiguration.setPostsLimit(Defaults.postsLimit);
            }

            if (feedConfig.has("template")) {
                newFeedConfiguration.setTemplate(feedConfig.get("template").getAsString());
            } else {
                newFeedConfiguration.setTemplate(Defaults.template);
            }

            if (feedConfig.has("filename")) {
                newFeedConfiguration.setFile(FeedUtils.getFile(feedConfig.get("filename").getAsString()));
            } else {
                newFeedConfiguration.setFile(FeedUtils.getFile(FeedUtils.convertUrlToFilename(newFeedConfiguration.getURL())));
            }
            RssFeedController rssFeedController = new RssFeedController(newFeedConfiguration);
            feedList.put(newFeedConfiguration.getName(), rssFeedController);

        });

        return feedList;
    }

    /**
     * Initialize executor scheduled thread pool executor.
     *
     * @return the scheduled thread pool executor
     */
    public static ScheduledThreadPoolExecutor initializeExecutor() {
        ScheduledThreadPoolExecutor scheduledThreadPoolExecutor = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        scheduledThreadPoolExecutor.setRemoveOnCancelPolicy(true);
        return scheduledThreadPoolExecutor;
    }

    /**
     * Init feed controller feed manager.
     *
     * @param feedList the feed list
     * @param executor the executor
     * @return the feed manager
     */
    public static FeedManager initFeedController(ConcurrentHashMap<String, RssFeedController> feedList, ScheduledThreadPoolExecutor executor) {
        return new FeedManager(feedList, executor);
    }
}
