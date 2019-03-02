package com.konovalov.edu.thingies.impl;

import com.konovalov.edu.model.RssFeedConfiguration;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.konovalov.edu.thingies.RssParser;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

public class RssParserImpl implements RssParser {
    public RssParserImpl() {
    }

    @Override
    public boolean checkSource(String url) {
        return true;
    }

    @Override
    public void fetchRssFeed(RssFeedConfiguration rssFeedConfiguration) {
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(rssFeedConfiguration.getURL());
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                List<SyndEntry> entries = feed.getEntries().stream()
                        .filter(e -> ((!isNull(e.getPublishedDate())) || (e.getPublishedDate().compareTo(rssFeedConfiguration.getLastUpdateDate())) > 0))
                        .collect(Collectors.toList());

                entries.forEach(e -> System.out.println(e.getDescription().toString()));
            } catch (FeedException e) {
                e.printStackTrace();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void transformRssFeed() {

    }

    @Override
    public void shapeRssFeed() {

    }
}
