package database;

import models.Employee;
import models.TimeStamp;

import java.util.ArrayList;

public class EmployeeProvider {
    private final ArrayList<Employee> employeeList;
    private final ArrayList<TimeStamp> timeStampList;

    public EmployeeProvider() {
        employeeList = new ArrayList<>();
        timeStampList = new ArrayList<>();
    }

    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    public void deleteEmployeeById(String employeeId) {
        employeeList.removeIf(e -> e.getId().equals(employeeId));
    }

    public void postTimeStamp(String employeeId, TimeStamp timeStamp) {
        timeStampList.add(timeStamp);
    }

    public ArrayList<TimeStamp> getTimeStampsForEmployee(String employeeId) {
        return timeStampList;
    }
}
