package contorller;

import database.DatabaseProvider;
import models.Employee;
import models.HourlyEmployee;
import models.SalariedEmployee;
import utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

public class PayrollController {
    private final DatabaseProvider databaseProvider;

    public PayrollController(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public void runPayroll(long timeInMillis) {
        processHourlyEmployee(TimeUtils.getLastFridayFrom(timeInMillis));
        processSalariedEmployees(TimeUtils.getLastEndOfMonthFrom(timeInMillis));
        processSalesPayment(TimeUtils.getLastEvenFridayFrom(timeInMillis));
    }

    private void processHourlyEmployee(long timeInMillis) {
        List<Employee> hourlyEmployees =  databaseProvider.queryEmployeeList(HourlyEmployee.getStartingCode());
        for(Employee employee : hourlyEmployees) {
            if (employee instanceof HourlyEmployee) {
                double wages = employee.getPay(timeInMillis);
                if (wages > 0)
                    System.out.println("Pay " + employee.getId() + " named " + employee.getName() + " amount of " + wages);
            }
        }
    }

    private void processSalariedEmployees(long timeInMillis) {
        List<Employee> salariedEmployees =  databaseProvider.queryEmployeeList(SalariedEmployee.getStartingCode());
        for (Employee employee : salariedEmployees) {
            if (employee instanceof SalariedEmployee) {
                double salary = employee.getPay(timeInMillis);
                if (salary > 0)
                    System.out.println("Pay " + employee.getId() + " named " + employee.getName() + " amount of " + salary);
            }
        }
    }

    private void processSalesPayment(long timeInMillis) {
        List<Employee> salariedEmployees = databaseProvider.queryEmployeeList(SalariedEmployee.getStartingCode());
        for (Employee employee : salariedEmployees) {
            if (employee instanceof SalariedEmployee) {
                double commission = ((SalariedEmployee) employee).getSalesCommission(timeInMillis);
                if (commission > 0)
                    System.out.println("Pay " + employee.getId() + " named " + employee.getName() + " amount of " + commission);
            }
        }
    }
}
