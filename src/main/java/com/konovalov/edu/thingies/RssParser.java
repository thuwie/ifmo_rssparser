package com.konovalov.edu.thingies;

import com.rometools.rome.feed.synd.SyndFeed;

import java.util.List;
import java.util.Map;

public interface RssParser {

    public boolean checkSource(String url);

    public void fetchRssFeed();

    public void transformRssFeed(SyndFeed feed);

    public void formalizeData(List<Map<String, Object>> parsedItems);

    public void writeFile(List<String> values);

}
