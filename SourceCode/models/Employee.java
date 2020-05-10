package models;

import database.DatabaseProvider;
import models.paymentmodes.PaymentMode;
import models.paymentmodes.PickupMode;

public abstract class Employee {
    private String id;
    private String name;
    private DatabaseProvider databaseProvider;
    private PaymentMode paymentMode;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
        paymentMode = new PickupMode();
    }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setDatabaseProvider(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public void dispatchPayment(double amount) {
        paymentMode.dispatchPayment(amount);
        System.out.println("\nEmployee Id: " + getId() + "\t Name: "+ getName());
        System.out.println("Dispatching a payment of Rs." + amount);
        System.out.println("Mode of payment: " + paymentMode.toString() + "\n");
    }

    public String getId() {
        return id;
    }

    public String getName() { return name; }

    public abstract double getPay(long endDate);

    public static String getStartingCode() {
        return "";
    }
}
