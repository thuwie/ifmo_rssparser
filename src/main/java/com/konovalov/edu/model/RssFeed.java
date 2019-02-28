package com.konovalov.edu.model;

import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.Data;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.konovalov.edu.thingies.RssParser;
import com.konovalov.edu.thingies.impl.RssParserImpl;

import java.io.InputStream;
import java.io.Serializable;
import java.util.List;

@Data
public class RssFeed implements Runnable, Serializable {
    private String URL;
    private String name;
    private long updateTime;
    private Boolean status;

    public RssFeed(String name, String URL, int updateTime) {
        this.name = name;
        this.URL = URL;
        this.updateTime = (long) updateTime;
        this.status = false;
    }

    public void dumb() {
        String urlNew = "https://xkcd.com/rss.xml";
        try (CloseableHttpClient client = HttpClients.createMinimal()) {
            HttpUriRequest request = new HttpGet(urlNew);
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()) {
                SyndFeedInput input = new SyndFeedInput();
                SyndFeed feed = input.build(new XmlReader(stream));
                List<SyndEntry> entries = feed.getEntries();
                SyndEntry entry = entries.get(0);

                System.out.println(entry.getTitle());
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        RssParser parser = new RssParserImpl();
        parser.fetchRssFeed("https://xkcd.com/rss.xml");
    }

}

