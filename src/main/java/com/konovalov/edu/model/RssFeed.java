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
import java.util.Date;
import java.util.List;

@Data
public class RssFeed implements Runnable {
    private String name;
    private String URL;
    private Long updateTime;
    private Date lastUpdateDate;
    private String template;
    private Boolean status;
    private String filename;
    private RssParser parser;

    public RssFeed() {
        this.status = false;
    }

    public RssFeed(String name, String URL, Long updateTime, Date lastUpdateDate, String template, String filename) {
        this.name = name;
        this.URL = URL;
        this.updateTime = updateTime;
        this.status = false;
        this.lastUpdateDate = lastUpdateDate;
        this.template = template;
        this.filename = filename;
        this.parser = new RssParserImpl();
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

//        parser.fetchRssFeed();
    }

    @Override
    public String toString() {
        return String.format("Name: [%s]\n " +
                "Url: [%s]\n" +
                "Update time: [%d]\n" +
                "Status: [%b]\n" +
                "Template: [%s]\n" +
                "Filename: [%s]", this.name, this.URL, this.updateTime, this.status, this.template, this.filename);
    }

}

