package com.konovalov.edu.controller;

import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.parser.RssParser;
import com.konovalov.edu.parser.impl.RssParserImpl;
import lombok.Data;

/**
 * The type Rss feed controller.
 */
@Data
public class RssFeedController implements Runnable {
    private RssFeedConfiguration feedConfiguration;
    private RssParser parser;

    /**
     * Instantiates a new Rss feed controller.
     *
     * @param feedConfiguration the feed configuration
     */
    public RssFeedController(RssFeedConfiguration feedConfiguration) {
        this.feedConfiguration = feedConfiguration;
        this.parser = new RssParserImpl(feedConfiguration);
    }

    @Override
    public void run() {
        System.out.println("phaha");
        parser.fetchRssFeed();
    }
}
