package com.konovalov.edu.parser.impl;

import com.konovalov.edu.exceptions.RssParserException;
import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.util.Defaults;
import com.rometools.rome.feed.synd.SyndContent;
import com.rometools.rome.feed.synd.SyndEntry;
import com.rometools.rome.feed.synd.SyndFeed;
import com.rometools.rome.io.FeedException;
import com.rometools.rome.io.SyndFeedInput;
import com.rometools.rome.io.XmlReader;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.text.StrSubstitutor;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import com.konovalov.edu.parser.RssParser;

import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.StandardOpenOption;
import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.isNull;

/**
 * The type Rss parser.
 */
@Slf4j
public class RssParserImpl implements RssParser {
    private RssFeedConfiguration rssFeedConfiguration;

    /**
     * Instantiates a new Rss parser.
     *
     * @param rssFeedConfiguration the rss feed configuration
     */
    public RssParserImpl(RssFeedConfiguration rssFeedConfiguration) {
        this.rssFeedConfiguration = rssFeedConfiguration;
    }

    @Override
    public boolean checkSource(String url) {
        return true;
    }

    @Override
    public SyndFeed fetchRssFeed() {
        SyndFeed feed = null;
        try (CloseableHttpClient client = HttpClients.createDefault()) {
            HttpUriRequest request = new HttpGet(this.rssFeedConfiguration.getURL());
            try (CloseableHttpResponse response = client.execute(request);
                 InputStream stream = response.getEntity().getContent()
                ) {
                SyndFeedInput input = new SyndFeedInput();
                feed = input.build(new XmlReader(stream));
                transformRssFeed(feed);
            } catch (FeedException e) {
                log.error("Failed to parse feed {} - {}", this.rssFeedConfiguration.getName(), e.getMessage());
                throw new RssParserException("Cannot parse feed", e);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return feed;
    }

    @Override
    public List<Map<String, Object>> transformRssFeed(SyndFeed feed) {
        HashMap<String, String> syndParseMethods = Defaults.syndTemplate();

        List<Map<String, Object>> parsedItems = new ArrayList<>();
        List<SyndEntry> entries = feed.getEntries().stream()
                .filter(entry -> (!isNull(entry.getPublishedDate()) && entry.getPublishedDate().after(this.rssFeedConfiguration.getLastUpdateDate())))
                .limit(this.rssFeedConfiguration.getPostsLimit())
                .collect(Collectors.toList());

        entries.forEach(entry -> {
            Map<String, Object> valuesToReplace = new HashMap<>();
            syndParseMethods.forEach((key, command) -> {
                try {
                    Method transformMethod = entry.getClass().getMethod(command);
                    Object value = transformMethod.invoke(entry);
                    if ("description".equalsIgnoreCase(key)) {
                        value = (((SyndContent) value).getValue()).trim();
                    }
                    valuesToReplace.put(key, value);
                } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException exception) {
                    log.error("Corrupted method inv", exception);
                }
            });
            parsedItems.add(valuesToReplace);
        });
        return parsedItems;
    }

    @Override
    public List<String> formalizeData(List<Map<String, Object>> parsedItems) {
        List<String> formalizedItems = new ArrayList<>();
        parsedItems.forEach(item -> {
            StrSubstitutor sub = new StrSubstitutor(item);
            String tag = this.rssFeedConfiguration.getTag(item.get("publishedDate").toString());
            String result = tag + sub.replace(this.rssFeedConfiguration.getTemplate()); //
            formalizedItems.add(result);
        });
        return formalizedItems;
    }

    @Override
    public synchronized void writeFile(List<String> values) {
        try {
            Files.write(this.rssFeedConfiguration.getFile().toPath(), values, Charset.forName("UTF-8"), StandardOpenOption.APPEND);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

}
