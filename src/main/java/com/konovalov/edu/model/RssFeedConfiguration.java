package com.konovalov.edu.model;

import lombok.Data;

import java.io.File;
import java.util.Date;

/**
 * The type Rss feed configuration.
 */
@Data
public class RssFeedConfiguration {
    private String name;
    private String URL;
    private Long updateTime;
    private Date lastUpdateDate;
    private String template;
    private Integer postsLimit;
    private Boolean status;
    private File file;

    /**
     * Instantiates a new Rss feed configuration.
     */
    public RssFeedConfiguration() {
        this.status = false;
    }

    /**
     * Instantiates a new Rss feed configuration.
     *
     * @param name           the rss name
     * @param URL            the rss url
     * @param updateTime     the gap between updates
     * @param lastUpdateDate the last update date
     * @param template       the template
     * @param file           the file
     */
    public RssFeedConfiguration(String name, String URL, Long updateTime, Date lastUpdateDate, String template, File file) {
        this.name = name;
        this.URL = URL;
        this.updateTime = updateTime;
        this.status = false;
        this.lastUpdateDate = lastUpdateDate;
        this.template = template;
        this.file = file;
    }

    /**
     * Gets tag.
     *
     * @param itemDate the item date
     * @return the tag
     */
    public String getTag(String itemDate) {
        return String.format("Feed [%s], [%s] \n", this.name, itemDate);
    }

    @Override
    public String toString() {
        return String.format("Name: [%s]\n " +
                "Url: [%s]\n" +
                "Update time: [%d]\n" +
                "Status: [%b]\n" +
                "Template: [%s]\n" +
                "Filename: [%s]", this.name, this.URL, this.updateTime, this.status, this.template, this.file);
    }

}

