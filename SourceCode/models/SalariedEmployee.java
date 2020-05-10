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
        this.lastPaidMonth = Calendar.MARCH;
    }

    public void postSalesReceipt(SalesReceipt    salesReceipt) {
        getDatabaseProvider().postSalesReceipt(getId(), salesReceipt);
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
        return "E";
    }

    public double getSalesCommission(long endDate) {
        List<SalesReceipt> allSalesReceipt = getDatabaseProvider().getSalesReceiptListForEmployee(getId(), endDate);
        double commision = 0;
        for(SalesReceipt receipt : allSalesReceipt) {
            commision += receipt.getSalesAmount() * commissionRate;
        }
        return commision;
    }
}
