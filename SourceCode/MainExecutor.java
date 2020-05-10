import contorller.SystemController;
import models.Employee;
import models.HourlyEmployee;
import models.paymentmodes.BankMode;
import models.paymentmodes.PostalMode;
import utils.TimeUtils;

import java.util.List;
import java.util.Scanner;

public class MainExecutor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SystemController systemController = new SystemController();
        TemporaryDataFiller.fillTemporaryData(systemController);


        int option = 0;
        do {
            System.out.println("\n\n\n-------Welcome to Payroll System-------------");
            System.out.println("What do you want to do?");
            System.out.println("1. Add New Employee");
            System.out.println("2. Delete Employee");
            System.out.println("3. Post Time Card");
            System.out.println("4. Post Sales Receipt");
            System.out.println("5. Change Employee Details");
            System.out.println("6. Run Payroll For Today");
            System.out.println("7. Exit");
            System.out.println("Enter your option");
            option = sc.nextInt();
            sc.nextLine();   //Consuming enter after the int
            if (option == 1) {
                showAddEmployeeInterface(sc, systemController);
            } else if (option == 2) {
                showDeleteEmployeeInterface(sc, systemController);
            } else if (option == 3) {
                showPostTimeCardInterface(sc, systemController);
            } else if (option == 6) {
                systemController.runPayRollTill(TimeUtils.getTodayDateInMillis());
            }
        } while(option < 7);

    }


    public static void showAddEmployeeInterface(Scanner sc, SystemController systemController) {
        System.out.println("\n\nAdd New Employee");
        System.out.print("Enter Name of Employee => ");
        //Consuming the enter pressed
        String name = sc.nextLine();
        System.out.print("Is the employee salaried (Enter 1) or hourly worker (Enter 2)? => ");
        int type = sc.nextInt();
        if (type == 2) {
            System.out.print("Enter the hourly rate of the employee => ");
            double hourlyRate = sc.nextDouble();
            sc.nextLine();
            String employeeId = systemController.addHourlyEmployee(name, hourlyRate);
            updatePaymentDetails(employeeId, sc, systemController);
        } else if (type == 1) {
            System.out.print("Enter the monthly salary of the employee => ");
            double salary = sc.nextDouble();
            sc.nextLine();
            System.out.print("Enter the sales commission rate of the employee => ");
            double commissionRate = sc.nextDouble();
            String employeeId = systemController.addSalariedEmployee(name, salary, commissionRate);
            updatePaymentDetails(employeeId, sc, systemController);
        } else {
            System.out.println("Invalid option entered.");
        }
    }

    private static void updatePaymentDetails(String employeeId, Scanner sc, SystemController systemController) {
        System.out.println("\nAdded Employee. Employee Id is :" + employeeId);
        System.out.print("Mode of payment has been set to pickup. Do you want to change Y or N :");
        String choice = sc.nextLine();
        if (choice.equals("N")) return;

        System.out.println("Which mode of payment you wish to set :");
        System.out.println("1. Postal Mode");
        System.out.println("2. Bank Deposit Mode");
        int mode = sc.nextInt();
        sc.nextLine();

        if (mode == 1) {
            System.out.println("Enter the postal address =>");
            String address = sc.nextLine();
            systemController.setPaymentMode(employeeId, new PostalMode(address));
        } else if (mode == 2) {
            System.out.println("Enter the bank account number");
            String accountNumber = sc.nextLine();
            systemController.setPaymentMode(employeeId, new BankMode(accountNumber));
        } else {
            System.out.println("Invalid option entered. Default payment mode applied.");
        }
    }



    public static void showDeleteEmployeeInterface(Scanner sc, SystemController systemController) {
        System.out.println("\n\nDelete New Employee");
        System.out.print("Do you want to view employee list? Y or N => ");
        String choice = sc.next();
        sc.nextLine();
        if (choice.equals("Y")) {
            showEmployeeList(systemController.getEmployeeList(Employee.getStartingCode()));
        }

        System.out.print("Enter Employee Id of Employee to be deleted => ");
        String id = sc.nextLine();
        systemController.deleteEmployeeWithId(id);
    }

    private static void showPostTimeCardInterface(Scanner sc, SystemController systemController) {
        System.out.println("\n\nPost New Time Card");
        System.out.print("Do you want to view employee list? Y or N => ");
        String choice = sc.next();
        sc.nextLine();
        if (choice.equals("Y")) {
            // Showing only the Hourly Employees since other do not have time cards feature
            showEmployeeList(systemController.getEmployeeList(HourlyEmployee.getStartingCode()));
        }

        System.out.print("Enter Employee Id of Employee to add time card to  => ");
        String id = sc.nextLine();

        System.out.print("How many days elapsed since the time card. Today is 0, Yesterday = 1, so on => ");
        int shift = sc.nextInt();
        sc.nextLine();
        long dateInMillis = TimeUtils.getTodayDateInMillis() - shift * TimeUtils.MILLIS_IN_DAY;

        System.out.print("Hours worked on that day => ");
        int hours = sc.nextInt();
        sc.nextLine();

        systemController.postTimeStamp(id, dateInMillis, hours);
    }


    public static void showEmployeeList(List<Employee> employees) {
        System.out.println("ID\t\t Name");
        System.out.println("-----------------------------------------");
        for(Employee employee: employees) {
            System.out.println(employee.getId() + "\t" + employee.getName());
        }
        System.out.println("\n\n");
    }
}
