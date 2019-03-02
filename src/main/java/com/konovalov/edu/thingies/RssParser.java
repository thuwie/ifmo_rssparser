package com.konovalov.edu.thingies;

import com.konovalov.edu.model.RssFeedConfiguration;

public interface RssParser {

    public boolean checkSource(String url);

    public void fetchRssFeed(RssFeedConfiguration rssFeedConfiguration);

    public void transformRssFeed();

    public void shapeRssFeed();

}
