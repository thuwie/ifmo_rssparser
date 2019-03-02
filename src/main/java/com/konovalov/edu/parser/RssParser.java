package com.konovalov.edu.parser;

import com.rometools.rome.feed.synd.SyndFeed;

import java.util.List;
import java.util.Map;

/**
 * The interface Rss parser.
 */
public interface RssParser {

    /**
     * Check source boolean.
     *
     * @param url the url
     * @return the boolean
     */
    public boolean checkSource(String url);

    /**
     * Fetch rss feed.
     */
    public SyndFeed fetchRssFeed();

    /**
     * Transform rss feed.
     *
     * @param feed the feed
     */
    public List<Map<String, Object>> transformRssFeed(SyndFeed feed);

    /**
     * Formalize data.
     *
     * @param parsedItems the parsed items
     */
    public List<String> formalizeData(List<Map<String, Object>> parsedItems);

    /**
     * Write file.
     *
     * @param values the values
     */
    public void writeFile(List<String> values);

}
