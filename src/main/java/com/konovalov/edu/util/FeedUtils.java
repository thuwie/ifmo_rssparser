package com.konovalov.edu.util;

import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

/**
 * The type Feed utils.
 */
@Slf4j
public class FeedUtils {

    /**
     * Instantiates a new Feed utils.
     */
    public FeedUtils() {
    }

    /**
     * Convert url to file string.
     *
     * @param url the url
     * @return the string
     */
    public static String convertUrlToFilename(String url) {
        return url
                .replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "")
                .replaceAll("[ ./:]", "_");
    }

    /**
     * Gets or creates file.
     *
     * @param filename the file
     * @return the file
     */
    public static File getFile(String filename) {
        String path = System.getProperty("user.dir") + File.pathSeparator + filename;
        File file = new File(path);
        if (!Files.exists(Paths.get(path))) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                log.error(e.getMessage());
            }
        }
        return file;

    }

    /**
     * Extract date.
     *
     * @param stringDate the string date
     * @return the date
     */
    public static Date extractDate(String stringDate) {
        DateFormat df = new SimpleDateFormat(Defaults.dateFormat, Locale.ENGLISH);
        Date result = null;
        try {
            result = df.parse(stringDate);
        } catch (ParseException e1) {
            e1.printStackTrace();
        }
        return result;
    }
}
