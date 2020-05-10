package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import utils.EmployeeIDGenerator;

import java.util.ArrayList;


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

    public void deleteEmployeeWithId(String employeeId) {
        databaseProvider.deleteEmployeeById(employeeId);
    }

    public ArrayList<Employee> getEmployeeList(String startIndex) {
        return databaseProvider.queryEmployeeList(startIndex);
    }

    public void postTimeStamp(String id, long dateInMillis, int hours) {
        Employee employee  = databaseProvider.queryEmployeeById(id);
        if (employee == null) {
            return;
        }
        if (employee instanceof HourlyEmployee) {
            ((HourlyEmployee) employee).postTimeStamp(dateInMillis, hours);
        }
    }
}
