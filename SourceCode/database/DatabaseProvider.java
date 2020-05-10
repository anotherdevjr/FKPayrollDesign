package database;

import models.Employee;
import models.SalesReceipt;
import models.TimeStamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

public class DatabaseProvider {
    private final ArrayList<Employee> employeeList;
    private final HashMap<String, ArrayList<TimeStamp>> timeStampList;
    private final HashMap<String, ArrayList<SalesReceipt>> salesReceiptList;

    public DatabaseProvider() {
        employeeList = new ArrayList<>();
        timeStampList = new HashMap<>();
        salesReceiptList = new HashMap<>();
    }

    public List<Employee> queryEmployeeList(String startingCode) {
        return employeeList.stream()
                .filter(t -> t.getId().startsWith(startingCode))
                .peek(t -> t.setDatabaseProvider(this)).collect(Collectors.toList());
    }

    public Employee queryEmployeeById(String employeeId) {
        for (Employee e : employeeList) {
            if (e.getId().equals(employeeId)) {
                e.setDatabaseProvider(this);
                return e;
            }
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
        if (!timeStampList.containsKey(employeeId)) {
            timeStampList.put(employeeId, new ArrayList<>());
        }
        timeStampList.get(employeeId).add(timeStamp);
    }

    public List<TimeStamp> getTimeStampsForEmployee(String employeeId, long endDate) {
        ArrayList<TimeStamp> timeStamps = timeStampList.getOrDefault(employeeId, new ArrayList<>());
        List<TimeStamp> filteredList = timeStamps.stream()
                .filter(t -> t.getDateInMillis() <= endDate).collect(Collectors.toList());
        timeStamps.removeIf(t -> t.getDateInMillis() <= endDate);
        return filteredList;
    }

    public void postSalesReceipt(String id, SalesReceipt salesReceipt) {
        if (!salesReceiptList.containsKey(id)) {
            salesReceiptList.put(id, new ArrayList<>());
        }
        salesReceiptList.get(id).add(salesReceipt);
    }

    public List<SalesReceipt> getSalesReceiptListForEmployee(String employeeId, long endDate) {
        ArrayList<SalesReceipt> receipts = salesReceiptList.getOrDefault(employeeId, new ArrayList<>());
        List<SalesReceipt> filteredList = receipts.stream()
                .filter(t -> t.getDateInMillis() <= endDate).collect(Collectors.toList());
        receipts.removeIf(t -> t.getDateInMillis() <= endDate);
        return filteredList;
    }
}
