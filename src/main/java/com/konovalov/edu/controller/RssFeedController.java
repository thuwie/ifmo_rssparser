package com.konovalov.edu.controller;

import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.thingies.RssParser;
import com.konovalov.edu.thingies.impl.RssParserImpl;
import lombok.Data;

@Data
public class RssFeedController implements Runnable {
    private RssFeedConfiguration feedConfiguration;
    private RssParser parser;

    public RssFeedController(RssFeedConfiguration feedConfiguration) {
        this.feedConfiguration = feedConfiguration;
        this.parser = new RssParserImpl();
    }

    @Override
    public void run() {
        System.out.println("phaha");
        parser.fetchRssFeed(feedConfiguration);
    }
}
