package utils;

import java.util.Calendar;

public class TimeUtils {
    public static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
    public static long getTodayDateInMillis() {
        long time = Calendar.getInstance().getTimeInMillis();
        return (time / MILLIS_IN_DAY) * MILLIS_IN_DAY;
    }


}
