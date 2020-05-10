package models;

public class SalariedEmployee extends Employee{

    private double salary;
    private double commissionRate;

    public SalariedEmployee(String id, String name, double salary, double commissionRate) {
        super(id, name);
        this.salary = salary;
        this.commissionRate = commissionRate;
    }


    @Override
    public double getPay() {
        return salary;
    }
}
