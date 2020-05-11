import contorller.SystemController;
import models.SalesReceipt;
import models.paymentmodes.BankMode;
import models.paymentmodes.PostalMode;
import utils.TimeUtils;

public class TemporaryDataFiller {
    public static void fillTemporaryData(SystemController systemController) {
        // Adding Initial Employees to the database
        String id1 = systemController.addHourlyEmployee("Hourly A", 400);
        String id2 = systemController.addHourlyEmployee("Hourly B", 300);
        String id3 = systemController.addHourlyEmployee("Hourly C", 500);

        String id4 = systemController.addSalariedEmployee("Salaried 1", 50000, .15);
        String id5 = systemController.addSalariedEmployee("Salaried 2", 100000, .12);

        long today = TimeUtils.getTodayDateInMillis();
        // Add Time Cards for employees.
        systemController.postTimeStamp(id1, today, 7);
        systemController.postTimeStamp(id1, today - TimeUtils.MILLIS_IN_DAY, 9);
        systemController.postTimeStamp(id1, today - 8 * TimeUtils.MILLIS_IN_DAY, 6);

        systemController.postTimeStamp(id2, today, 8);
        systemController.postTimeStamp(id3, today - TimeUtils.MILLIS_IN_DAY, 10);
        systemController.postTimeStamp(id2, today - 8 * TimeUtils.MILLIS_IN_DAY, 8);

        // Change payment modes for two employees to BankMode or Postal
        systemController.setPaymentMode(id1, new BankMode("132200110011"));
        systemController.setPaymentMode(id4, new PostalMode("221 B, Baker Street, London"));

        // Add a sales which was made 16 days ago.
        systemController.postSalesReceipt(id4, today - 16 * TimeUtils.MILLIS_IN_DAY, 100000);
    }
}
