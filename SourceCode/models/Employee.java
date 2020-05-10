package models;

import database.EmployeeProvider;

public abstract class Employee {
    private String id;
    private String name;
    private EmployeeProvider employeeProvider;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setProvider(EmployeeProvider employeeProvider) {
        this.employeeProvider = employeeProvider;
    }

    public EmployeeProvider getEmployeeProvider() {
        return employeeProvider;
    }

    public String getId() {
        return id;
    }

    public abstract double getPay();
}
