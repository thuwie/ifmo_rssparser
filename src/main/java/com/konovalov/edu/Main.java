package com.konovalov.edu;

import com.konovalov.edu.controller.RssFeedController;
import com.konovalov.edu.system.FeedManager;
import com.konovalov.edu.system.Init;
import org.apache.log4j.PropertyConfigurator;
import com.konovalov.edu.system.UserMenu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        //https://lenta.ru/rss
        //http://blog.case.edu/news/feed.atom
        PropertyConfigurator.configure(Main.class.getResourceAsStream("/log4j.properties"));

        ScheduledThreadPoolExecutor scheduledExecutorService = Init.initializeExecutor();
        ConcurrentHashMap<String, RssFeedController> feedList = Init.initializeFeedList();
        FeedManager feedManager = Init.initFeedController(feedList, scheduledExecutorService);

        UserMenu cli = new UserMenu(feedManager);
        cli.launchUserMenu();
    }
}
