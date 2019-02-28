package controller;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

@Data
@Slf4j
public class FeedsController {
    ConcurrentHashMap<String, RssFeed> feedsList;
    ConcurrentHashMap<String, ScheduledFuture<?>> tasksList;
    ScheduledThreadPoolExecutor executor;

    public FeedsController(ConcurrentHashMap<String, RssFeed> feedsList, ScheduledThreadPoolExecutor scheduledExecutorService) {
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
        log.info(String.format("Feed %s successfully added", feed.getName()));
    }

    public void stopFeed(String name) {
        tasksList.get(name).cancel(false);
        tasksList.remove(name);
        feedsList.get(name).setStatus(false);
        log.info(String.format("Feed %s successfully stopped.", name));
    }

    private void scheduleFeeds() {
        this.feedsList.forEach((key, value) -> {
            //TODO create task for each RssFeed
        });
    }


}
