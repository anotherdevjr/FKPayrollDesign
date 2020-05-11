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
        paymentMode = new PickupMode();     //Set payment mode to Pickup by default
    }

    /**
     * Returns the pay of employee till the end data passed
     * @param endDate The date to which to process in milliseconds
     * @return The pay which is owed to the Employee
     */
    public abstract double getPay(long endDate);

    /**
     * Returns the starting code which should be Unique to each Employee Type
     * @return The unique code for employees of particular Type.
     */
    public static String getStartingCode() {
        return "";
    }

    public void dispatchPayment(double amount) {
        paymentMode.dispatchPayment(amount);
        System.out.println("\nEmployee Id: " + getId() + "\t Name: "+ getName());
        System.out.println("Dispatching a payment of Rs." + amount);
        System.out.println("Mode of payment: " + paymentMode.toString() + "\n");
    }

    // Getters and Setters
    public String getId() {
        return id;
    }

    public String getName() { return name; }

    public void setPaymentMode(PaymentMode paymentMode) {
        this.paymentMode = paymentMode;
    }

    public void setDatabaseProvider(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }
}
