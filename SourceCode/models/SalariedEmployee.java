package models;

import java.util.ArrayList;
import java.util.List;

public class SalariedEmployee extends Employee{

    private double salary;
    private double commissionRate;

    public SalariedEmployee(String id, String name, double salary, double commissionRate) {
        super(id, name);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }

    public void postSalesReceipt(SalesReceipt    salesReceipt) {
        getDatabaseProvider().postSalesReceipt(getId(), salesReceipt);
    }

    @Override
    public double getPay(long endDate) {
        return salary;
    }

    public static String getStartingCode() {
        return "S";
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
