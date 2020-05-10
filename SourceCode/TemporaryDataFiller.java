import contorller.SystemController;
import utils.TimeUtils;

public class TemporaryDataFiller {
    public static void fillTemporaryData(SystemController systemController) {
        String id1 = systemController.addHourlyEmployee("Person A", 400);
        String id2 = systemController.addHourlyEmployee("Person B", 300);
        String id3 = systemController.addHourlyEmployee("Person C", 500);

        String id4 = systemController.addSalariedEmployee("Employee 1", 50000, .15);
        String id5 = systemController.addSalariedEmployee("Employee 2", 100000, .12);

        long today = TimeUtils.getTodayDateInMillis();
        systemController.postTimeStamp(id1, today, 7);
        systemController.postTimeStamp(id1, today - TimeUtils.MILLIS_IN_DAY, 9);
        systemController.postTimeStamp(id1, today - 8 * TimeUtils.MILLIS_IN_DAY, 6);

        systemController.postTimeStamp(id2, today, 8);
        systemController.postTimeStamp(id3, today - TimeUtils.MILLIS_IN_DAY, 10);
        systemController.postTimeStamp(id2, today - 8 * TimeUtils.MILLIS_IN_DAY, 8);
    }
}
