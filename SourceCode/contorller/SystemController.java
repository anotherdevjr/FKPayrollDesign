package contorller;

import database.DatabaseProvider;
import models.HourlyEmployee;
import models.SalariedEmployee;
import utils.EmployeeIDGenerator;


public class SystemController {

    private DatabaseProvider databaseProvider;

    public SystemController() {
        databaseProvider = new DatabaseProvider();
    }

    public void addHourlyEmployee(String name, double hourlyRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("H", 5);
        databaseProvider.addEmployee(new HourlyEmployee(randomId, name, hourlyRate));
    }

    public void addSalariedEmployee(String name, double salary, double commissionRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("E", 5);
        databaseProvider.addEmployee(new SalariedEmployee(randomId, name, salary, commissionRate));
    }

}
