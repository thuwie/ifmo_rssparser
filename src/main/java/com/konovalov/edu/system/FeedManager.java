package com.konovalov.edu.system;

import com.konovalov.edu.controller.ConfigController;
import com.konovalov.edu.controller.RssFeedController;
import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.util.FeedUtils;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

import static java.util.Objects.isNull;

/**
 * The type Feed manager.
 */
@Data
@Slf4j
public class FeedManager {
    /**
     * The Feeds list.
     */
    ConcurrentHashMap<String, RssFeedController> feedsList;
    /**
     * The Tasks list.
     */
    ConcurrentHashMap<String, ScheduledFuture<?>> tasksList;
    /**
     * The Executor.
     */
    ScheduledThreadPoolExecutor executor;

    /**
     * Instantiates a new Feed manager.
     *
     * @param feedsList                the feeds list
     * @param scheduledExecutorService the scheduled executor service
     */
    public FeedManager(ConcurrentHashMap<String, RssFeedController> feedsList, ScheduledThreadPoolExecutor scheduledExecutorService) {
        this.feedsList = feedsList;
        this.tasksList = new ConcurrentHashMap<>();
        this.executor = scheduledExecutorService;
        if (!feedsList.isEmpty()) {
            scheduleFeeds();
        }
    }

    /**
     * Add feed.
     *
     * @param feedConfiguration the feed configuration
     */
    public void addFeed(RssFeedConfiguration feedConfiguration) {
        RssFeedController feed = new RssFeedController(feedConfiguration);
        ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, feed.getFeedConfiguration().getUpdateTime(), feed.getFeedConfiguration().getUpdateTime(), TimeUnit.MILLISECONDS);
        feedsList.put(feed.getFeedConfiguration().getName(), feed);
        tasksList.put(feed.getFeedConfiguration().getName(), feedFuture);
        feed.getFeedConfiguration().setStatus(true);
        log.info("Feed {} successfully added.", feed.getFeedConfiguration().getName());
    }

    /**
     * Stop feed.
     *
     * @param name the name
     */
    public void stopFeed(String name) {
        tasksList.get(name).cancel(false);
        tasksList.remove(name);
        feedsList.get(name).getFeedConfiguration().setStatus(false);
        log.info("Feed {} successfully stopped.", name);
    }

    /**
     * View feed string.
     *
     * @param name the name
     * @return the string
     */
    public String viewFeed(String name) {
        return feedsList.get(name).getFeedConfiguration().toString();
    }

    private void scheduleFeeds() {
        this.feedsList.forEach((name, feed) -> {
            ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, feed.getFeedConfiguration().getUpdateTime(), feed.getFeedConfiguration().getUpdateTime(), TimeUnit.MILLISECONDS);
            feed.getFeedConfiguration().setStatus(true);
            tasksList.put(name, feedFuture);
        });
    }


    /**
     * Edit properties.
     *
     * @param name     the name
     * @param property the property
     * @param value    the value
     */
    public void editProperties(String name, String property, String value) {
        RssFeedConfiguration tempConfiguration;
        switch (property) {
            case ("name"): {
                this.feedsList.put(value, this.feedsList.remove(name));
                this.tasksList.put(value, this.tasksList.remove(name));
                break;
            }
            case ("url"): {
                stopEditedFeed(name);
                tempConfiguration = this.feedsList.remove(name).getFeedConfiguration();
                this.tasksList.remove(name);
                tempConfiguration.setURL(value);
                addEditedFeed(tempConfiguration);
                break;
            }
            case ("updateTime"): {
                stopEditedFeed(name);
                tempConfiguration = this.feedsList.remove(name).getFeedConfiguration();
                this.tasksList.remove(name);
                tempConfiguration.setUpdateTime(Integer.parseInt(value));
                addEditedFeed(tempConfiguration);
                break;
            }
            case ("postsLimit"): {
                stopEditedFeed(name);
                tempConfiguration = this.feedsList.remove(name).getFeedConfiguration();
                this.tasksList.remove(name);
                tempConfiguration.setPostsLimit(Integer.parseInt(value));
                addEditedFeed(tempConfiguration);
                break;
            }
            case ("template"): {
                stopEditedFeed(name);
                tempConfiguration = this.feedsList.remove(name).getFeedConfiguration();
                this.tasksList.remove(name);
                tempConfiguration.setTemplate(value);
                addEditedFeed(tempConfiguration);
                break;
            }
            case ("filename"): {
                stopEditedFeed(name);
                tempConfiguration = this.feedsList.remove(name).getFeedConfiguration();
                this.tasksList.remove(name);
                tempConfiguration.setFile(FeedUtils.getFile(value));
                addEditedFeed(tempConfiguration);
                break;
            }
        }
    }

    /**
     * Shutdown executor.
     */
    public void shutdownExecutor() {
        this.executor.shutdown();
    }

    /**
     * Is feed exists boolean.
     *
     * @param name the name
     * @return the boolean
     */
    public boolean isFeedExists(String name) {
        return !isNull(this.feedsList.get(name));
    }

    private void stopEditedFeed(String name) {
        tasksList.get(name).cancel(false);
        tasksList.remove(name);
        feedsList.get(name).getFeedConfiguration().setStatus(false);
    }

    private void addEditedFeed(RssFeedConfiguration feedConfiguration) {
        RssFeedController feed = new RssFeedController(feedConfiguration);
        ScheduledFuture<?> feedFuture = executor.scheduleAtFixedRate(feed, feed.getFeedConfiguration().getUpdateTime(), feed.getFeedConfiguration().getUpdateTime(), TimeUnit.MILLISECONDS);
        feedsList.put(feed.getFeedConfiguration().getName(), feed);
        tasksList.put(feed.getFeedConfiguration().getName(), feedFuture);
        feed.getFeedConfiguration().setStatus(true);
    }
}
