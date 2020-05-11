package models;

import utils.TimeUtils;

import java.util.Calendar;
import java.util.List;

public class SalariedEmployee extends Employee{

    private double salary;
    private double commissionRate;
    private int lastPaidMonth;

    public SalariedEmployee(String id, String name, double salary, double commissionRate) {
        super(id, name);
        this.salary = salary;
        this.commissionRate = commissionRate;
        this.lastPaidMonth = Calendar.MARCH;    //TODO: For testing lets assume all employees were paid in the month of
                                                // March last time. To change later.
    }

    /**
     * Write the sales Receipt to the database
     * @param salesReceipt
     */
    public void postSalesReceipt(SalesReceipt salesReceipt) {
        getDatabaseProvider().postSalesReceipt(getId(), salesReceipt);
    }

    /**
     * Fetches list of sales before the end date and computes total commission on it.
     * @param endDate The date which limits the sales which needs to be dispatched
     * @return Commission for employee till given date
     */
    public double getSalesCommission(long endDate) {
        List<SalesReceipt> allSalesReceipt = getDatabaseProvider().getSalesReceiptListForEmployee(getId(), endDate);
        double commision = 0;
        for(SalesReceipt receipt : allSalesReceipt) {
            commision += receipt.getSalesAmount() * commissionRate;
        }
        return commision;
    }

    @Override
    public double getPay(long endDate) {
        int endMonth = TimeUtils.getMonthFromMillis(endDate);
        // TODO: This is not how it should be handled
        //      we should use a database for this. Although this
        //      is hack to show working of payroll.
        if (endMonth >= lastPaidMonth) {
            int monthsToPay = endMonth - lastPaidMonth;
            lastPaidMonth = endMonth;
            return salary * monthsToPay;
        }
        return salary;
    }

    public static String getStartingCode() {
        return "E";         // All salaried employees Id begin with E
    }
}
