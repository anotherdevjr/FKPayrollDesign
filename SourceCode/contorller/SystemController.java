package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import models.paymentmodes.PaymentMode;
import models.paymentmodes.PostalMode;
import utils.EmployeeIDGenerator;

import java.util.ArrayList;
import java.util.List;


public class SystemController {

    private final DatabaseProvider databaseProvider;
    private final PayrollController payrollController;

    public SystemController() {
        databaseProvider = new DatabaseProvider();
        payrollController = new PayrollController(databaseProvider);
    }

    public String addHourlyEmployee(String name, double hourlyRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("H", 5);
        databaseProvider.addEmployee(new HourlyEmployee(randomId, name, hourlyRate));

        return randomId;
    }

    public String addSalariedEmployee(String name, double salary, double commissionRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("E", 5);
        databaseProvider.addEmployee(new SalariedEmployee(randomId, name, salary, commissionRate));

        return randomId;
    }

    public void deleteEmployeeWithId(String employeeId) {
        databaseProvider.deleteEmployeeById(employeeId);
    }

    public List<Employee> getEmployeeList(String startIndex) {
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

    public void runPayRollTill(long timeInMillis) {
        payrollController.runPayroll(timeInMillis);
    }

    public void setPaymentMode(String employeeId, PaymentMode paymentMode) {
        Employee employee  = databaseProvider.queryEmployeeById(employeeId);
        employee.setPaymentMode(paymentMode);
    }
}
