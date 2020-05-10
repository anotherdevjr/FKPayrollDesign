import contorller.SystemController;

import java.util.Scanner;

public class MainExecutor {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        SystemController systemController = new SystemController();

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
            }
        } while(option < 7);
    }

    public static void showAddEmployeeInterface(Scanner sc, SystemController systemController) {
        System.out.print("Enter Name of Employee =>");
        //Consuming the enter pressed
        String name = sc.nextLine();
        System.out.print("Is the employee salaried (Enter 1) or hourly worker (Enter 2)? =>");
        int type = sc.nextInt();
        if (type == 1) {
            System.out.print("Enter the hourly rate of the employee => ");
            double hourlyRate = sc.nextDouble();

            systemController.addHourlyEmployee(name, hourlyRate);
        } else if (type == 2) {
            System.out.print("Enter the monthly salary of the employee => ");
            double salary = sc.nextDouble();
            System.out.println("Enter the sales commission rate of the employee => ");
            double commissionRate = sc.nextDouble();

            systemController.addSalariedEmployee(name, salary, commissionRate);
        } else {
            System.out.println("Invalid option entered.");
        }
    }
}
