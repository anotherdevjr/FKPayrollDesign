package models;

public class TimeStamp {
    private final long dateInMillis;
    private final int noOfHours;
    private final double hourlyRate;
    public static double OVERTIME_RATE_MULTIPLIER = 1.5;

    /**
     * Getting required params of Time Card. Saving hourlyRate
     * because it might happen we update hourlyRate before
     * old payment is processed. So, saving it along with the
     * time card.
     * @param dateInMillis
     * @param noOfHours
     * @param hourlyRate
     */
    public TimeStamp(long dateInMillis, int noOfHours, double hourlyRate) {
        this.dateInMillis = dateInMillis;
        this.noOfHours = noOfHours;
        this.hourlyRate = hourlyRate;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }

    public int getNoOfHours() {
        return noOfHours;
    }

    public double getHourlyRate() {
        return hourlyRate;
    }

    public double getPayForDay() {
        if (noOfHours > 8) {
            return noOfHours * hourlyRate;
        } else {
            double base = 8 * hourlyRate;
            return base + (noOfHours - 8) * hourlyRate * OVERTIME_RATE_MULTIPLIER;
        }
    }
}
