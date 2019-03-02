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
    private Integer updateTime;
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
        return String.format("Name:         [%s]\n" +
                             "Url:          [%s]\n" +
                             "Update time:  [%d]\n" +
                             "Status:       [%b]\n" +
                             "Template:     [%s]\n" +
                             "Filename:     [%s]", this.name, this.URL, this.updateTime, this.status, this.template, this.file);
    }

}

