package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import models.SalesReceipt;
import models.paymentmodes.PaymentMode;
import models.paymentmodes.PostalMode;
import utils.EmployeeIDGenerator;

import java.util.ArrayList;
import java.util.List;


/**
 * The System Controller is the only controller you need to access.
 * This class hides all the logic within itself and only provides
 * methods to other classes so that they can interact with the system
 */
public class SystemController {

    private final DatabaseProvider databaseProvider;
    private final PayrollController payrollController;

    public SystemController() {
        databaseProvider = new DatabaseProvider();
        payrollController = new PayrollController(databaseProvider);
    }

    /**
     * Generate random id and add Hourly employee
     * @param name
     * @param hourlyRate
     * @return The ID of the employee
     */
    public String addHourlyEmployee(String name, double hourlyRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("H", 5);
        databaseProvider.addEmployee(new HourlyEmployee(randomId, name, hourlyRate));

        return randomId;
    }

    /**
     * Generate random id and add Salaried Employee to database
     * @param name
     * @param salary
     * @param commissionRate
     * @return
     */
    public String addSalariedEmployee(String name, double salary, double commissionRate) {
        String randomId = EmployeeIDGenerator.getEmployeeId("E", 5);
        databaseProvider.addEmployee(new SalariedEmployee(randomId, name, salary, commissionRate));

        return randomId;
    }

    /**
     * Delete the employee with given employee ID
     * @param employeeId
     */
    public void deleteEmployeeWithId(String employeeId) {
        databaseProvider.deleteEmployeeById(employeeId);
    }

    /**
     * Return list of employees whose ID begin with passes startCode
     * @param startCode
     * @return
     */
    public List<Employee> getEmployeeList(String startCode) {
        return databaseProvider.queryEmployeeList(startCode);
    }

    /**
     * Adds a time card to the employee
     * @param id
     * @param dateInMillis
     * @param hours
     */
    public void postTimeStamp(String id, long dateInMillis, int hours) {
        Employee employee  = databaseProvider.queryEmployeeById(id);
        if (employee == null) {
            return;
        }
        if (employee instanceof HourlyEmployee) {
            ((HourlyEmployee) employee).postTimeStamp(dateInMillis, hours);
        }
    }

    /**
     * Add sales receipt for the employee
     * @param id
     * @param dateInMillis
     * @param salesAmount
     */
    public void postSalesReceipt(String id, long dateInMillis, double salesAmount) {
        Employee employee = databaseProvider.queryEmployeeById(id);
        if (employee == null) {
            return;
        }
        if (employee instanceof SalariedEmployee) {
            ((SalariedEmployee) employee).postSalesReceipt(new SalesReceipt(dateInMillis, salesAmount));
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
