package Presentetion;

import Domain.*;

import java.util.Date;
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
        System.out.println("Hello " + emp.getName());
        Scanner scanner = new Scanner(System.in);
        while (login) {
            System.out.println("\n\nactions menu:\n");
            Set<String> access = emp.getAccess();
            System.out.println("Show your Details");
            //GeneralEmployee
            if (access.contains("updateShifts")) {
                System.out.println("Update your shifts");
            }
            if (access.contains("getShifts")) {
                System.out.println("Show this week shifts");
            }
            if (access.contains("ShowShiftReq")) {
                System.out.println("Show your shifts Requests");
            }
            //BranchManager
            if (access.contains("AddGeneralEmployee")) {
                System.out.println("Add general employee");
            }
            if (access.contains("UpdateBranchShifts")) {
                System.out.println("Update shifts of the week");
            }
            if (access.contains("UpdateGeneralEmployeeRoles")) {
                System.out.println("Update roles of employee");
            }
            if (access.contains("UpdateGeneralEmployeeDetails")) {
                System.out.println("Update details of the employee");
            }
            if (access.contains("ShowEmployeeDetails")) {
                System.out.println("Show details on an employee");
            }
            if (access.contains("UpdateRolesOfShifts")) {
                System.out.println("Update roles for next shifts");
            }
            if (access.contains("ShowShiftsAvailability")) {
                System.out.println("Show shifts availability per role");
            }

        }

            System.out.println("Logoff");

            String action = scanner.nextLine();
            switch (action) {

                case "Show your Details":
                    showYourDetails(emp);
                    break;
                //General Employee
                case "Update your shifts":
                    updateShifts(emp);
                    break;
                case "Show this week shifts":
                    getGeneralEmployeeShifts(emp);
                    break;
                case "Show your shifts Requests":
                    getShiftsReq(emp);
                    break;
                //Branch Manager
                case "Add general employee":

                case "Logoff":
                    login=false;
                    break;

                default:
                    System.out.println("not a valid action\n");
            }
        }

    private static void showYourDetails(Employee emp) {
        System.out.println("ID: " + emp.getID());
        System.out.println("Name: " + emp.getName());
        System.out.println("Bank account details: " + emp.getBankAccountDetails());
        System.out.println("Salary: " + emp.getSalary());
        System.out.println("Start of employment: " + emp.getStartOfEmployment());
        if(emp.getEndOfEmployment()!=null)
            System.out.println("End of employment: " + emp.getEndOfEmployment());
        else
            System.out.println("End of employment: TBD");
        System.out.println("Part of job: " + emp.getPartOfJob());
        System.out.println("Vacations days: " + emp.getVacationsDays());
        System.out.println("Password: " + emp.getPassword());

        if(emp instanceof GeneralEmployee) {
            GeneralEmployee ge = (GeneralEmployee) emp;
            if (ge.isManager())
                System.out.println("Shift Manager");
            else {
                if (ge.getRoles().isEmpty())
                    System.out.println("roles: none");
                else if (ge.getRoles() == null)
                    System.out.println("system error, roles=null");
                else {
                    System.out.println("roles:");
                    for (Role r : ge.getRoles())
                        System.out.println("role - " + r.getRoleName());
                }
            }
            System.out.println("Shifts requests for next week:");
            getShiftsReq(emp);
            System.out.println("Branch: " + ge.getBranch());
        }
        else if(emp instanceof BranchManager){
            BranchManager bm = (BranchManager) emp;
            System.out.println("Branch: " + bm.getBranch());
        }
        else if(emp instanceof HRManager){
            //empty for now
        }
    }
    //General Employee
    private static void getGeneralEmployeeShifts(Employee emp) {
        GeneralEmployee ge = (GeneralEmployee) emp;
        String[][] shifts = new String[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++)
                if (ge.getBranch().getEmployeesShifts()[i][j].contains(ge.getID()))
                    shifts[i][j] = "yes";
                else
                    shifts[i][j] = "no ";
        shiftPrinter(shifts);
    }
    private static void shiftPrinter(String[][] shifts){
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
                        else {
                            ((GeneralEmployee) emp).getShiftsRequest()[theShift - 1][theDay - 1] = !((GeneralEmployee) emp).getShiftsRequest()[theShift - 1][theDay - 1];
                            System.out.println("your new shifts request:");
                            getShiftsReq(emp);
                            System.out.println();
                        }

                    }
                }
            }
        }
    }
    private static void getShiftsReq(Employee emp){
        GeneralEmployee ge = (GeneralEmployee) emp;
        String[][] shifts = new String[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++)
                if (ge.getShiftsRequest()[i][j])
                    shifts[i][j] = "yes";
                else
                    shifts[i][j] = "no ";
        shiftPrinter(shifts);
    }
    //Branch Manager
    private static void AddGeneralEmployee(Employee emp) {
        System.out.println("Adding new General employee:");
        System.out.println("ID: ");
        Scanner scanner = new Scanner(System.in);
        String id = scanner.nextLine();
        System.out.println("Name: ");
        String name = scanner.nextLine();
        System.out.println("Bank account details: ");
        String bankAccount = scanner.nextLine();
        System.out.println("Salary: ");
        String salary = scanner.nextLine();
        System.out.println("Start of employment: ");
        String startOfEmp = scanner.nextLine();
        System.out.println("End of employment: ");
        String endOfEmp = scanner.nextLine();
        if (endOfEmp.equals("TBD"))
            endOfEmp = null;
        System.out.println("Part of job: ");
        String partOfJob = scanner.nextLine();
        System.out.println("Vacations days: ");
        String vacationDays = scanner.nextLine();
        System.out.println("Password: ");
        String password = scanner.nextLine();
        System.out.println("Is he a shift manager?: ");
        String shiftManager = scanner.nextLine();
        if(shiftManager.equals("no"))

            if (ge.isManager())
                System.out.println("Shift Manager");
            else {
                if (ge.getRoles().isEmpty())
                    System.out.println("roles: none");
                else if (ge.getRoles() == null)
                    System.out.println("system error, roles=null");
                else {
                    System.out.println("roles:");
                    for (Role r : ge.getRoles())
                        System.out.println("role - " + r.getRoleName());
                }
            }
            System.out.println("Shifts requests for next week:");
            getShiftsReq(emp);
            System.out.println("Branch: " + ge.getBranch());

        }
    }
}
