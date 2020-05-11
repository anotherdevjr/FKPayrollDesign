package utils;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;

/**
 * The TimeUtils contains a list of static methods to handle all the
 * milliseconds transaction. The system saves all the dates in Milliseconds
 * as it is easy to compare dates as numbers and also save in database.
 */
public class TimeUtils {
    // One Day = 24 hours, each 60 mins, each 60 seconds, every second has 1000 milliseconds
    public static final long MILLIS_IN_DAY = 24 * 60 * 60 * 1000;

    /**
     * Find Today's date in milliseconds
     * @return The milliseconds of midnight for current date
     */
    public static long getTodayDateInMillis() {
        long time = Calendar.getInstance().getTimeInMillis();
        // Dividing by MILLIS_IN_DAY and multiplying removes any seconds which
        // were due to time passed since midnight today.
        return (time / MILLIS_IN_DAY) * MILLIS_IN_DAY ;
    }

    /**
     * Find the last Friday from given date
     * @param timeInMillis The date from which last friday is to be computed
     * @return The date of last Friday in milliseconds
     */
    public static long getLastFridayFrom(long timeInMillis) {
        // Find time of midnight for given time
        timeInMillis = (timeInMillis / MILLIS_IN_DAY) * MILLIS_IN_DAY ;

        // Getting calender instance for the time
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        // Getting day of week
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        int shiftNeeded = 0;
        if (dayOfWeek >= Calendar.FRIDAY) {
            // Shift can be found in case it is greater than Friday.
            shiftNeeded = (dayOfWeek - Calendar.FRIDAY);
        } else {
            // If it is less than Friday then we need to go to past week's Friday
            // So adding 7 to it.
            shiftNeeded = 7 + (dayOfWeek - Calendar.FRIDAY);
        }
        return timeInMillis - shiftNeeded * MILLIS_IN_DAY;
    }

    /**
     * Return last even occurrence of Friday.
     * @param timeInMillis
     * @return Date of last Friday in milliseconds
     */
    public static long getLastEvenFridayFrom(long timeInMillis) {
        // Find time of midnight for given time
        timeInMillis = (timeInMillis / MILLIS_IN_DAY) * MILLIS_IN_DAY ;

        // Getting last Friday in milliseconds
        long lastFriday = getLastFridayFrom(timeInMillis);

        // Finding what occurrence of Friday is it in the month
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(lastFriday);
        int fridayCount = calendar.get(Calendar.DAY_OF_WEEK_IN_MONTH);


        if (fridayCount == 2 || fridayCount == 4) {
            // If it is even occurrence then return as it is.
            return lastFriday;
        } else {
            // Else return the last week's Friday instance.
            return lastFriday - 7 * MILLIS_IN_DAY;
        }
    }

    /**
     * Returns the last month's end date if the passed date is not end of month
     * @param timeInMillis
     * @return
     */
    public static long getLastEndOfMonthFrom(long timeInMillis) {
        // Find time of midnight for given time
        timeInMillis = (timeInMillis / MILLIS_IN_DAY) * MILLIS_IN_DAY ;

        // Getting last date of the current month
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);

        int lastOfThisMonth = calendar.getActualMaximum(Calendar.DATE);
        int currentDate = calendar.get(Calendar.DATE);

        // If today is the last date of the month return the date as it is.
        if (lastOfThisMonth == currentDate) {
            return timeInMillis;
        } else {
            // Else subtract the number of days passed in current month to get last month's end date.
            return timeInMillis - currentDate * MILLIS_IN_DAY;
        }
    }

    /**
     * @param timeInMillis The time for which we need to find month
     * @return The month corresponding to the time.
     */
    public static int getMonthFromMillis(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        return calendar.get(Calendar.MONTH);
    }

    /**
     *
     * @param timeInMillis The time which we need to represent in dd/MM/yy
     * @return The formatted String which is representation of passed time in milliseconds.
     */
    public static String getDateStringFromMillis(long timeInMillis) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeInMillis(timeInMillis);
        DateFormat df = new SimpleDateFormat("dd/MM/yy");
        return df.format(calendar.getTime());
    }
}
