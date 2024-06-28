package xyz.felh.utils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.TimeZone;

public class DateUtils {

    public static final String FORMAT_UTC_ISO8601 = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    public static String formatISO8601(Date date) {
        SimpleDateFormat sdf = new SimpleDateFormat();
        sdf.setTimeZone(TimeZone.getTimeZone("UTC"));
        sdf.applyPattern(FORMAT_UTC_ISO8601);
        return sdf.format(date);
    }

}
