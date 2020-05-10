package models.paymentmodes;

public class PostalMode implements PaymentMode {
    private final String address;

    public PostalMode(String address) {
        this.address = address;
    }

    @Override
    public void dispatchPayment(double amount) {
        // TODO: Logic to send check to the address
    }

    @Override
    public String toString() {
        return "Mailed the paycheck to the address " + address;
    }
}
