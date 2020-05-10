package models;

public class SalesReceipt {
    private long dateInMillis;
    private double salesAmount;

    public SalesReceipt(long dateInMillis, double salesAmount) {
        this.dateInMillis = dateInMillis;
        this.salesAmount = salesAmount;
    }

    public double getSalesAmount() {
        return salesAmount;
    }
}
