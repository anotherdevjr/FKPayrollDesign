package database;

import models.Employee;
import models.SalesReceipt;
import models.TimeStamp;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Although I was not able to integrate database since due to exams I could only devote
 * 9 hours to this project. Yet the whole code is written keeping long term scalability in mind.
 *
 * So, the purpose of this class is that we can create access to database here and do the desired
 * CRUD operations here itself. The DatabaseProvider works as an abstraction layer for other parts
 * of the code and database layer.
 *
 * Example : queryEmployeeList takes a starting code and return list of employees with
 *          id beginning with it. The database access should not be visible to outer world.
 *          It should request for collection of model objects and receive just that.
 *
 *          If in case we need to change the database (Or integrate one) all we need to do is
 *          change this class methods to work with those and we are good to go.
 */
public class DatabaseProvider {
    // Employee database
    private final ArrayList<Employee> employeeList;

    // Time stamp and sales receipt will have employee id as foreign key.
    private final HashMap<String, ArrayList<TimeStamp>> timeStampList;
    private final HashMap<String, ArrayList<SalesReceipt>> salesReceiptList;

    public DatabaseProvider() {
        employeeList = new ArrayList<>();
        timeStampList = new HashMap<>();
        salesReceiptList = new HashMap<>();
    }

    /**
     * Accesses database to query list of employees with Id beginning with the given starting code
     * @param startingCode The starting code needed for filtering employees. Pass "" to get all employees
     * @return The list of employees (We also need to set the database provider in employees for it to have access
     *          to the database in case it needs to write over its data.
     */
    public List<Employee> queryEmployeeList(String startingCode) {
        return employeeList.stream()
                .filter(t -> t.getId().startsWith(startingCode))
                .peek(t -> t.setDatabaseProvider(this)).collect(Collectors.toList());
    }

    /**
     * Find employee with given EmployeeId.
     * @param employeeId
     * @return Employee object if found else null.
     */
    public Employee queryEmployeeById(String employeeId) {
        for (Employee e : employeeList) {
            if (e.getId().equals(employeeId)) {
                e.setDatabaseProvider(this);
                return e;
            }
        }
        return null;
    }

    /**
     * Write an employee to the database.
     * @param employee
     */
    public void addEmployee(Employee employee) {
        employeeList.add(employee);
    }

    /**
     * Delete employee from the database. If not found it silently returns.
     * @param employeeId
     */
    public void deleteEmployeeById(String employeeId) {
        employeeList.removeIf(e -> e.getId().equals(employeeId));
    }

    /**
     * Add time card for the employee.
     * @param employeeId
     * @param timeStamp
     */
    public void postTimeStamp(String employeeId, TimeStamp timeStamp) {
        if (!timeStampList.containsKey(employeeId)) {
            timeStampList.put(employeeId, new ArrayList<>());
        }
        timeStampList.get(employeeId).add(timeStamp);
    }

    /**
     * Add sales receipt for the employee.
     * @param id
     * @param salesReceipt
     */
    public void postSalesReceipt(String id, SalesReceipt salesReceipt) {
        if (!salesReceiptList.containsKey(id)) {
            salesReceiptList.put(id, new ArrayList<>());
        }
        salesReceiptList.get(id).add(salesReceipt);
    }

    /**
     * Return time cards of Employee with employeeId, with the Time Cards filtered so that
     * it is before the endDate passed.
     * @param employeeId
     * @param endDate
     * @return
     */
    public List<TimeStamp> getTimeStampsForEmployee(String employeeId, long endDate) {
        ArrayList<TimeStamp> timeStamps = timeStampList.getOrDefault(employeeId, new ArrayList<>());
        List<TimeStamp> filteredList = timeStamps.stream()
                .filter(t -> t.getDateInMillis() <= endDate).collect(Collectors.toList());
        timeStamps.removeIf(t -> t.getDateInMillis() <= endDate);
        return filteredList;
    }


    /**
     *  Return Sales Receipt of Employee with employeeId, with the Receipts filtered so that
     *  it is before the endDate passed.
     * @param employeeId
     * @param endDate
     * @return
     */
    public List<SalesReceipt> getSalesReceiptListForEmployee(String employeeId, long endDate) {
        ArrayList<SalesReceipt> receipts = salesReceiptList.getOrDefault(employeeId, new ArrayList<>());
        List<SalesReceipt> filteredList = receipts.stream()
                .filter(t -> t.getDateInMillis() <= endDate).collect(Collectors.toList());
        receipts.removeIf(t -> t.getDateInMillis() <= endDate);
        return filteredList;
    }
}
