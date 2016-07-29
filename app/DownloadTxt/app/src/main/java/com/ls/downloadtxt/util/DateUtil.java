package com.ls.downloadtxt.util;

/**
 * Created by hx on 2016/4/7.
 */
public class DateUtil {
    public static final long MILLSECOND = 1;
    public static final long SECOND = 1000;
    public static final long MINUTE  = 60 * SECOND;
    public static final long HOUR  = 60 * MINUTE;
    public static final long DAY  = 24 * HOUR;

    public static long addDay(long source, int day) {
        return source + day * DAY;
    }

//    public static boolean isAfterToday(long time) {
//        return new DateTime().minusDays(1).withHourOfDay(0).withMinuteOfHour(0).withMillisOfSecond(0).getMillis() <= time;
//    }


}
