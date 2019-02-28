package com.konovalov.edu.thingies;

public interface RssParser {

    public boolean checkSource(String url);

    public void fetchRssFeed(String url);

    public void transformRssFeed();

    public void shapeRssFeed();

}
