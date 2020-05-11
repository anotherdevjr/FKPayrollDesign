package models;

public class SalesReceipt {
    private final long dateInMillis;
    private final double salesAmount;

    /**
     * Taking only essential values
     * Others like Title, Id etc can also be saved.
     * @param dateInMillis
     * @param salesAmount
     */
    public SalesReceipt(long dateInMillis, double salesAmount) {
        this.dateInMillis = dateInMillis;
        this.salesAmount = salesAmount;
    }

    public double getSalesAmount() {
        return salesAmount;
    }

    public long getDateInMillis() {
        return dateInMillis;
    }
}
