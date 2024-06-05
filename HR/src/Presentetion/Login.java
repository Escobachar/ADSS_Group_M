package Presentetion;

import Server.*;
import Domain.*;

import java.util.Scanner;
import java.util.Set;

public class Login {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("with items or no? \n 1.with\n2.without\n");
        Network network= Server.init.init(scanner.nextLine().equals("1"));
        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();
        Employee emp=null;
        boolean IDFound= false;
        while(true) {
            while(!IDFound) {
                while (!(onlyNumbers(ID))) {
                    System.out.print("ID not valid or not found.\nEnter  ID: ");
                    ID = scanner.nextLine();
                }
                emp = network.SearchByID(Integer.parseInt(ID));
                IDFound=emp!=null;
            }
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if(emp.getPassword().equals(password))
                Menu(emp);
            else
                System.out.println("password does not match");
            System.out.println("Enter  ID: ");
            IDFound= false;
            ID = scanner.nextLine();
        }

    }

    private static boolean onlyNumbers(String toCheck) {
        for (int i = 0; i < toCheck.length(); i++)
            if (!(toCheck.charAt(i) > 47 && toCheck.charAt(i) < 58))
                return false;
        return true;
    }

    private static void Menu(Employee emp) {
        boolean login = true;
        while (login) {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Hello " + emp.getName() + "\n\nactions menu:\n");
            Set<String> access = emp.getAccess();
            if (access.contains("updateShifts")) {
                System.out.println("Update your shifts");
            }
            if (access.contains("getShifts")) {
                System.out.println("Show your shifts");
            }


            System.out.println("Logoff");

            String action = scanner.nextLine();
            switch (action) {
                case "Update your shifts":
                    updateShifts(emp);
                    break;
                case "Show your shifts":
                    getShifts(emp);
                    break;
                case "Logoff":
                    login=false;
                    break;

            }
        }
    }

    private static void updateShifts(Employee emp) {
        String day = "";
        while (true) {
            System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n8.Back to main menu");
            Scanner scanner = new Scanner(System.in);
            day = scanner.nextLine();
            if (!(onlyNumbers(day)))
                System.out.println("Please send a number between 1-8.");
            else {
                int theDay = Integer.parseInt(day);
                if (theDay < 1 || theDay > 8)
                    System.out.println("Please send a number between 1-8.");
                else if (theDay == 8)
                    return;
                else {
                    System.out.println("Which shift?\n1.Morning\n2.Evening\n3.Back to day selection");
                    String shift = scanner.nextLine();
                    if (!(onlyNumbers(day)))
                        System.out.println("Not a valid option, select only numbers.");
                    else {
                        int theShift = Integer.parseInt(shift);
                        if (theShift < 1 || theShift > 3)
                            System.out.println("Please send a number between 1-3.");
                        else
                            ((GeneralEmployee) emp).getShiftsRequest()[theShift - 1][theDay - 1] = !((GeneralEmployee) emp).getShiftsRequest()[theShift - 1][theDay - 1];
                    }
                }
            }
        }
    }
    private static void getShifts(Employee emp){
        GeneralEmployee ge = (GeneralEmployee) emp;
        String[][] shifts = new String[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++)
                if (ge.getShiftsRequest()[i][j])
                    shifts[i][j] = "yes";
                else
                    shifts[i][j] = "no ";
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i + "  ");
        }


        for (int j = 0; j < Network.shifts; j++) {
            System.out.print("\n"+j+"  ");
            for (int i = 0; i < Network.days; i++) {
                System.out.print("|" + shifts[i][j]);
            }
        }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("---");
        }

    }

}
