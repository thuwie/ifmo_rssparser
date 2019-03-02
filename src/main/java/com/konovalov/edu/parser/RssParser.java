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
    public void fetchRssFeed();

    /**
     * Transform rss feed.
     *
     * @param feed the feed
     */
    public void transformRssFeed(SyndFeed feed);

    /**
     * Formalize data.
     *
     * @param parsedItems the parsed items
     */
    public void formalizeData(List<Map<String, Object>> parsedItems);

    /**
     * Write file.
     *
     * @param values the values
     */
    public void writeFile(List<String> values);

}
