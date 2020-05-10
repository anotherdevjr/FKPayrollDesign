package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

public class TimeUtils {
    public static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;
    public static long getTodayDateInMillis() {
        long time = Calendar.getInstance().getTimeInMillis();
        return (time / MILLIS_IN_DAY) * MILLIS_IN_DAY ;
    }

    public static long getLastFridayFrom(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int shiftNeeded = 0;
        if (dayOfWeek >= Calendar.FRIDAY) {
            shiftNeeded = (dayOfWeek - Calendar.FRIDAY);
        } else {
            shiftNeeded = 7 + (dayOfWeek - Calendar.FRIDAY);
        }
        return timeInMillis - shiftNeeded * MILLIS_IN_DAY;
    }

    public static long getLastEvenFridayFrom(long timeInMillis) {
        long lastFriday = getLastFridayFrom(timeInMillis);
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastFriday);
        int fridayCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);

        if (fridayCount == 2 || fridayCount == 4) {
            return lastFriday;
        } else {
            return lastFriday - 7 * MILLIS_IN_DAY;
        }
    }

    public static long getLastEndOfMonthFrom(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int lastOfThisMonth = calendar.getActualMaximum(Calendar.DATE);
        int currentDate = calendar.get(Calendar.DATE);
        if (lastOfThisMonth == currentDate) {
            return timeInMillis;
        } else {
            return timeInMillis - currentDate * MILLIS_IN_DAY;
        }
    }

    public static String getDateStringFromMillis(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(calendar.getTime());
    }
}
