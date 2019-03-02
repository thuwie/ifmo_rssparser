package com.konovalov.edu.controller;

import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.parser.RssParser;
import com.konovalov.edu.parser.impl.RssParserImpl;
import com.rometools.rome.feed.synd.SyndFeed;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;

import java.io.IOException;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import static java.util.Objects.isNull;

/**
 * The type Rss feed controller.
 */
@Data
@Slf4j
public class RssFeedController implements Runnable {
    private RssFeedConfiguration feedConfiguration;
    private CloseableHttpClient httpClient;
    private RssParser parser;

    /**
     * Instantiates a new Rss feed controller.
     *
     * @param feedConfiguration the feed configuration
     */
    public RssFeedController(RssFeedConfiguration feedConfiguration) {
        this.feedConfiguration = feedConfiguration;
        this.httpClient = HttpClients.createDefault();
        this.parser = new RssParserImpl(feedConfiguration);

    }

    @Override
    public void run() {
        SyndFeed parsedFeed = parser.fetchRssFeed();
        List<Map<String, Object>> transformedFeed = parser.transformRssFeed(parsedFeed);
        List<String> preparedData = parser.formalizeData(transformedFeed);
        parser.writeFile(preparedData);
        this.feedConfiguration.setLastUpdateDate(Calendar.getInstance().getTime());
    }
}
