package database;

import models.Employee;
import models.SalesReceipt;
import models.TimeStamp;
import java.util.ArrayList;

public class DatabaseProvider {
    private final ArrayList<Employee> employeeList;
    private final ArrayList<TimeStamp> timeStampList;
    private final ArrayList<SalesReceipt> salesReceiptList;

    public DatabaseProvider() {
        employeeList = new ArrayList<>();
        timeStampList = new ArrayList<>();
        salesReceiptList = new ArrayList<>();
    }

    public ArrayList<Employee> queryEmployeeList(String startingCode) {
        return employeeList;
    }

    public Employee queryEmployeeById(String employeeId) {
        for (Employee e : employeeList) {
            if (e.getId().equals(employeeId))
                return e;
        }
        return null;
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

    public void postSalesReceipt(String id, SalesReceipt salesReceipt) {
        salesReceiptList.add(salesReceipt);
    }

    public ArrayList<SalesReceipt> getSalesReceiptListForEmployee(String employeeId) {
        return salesReceiptList;
    }
}
