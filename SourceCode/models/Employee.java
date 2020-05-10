package models;

import database.DatabaseProvider;

public abstract class Employee {
    private String id;
    private String name;
    private DatabaseProvider databaseProvider;

    public Employee(String id, String name) {
        this.id = id;
        this.name = name;
    }

    public void setDatabaseProvider(DatabaseProvider databaseProvider) {
        this.databaseProvider = databaseProvider;
    }

    public DatabaseProvider getDatabaseProvider() {
        return databaseProvider;
    }

    public String getId() {
        return id;
    }

    public String getName() { return name; }
    public abstract double getPay();
}
