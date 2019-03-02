package com.konovalov.edu.system;

import com.konovalov.edu.controller.RssFeedController;
import com.konovalov.edu.model.RssFeedConfiguration;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

@Data
@Slf4j
public class FeedManager {
    ConcurrentHashMap<String, RssFeedController> feedsList;
    ConcurrentHashMap<String, ScheduledFuture<?>> tasksList;
    ScheduledThreadPoolExecutor executor;

    public FeedManager(ConcurrentHashMap<String, RssFeedController> feedsList, ScheduledThreadPoolExecutor scheduledExecutorService) {
        this.feedsList = feedsList;
        this.tasksList = new ConcurrentHashMap<>();
        this.executor = scheduledExecutorService;
        if (!feedsList.isEmpty()) {
            scheduleFeeds();
        }
    }

    public void addFeed(RssFeedConfiguration feedConfiguration) {
        RssFeedController feed = new RssFeedController(feedConfiguration);
        ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, 0L, feed.getFeedConfiguration().getUpdateTime(), TimeUnit.SECONDS);
        feedsList.put(feed.getFeedConfiguration().getName(), feed);
        tasksList.put(feed.getFeedConfiguration().getName(), feedFuture);
        feed.getFeedConfiguration().setStatus(true);
        log.info("Feed {} successfully added.", feed.getFeedConfiguration().getName());
    }

    public void stopFeed(String name) {
        tasksList.get(name).cancel(false);
        tasksList.remove(name);
        feedsList.get(name).getFeedConfiguration().setStatus(false);
        log.info("Feed {} successfully stopped.", name);
    }

    public String viewFeed(String name) {
        return feedsList.get(name).toString();
    }

    private void scheduleFeeds() {
        this.feedsList.forEach((name, feed) -> {
            ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, feed.getFeedConfiguration().getUpdateTime(), feed.getFeedConfiguration().getUpdateTime(), TimeUnit.SECONDS);
            feed.getFeedConfiguration().setStatus(true);
            tasksList.put(name, feedFuture);
        });
    }


    public void editProperties(String name, String property, String value) {
        if ("name".equalsIgnoreCase(property)) {
            this.feedsList.put(value, this.feedsList.remove(name));
            this.tasksList.put(value, this.tasksList.remove(name));
        }
    }

    public void shutdownExecutor() {
        this.executor.shutdown();
    }

    public boolean isFeedExists(String name) {
        return !isNull(this.feedsList.get(name));
    }
}
