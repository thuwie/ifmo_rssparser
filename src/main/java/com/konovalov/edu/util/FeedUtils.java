package com.konovalov.edu.util;

public class FeedUtils {

    public FeedUtils() {
    }

    public static String convertUrlToFilename(String url) {
        return url
                .replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "")
                .replaceAll("[ ./:]", "_");
    }
}
