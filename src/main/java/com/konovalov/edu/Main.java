package com.konovalov.edu;

import org.apache.log4j.PropertyConfigurator;
import com.konovalov.edu.system.UserMenu;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledThreadPoolExecutor;

public class Main {
    public static void main(String[] args) {
        PropertyConfigurator.configure(Main.class.getResourceAsStream("/log4j.properties"));
        ScheduledThreadPoolExecutor scheduledExecutorService = (ScheduledThreadPoolExecutor) Executors.newScheduledThreadPool(4);
        scheduledExecutorService.setRemoveOnCancelPolicy(true);


        UserMenu cli = new UserMenu(scheduledExecutorService);
        cli.launchUserMenu();


//        org.apache.log4j.BasicConfigurator.configure();
//        String urlNew = "https://www.jpl.nasa.gov/multimedia/rss/news.xml";
//        com.konovalov.edu.model.RssFeed parser = new com.konovalov.edu.model.RssFeed(urlNew, 1);
//
//        parser.dumb();

    }
}
