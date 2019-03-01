package com.konovalov.edu.thingies;

import com.konovalov.edu.model.RssFeed;

public interface RssParser {

    public boolean checkSource(String url);

    public void fetchRssFeed(RssFeed rssFeed);

    public void transformRssFeed();

    public void shapeRssFeed();

}
