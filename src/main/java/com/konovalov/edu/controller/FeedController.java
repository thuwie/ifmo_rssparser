package com.konovalov.edu.controller;

import com.konovalov.edu.model.RssFeed;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

@Data
@Slf4j
public class FeedController {
    ConcurrentHashMap<String, RssFeed> feedsList;
    ConcurrentHashMap<String, ScheduledFuture<?>> tasksList;
    ScheduledThreadPoolExecutor executor;

    public FeedController(ConcurrentHashMap<String, RssFeed> feedsList, ScheduledThreadPoolExecutor scheduledExecutorService) {
        this.feedsList = feedsList;
        this.tasksList = new ConcurrentHashMap<>();
        this.executor = scheduledExecutorService;
        if (!feedsList.isEmpty()) {
            scheduleFeeds();
        }
    }

    public void addFeed(RssFeed feed) {
        ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, 0L, feed.getUpdateTime(), TimeUnit.SECONDS);
        feedsList.put(feed.getName(), feed);
        tasksList.put(feed.getName(), feedFuture);
        feed.setStatus(true);
        log.info("Feed {} successfully added.", feed.getName());
    }

    public void stopFeed(String name) {
        tasksList.get(name).cancel(false);
        tasksList.remove(name);
        feedsList.get(name).setStatus(false);
        log.info("Feed {} successfully stopped.", name);
    }

    public String viewFeed(String name) {
        return feedsList.get(name).toString();
    }

    private void scheduleFeeds() {
        this.feedsList.forEach((name, feed) -> {
            ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, feed.getUpdateTime(), feed.getUpdateTime(), TimeUnit.SECONDS);
            feed.setStatus(true);
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
