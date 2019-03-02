package com.konovalov.edu.util;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FeedUtils {

    public FeedUtils() {
    }

    public static String convertUrlToFilename(String url) {
        return url
                .replaceFirst("^(http[s]?://www\\.|http[s]?://|www\\.)", "")
                .replaceAll("[ ./:]", "_");
    }

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
