package com.konovalov.edu.model;

import lombok.Data;

import java.util.Date;

@Data
public class RssFeedConfiguration {
    private String name;
    private String URL;
    private Long updateTime;
    private Date lastUpdateDate;
    private String template;
    private Integer postsLimit;
    private Boolean status;
    private String filename;

    public RssFeedConfiguration() {
        this.status = false;
    }

    public RssFeedConfiguration(String name, String URL, Long updateTime, Date lastUpdateDate, String template, String filename) {
        this.name = name;
        this.URL = URL;
        this.updateTime = updateTime;
        this.status = false;
        this.lastUpdateDate = lastUpdateDate;
        this.template = template;
        this.filename = filename;
    }

    public String getTag(String itemDate) {
        return String.format("Feed [%s], [%s]", this.name, itemDate);
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

