package models.paymentmodes;

public class PickupMode implements PaymentMode {

    @Override
    public void dispatchPayment(double amount) {
        // TODO: Logic to set the pickup by paymaster
    }

    @Override
    public String toString() {
        return "Holding paycheck for pickup by paymaster";
    }
}
