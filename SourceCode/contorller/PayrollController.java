package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import utils.TimeUtils;

import java.util.List;

public class PayrollController {
    private final DatabaseProvider databaseProvider;

    public PayrollController(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public void runPayroll(long timeInMillis) {
        System.out.println("Running payroll for " + TimeUtils.getDateStringFromMillis(timeInMillis));
        processHourlyEmployee(TimeUtils.getLastFridayFrom(timeInMillis));
        processSalariedEmployees(TimeUtils.getLastEndOfMonthFrom(timeInMillis));
        processSalesPayment(TimeUtils.getLastEvenFridayFrom(timeInMillis));
    }

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
