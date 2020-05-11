package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import utils.TimeUtils;

import java.util.List;

/**
 * The payroll controller has a single public method called runPayroll that takes
 * a date in milliseconds and run all the payments till that date.
 *
 * Note that the payroll needs to manage last payments records so that running
 * payroll 2 times for same date doesn't dispatch payment twice but only processes it the first
 * time.
 */
public class PayrollController {
    private final DatabaseProvider databaseProvider;

    public PayrollController(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    /**
     * Is calculates the last Friday, last even Friday and last end of month
     * From the passed date in Milliseconds. Then processes the requisite payments
     * for the respective dates.
     * @param timeInMillis
     */
    public void runPayroll(long timeInMillis) {
        System.out.println("Running payroll for " + TimeUtils.getDateStringFromMillis(timeInMillis));
        processHourlyEmployee(TimeUtils.getLastFridayFrom(timeInMillis));

        // The salary and sales commission have different time lines of dispatch.
        // Hence they are processed independently. So, two payments would be dispatched
        // one with salary and commission payment in case it is not 0.
        processSalariedEmployees(TimeUtils.getLastEndOfMonthFrom(timeInMillis));
        processSalesPayment(TimeUtils.getLastEvenFridayFrom(timeInMillis));
    }

    /**
     * Process the hourly employees wages
     * @param timeInMillis The date of last Friday in millis
     */
    private void processHourlyEmployee(long timeInMillis) {
        System.out.println("---- Processing hourly employees payment till latest Friday i.e. "
                + TimeUtils.getDateStringFromMillis(timeInMillis) +" -----");
        List<Employee> hourlyEmployees =  databaseProvider.queryEmployeeList(HourlyEmployee.getStartingCode());
        for(Employee employee : hourlyEmployees) {
            if (employee instanceof HourlyEmployee) {
                double wages = employee.getPay(timeInMillis);
                if (wages > 0)
                    employee.dispatchPayment(wages);
            }
        }
    }

    /**
     * Process the salaries of Salaried Employees.
     * @param timeInMillis The date of last month's end
     */
    private void processSalariedEmployees(long timeInMillis) {
        System.out.println("---- Processing salary based employees payment till latest month i.e. "
                + TimeUtils.getDateStringFromMillis(timeInMillis) +" -----");
        List<Employee> salariedEmployees =  databaseProvider.queryEmployeeList(SalariedEmployee.getStartingCode());
        for (Employee employee : salariedEmployees) {
            if (employee instanceof SalariedEmployee) {
                double salary = employee.getPay(timeInMillis);
                if (salary > 0)
                    employee.dispatchPayment(salary);
            }
        }
    }

    /**
     * Process all the sales commissions
     * @param timeInMillis The date of last even occurrence of Friday in milliseconds
     */
    private void processSalesPayment(long timeInMillis) {
        System.out.println("---- Processing sales commission payment till latest even Friday i.e. "
                + TimeUtils.getDateStringFromMillis(timeInMillis) +" -----");
        List<Employee> salariedEmployees = databaseProvider.queryEmployeeList(SalariedEmployee.getStartingCode());
        for (Employee employee : salariedEmployees) {
            if (employee instanceof SalariedEmployee) {
                double commission = ((SalariedEmployee) employee).getSalesCommission(timeInMillis);
                if (commission > 0)
                    employee.dispatchPayment(commission);
            }
        }
    }
}
