package models;

import java.util.ArrayList;
import java.util.List;

public class HourlyEmployee extends Employee {
    private double hourlyRate;

    public HourlyEmployee(String id, String name, double hourlyRate) {
        super(id, name);
        this.hourlyRate = hourlyRate;
    }

    public void postTimeStamp(long dateInMillis, int hours) {
        getDatabaseProvider().postTimeStamp(getId(), new TimeStamp(dateInMillis, hours, hourlyRate));
    }

    @Override
    public double getPay(long endDate) {
        List<TimeStamp> timeStamps = getDatabaseProvider().getTimeStampsForEmployee(getId(), endDate);
        double pay = 0;
        for (TimeStamp timeStamp: timeStamps) {
            pay += timeStamp.getPayForDay();
        }
        return pay;
    }

    public static String getStartingCode() {
        return "H";
    }
}
