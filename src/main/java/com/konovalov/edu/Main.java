package com.konovalov.edu;

import com.konovalov.edu.controller.FeedController;
import com.konovalov.edu.model.RssFeed;
import com.konovalov.edu.system.Init;
import org.apache.log4j.PropertyConfigurator;
import com.konovalov.edu.system.UserMenu;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        PropertyConfigurator.configure(Main.class.getResourceAsStream("/log4j.properties"));

        ScheduledThreadPoolExecutor scheduledExecutorService = Init.initializeExecutor();
        ConcurrentHashMap<String, RssFeed> feedList = Init.initializeFeedList();
        FeedController feedController = Init.initFeedController(feedList, scheduledExecutorService);

        UserMenu cli = new UserMenu(feedController);
        cli.launchUserMenu();


//        org.apache.log4j.BasicConfigurator.configure();
//        String urlNew = "https://www.jpl.nasa.gov/multimedia/rss/news.xml";
//        com.konovalov.edu.model.RssFeed parser = new com.konovalov.edu.model.RssFeed(urlNew, 1);
//
//        parser.dumb();

    }
}
