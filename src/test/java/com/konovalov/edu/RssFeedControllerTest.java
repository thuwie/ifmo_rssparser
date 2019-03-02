package com.konovalov.edu;

import com.konovalov.edu.controller.RssFeedController;
import com.konovalov.edu.model.RssFeedConfiguration;
import com.konovalov.edu.util.FeedUtils;
import org.apache.commons.lang.StringUtils;
import org.junit.After;
import org.junit.Test;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Date;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class RssFeedControllerTest {

    @After
    public void setUp() {
        File file = FeedUtils.getFile("test.txt");
        assertTrue(file.delete());
    }

    @Test
    public void simpleTestSingle() throws IOException, InterruptedException {
        RssFeedConfiguration rssConfig = getDefaultConfig("first");
        runAndWait(rssConfig);

        File fileResult = rssConfig.getFile();
        String content = new String(Files.readAllBytes(fileResult.toPath()));
        assertTrue(fileResult.isFile());
        assertEquals(3, StringUtils.countMatches(content, "Feed [first],"));
    }


    @Test
    public void simpleTestMultiple() throws IOException, InterruptedException {
        RssFeedConfiguration rssConfig = getDefaultConfig("first");
        runAndWait(rssConfig);

        RssFeedConfiguration rssConfigSecond = getDefaultConfig("second");
        runAndWait(rssConfigSecond);

        File fileResult = rssConfig.getFile();
        String content = new String(Files.readAllBytes(fileResult.toPath()));
        assertTrue(fileResult.isFile());
        assertEquals(3, StringUtils.countMatches(content, "Feed [first],"));
        assertEquals(3, StringUtils.countMatches(content, "Feed [second],"));
    }

    private RssFeedConfiguration getDefaultConfig(String name) {
        RssFeedConfiguration rssConfig = new RssFeedConfiguration();
        rssConfig.setName(name);
        rssConfig.setURL("https://lenta.ru/rss");
        rssConfig.setUpdateTime(1500);
        rssConfig.setLastUpdateDate(new Date(1551470286));
        rssConfig.setPostsLimit(3);
        rssConfig.setStatus(true);
        File file = FeedUtils.getFile("test.txt");
        rssConfig.setFile(file);
        rssConfig.setTemplate("${title}\\n${description}\\n");

        return rssConfig;
    }

    private void runAndWait(RssFeedConfiguration rssConfig) throws InterruptedException {
        RssFeedController controller = new RssFeedController(rssConfig);
        controller.run();

        Thread.sleep(rssConfig.getUpdateTime() + 1000);
    }
}
