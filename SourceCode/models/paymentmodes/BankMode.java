package models.paymentmodes;

public class BankMode implements PaymentMode {
    private final String accountNumber;

    public BankMode(String accountNumber) {
        this.accountNumber = accountNumber;
    }

    @Override
    public void dispatchPayment(double amount) {
        // TODO: Logic to send check directly to bank
    }

    @Override
    public String toString() {
        return "The check has been deposited to bank account numbered " + accountNumber;
    }
}
