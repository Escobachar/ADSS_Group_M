package Presentetion;

import Domain.*;
import java.util.*;

public class Login {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        boolean flag=true;
        Network network=null;
        while(flag) {
            System.out.println("with items or no? \n1.with\n2.without");
            String answer = scanner.nextLine();
            flag = false;
            if (answer.equals("1"))
                network = Server.init.init(true);
            else if (answer.equals("2"))
                network = Server.init.init(false);
            else
                flag=true;
        }
        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();
        while(true) {
            Employee emp=null;
            boolean IDFound= true;
            do{
                while (!(onlyNumbers(ID)) || !IDFound) {
                    System.out.print("ID not valid or not found.\nEnter  ID: ");
                    ID = scanner.nextLine();
                    if(onlyNumbers(ID))
                        IDFound=true;
                }
                emp = network.SearchByID(Integer.parseInt(ID));
                IDFound=emp!=null;
            }while((!IDFound) );
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if(emp.getPassword().equals(password)) {
                System.out.println();
                Menu(emp);
            }
            else
                System.out.println("password does not match");
            System.out.print("Enter  ID: ");
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
            System.out.println("\nactions menu:\n");
            Set<String> access = emp.getAccess();
            System.out.println("Show your details");
            //GeneralEmployee
            if (access.contains("updateShifts")) {
                System.out.println("Update your shifts");
            }
            if (access.contains("getShifts")) {
                System.out.println("Show this week shifts");
            }
            if (access.contains("ShowShiftReq")) {
                System.out.println("Show your shifts requests");
            }
            //BranchManager
            if (access.contains("AddGeneralEmployee")) {
                System.out.println("Add general employee");
            }
            if (access.contains("UpdateBranchShifts")) {//this
                System.out.println("Update shifts of the week");
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


            System.out.println("Logoff");

            String action = scanner.nextLine();
            switch (action) {

                case "Show your details":
                    showYourDetails(emp);
                    break;
                //General Employee
                case "Update your shifts":
                    updateShifts((GeneralEmployee)emp);
                    break;
                case "Show this week shifts":
                    getGeneralEmployeeShifts(emp);
                    break;
                case "Show your shifts requests":
                    getShiftsReq(emp);
                    break;
                //Branch Manager
                case "Add general employee":
                    AddGeneralEmployee((Manager) emp);
                    break;
                case "Update details of the employee":
                    UpdateGeneralEmployeeDetails(emp);
                    break;
                case "Show details on an employee":
                    ShowDetailsOnGeneralEmployee((BranchManager) emp);
                    break;
                case "Show shifts availability per role":
                    ShowShiftAvailability((BranchManager)emp);
                    break;
                case "Update roles for next shifts":
                    UpdateRolesOfShifts((BranchManager)emp);
                    break;
                case "Update shifts of the week":
                    UpdateShiftsOfWeek((BranchManager)emp);
                    break;
                case "Logoff":
                    login = false;
                    break;

                default:
                    System.out.println("not a valid action\n");
            }
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
            System.out.println("Branch: " + ge.getBranch().getBranchName());
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
            System.out.print("\n"+(j+1)+"  ");
            for (int i = 0; i < Network.days; i++) {
                System.out.print("|" + shifts[j][i]);
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
        }
        System.out.println();
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
                        else if(theShift != 3){
                            emp.getShiftsRequest()[theShift - 1][theDay - 1] = !emp.getShiftsRequest()[theShift - 1][theDay - 1];
                            for (Role r : emp.getRoles())
                                        if (emp.getShiftsRequest()[theShift - 1][theDay - 1])
                                            emp.getBranch().getShiftsAvailability().get(r)[theShift - 1][theDay - 1].add(emp);
                                        else
                                            emp.getBranch().getShiftsAvailability().get(r)[theShift - 1][theDay - 1].remove(emp);
                        }

                            System.out.println("your new shifts request:");
                            getShiftsReq(emp);
                            System.out.println();

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
    private static void ShowDetailsOnGeneralEmployee(BranchManager emp) {
        Scanner scanner = new Scanner(System.in);
        Integer id = null;
        while(true) {
            do {
                if (id != null)
                    System.out.println("ID not valid");
                id = -1;
                System.out.println("ID of employee to show?");
                String salaryScan = scanner.nextLine();
                if (onlyNumbers(salaryScan))
                    id = Integer.parseInt(salaryScan);
            } while (!Network.CheckID(id));
            GeneralEmployee ge=emp.getBranch().SearchGeneralEmployee(id);
            if(ge!=null) {
                showYourDetails(ge);
                return ;
            }
        }
    }
    private static void AddGeneralEmployee(Manager emp) {
        System.out.println("Adding new General employee:");
        Scanner scanner = new Scanner(System.in);
        Integer id=null;
        String Id=null;
        do {
            if(Id!=null)
                System.out.println("ID not valid");
            System.out.println("ID: ");
            Id = scanner.nextLine();
            if(onlyNumbers(Id))
                id=Integer.parseInt(Id);
        }while(!Network.CheckID(id));

        String name=null;
        do {
            if(name!=null)
                System.out.println("name not valid");
            System.out.println("Name: ");
            name = scanner.nextLine();
        }while(!Network.CheckName(name));

        String bankAccount=null;
        do {
            if(bankAccount!=null)
                System.out.println("Bank account details not valid");
            System.out.println("Bank account details: ");
            bankAccount = scanner.nextLine();
        }while(!Network.CheckBankAccountDetails(bankAccount));

        Integer salary=null;
        do {
            if(salary!=null)
                System.out.println("Salary not valid");
            salary=-1;
            System.out.println("Salary: ");
            String salaryScan = scanner.nextLine();
            if(onlyNumbers(salaryScan))
                salary=Integer.parseInt(salaryScan);
        }while(!Network.checkSalary(salary));

        String startOfEmp=null;
        do{
            if(startOfEmp!=null)
                System.out.println("Start of employment not valid");
            System.out.println("Start of employment(xx mm yyyy): ");
            startOfEmp = scanner.nextLine();
        }while(!Network.checkCreateDate(startOfEmp));

        String endOfEmp=null;
        do{
            if(endOfEmp!=null)
                System.out.println("end of employment not valid");
            System.out.println("end of employment(xx mm yyyy) or TBD: ");
            endOfEmp = scanner.nextLine();
        }while(!Network.checkCreateDate(endOfEmp) & !endOfEmp.equals("TBD"));
        if (endOfEmp.equals("TBD"))
            endOfEmp = null;

        String partOfJob=null;
        do {
            if(partOfJob!=null)
                System.out.println("part of job not valid");
            System.out.println("part of job(Half or Full): ");
            partOfJob = scanner.nextLine();
        }while(!Network.checkPartOfJob(partOfJob));

        Integer vacationDays=null;
        do {
            if(vacationDays!=null)
                System.out.println("Vacation days not valid");
            vacationDays=-1;
            System.out.println("Vacation days: ");
            String vacationDaysScan = scanner.nextLine();
            if(onlyNumbers(vacationDaysScan))
                vacationDays=Integer.parseInt(vacationDaysScan);
        }while(!Network.checkSalary(vacationDays));


        System.out.println("Password: ");
        String password = scanner.nextLine();
        System.out.println("Is he a shift manager?: ");
        String shiftManager = scanner.nextLine();
        boolean SM=true;
        List<Role> newRL=new ArrayList<>();
        if(shiftManager.equals("no")) {
            SM=false;
            System.out.println("Choose roles of the employee,\npress all the numbers of the roles you desire then enter: ");
            List<Role> RL = null;
            RL = ((Manager) emp).getNetwork().getRoles();
            int i = 1;
            for (Role r : RL) {
                System.out.println(i + ". " + r.getRoleName());
                i++;
            }
            String rolesLine = scanner.nextLine();
            int j=0;
            i = 1;
            for (Role r : RL) {
                if(i==rolesLine.charAt(j)) {
                    newRL.add(r);
                    j++;
                }
                i++;
                if(j>rolesLine.length())
                    break;
            }
        }
        if(emp instanceof HRManager) {
            String branchName = null;
            do {
                if (branchName != null)
                    System.out.println("Branch name not valid");
                System.out.println("Branch name: ");
                branchName = scanner.nextLine();
            } while (!((Manager) emp).getNetwork().checkBranch(branchName));
            Branch branch = ((Manager) emp).getNetwork().getBranch(branchName);
            ((HRManager)emp).addGeneralEmployee(id,name,bankAccount,salary,new Date(startOfEmp),new Date(endOfEmp),partOfJob,vacationDays,newRL,SM,branch,password);
        }
        else
            ((BranchManager)emp).addGeneralEmployee(id,name,bankAccount,salary,new Date(startOfEmp),new Date(endOfEmp),partOfJob,vacationDays,newRL,SM,password);

    }
    private static void UpdateGeneralEmployeeDetails(Employee emp) {
        System.out.println("Update General employee:");
        Integer id = emp.getID();
        Scanner scanner = new Scanner(System.in);

        System.out.println("DO you want to change employee name? y/n ");
        String ans = scanner.nextLine();
        if (ans.equals("y")){
            String name = "";
            do{
                System.out.println("New name: ");
                name = scanner.nextLine();}
            while (!Network.CheckName(name));
        }
        else {String name = emp.getName();}

        System.out.println("DO you want to change employee bank account details? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String bankAccount = "";
            do{
                System.out.println("New bank account details: ");
                bankAccount = scanner.nextLine();}
            while (!Network.CheckBankAccountDetails(bankAccount));
        }
        else {String bankAccount = emp.getBankAccountDetails();}

        System.out.println("DO you want to change employee salary? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String salary ="";
            do{
                System.out.println("New salary: ");
                salary = scanner.nextLine();
            }
            while (!Network.checkSalary(salary));
        }
        else {Integer salary = emp.getSalary();}


        Date startOfEmp = emp.getStartOfEmployment();

        System.out.println("DO you want to change employee end of employment? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String endOfEmp = "";
            do{
                System.out.println("New end of employment: ");
                endOfEmp = scanner.nextLine();
            }
            while (!Network.checkCreateDate(endOfEmp));
        }
        else {Date endOfEmp = emp.getStartOfEmployment();}

        System.out.println("DO you want to change employee part of job? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String partOfJob = "";
            do{
                System.out.println("New part of job: ");
                partOfJob = scanner.nextLine();
            }
            while (!Network.checkPartOfJob(partOfJob));
        }
        else {String partOfJob = emp.getPartOfJob();}

        System.out.println("DO you want to change employee vacations days? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String vacationDays = "";
            do{
                System.out.println("New vacations days: ");
                vacationDays = scanner.nextLine();
            }
            while (!Network.checkVacationsDays(vacationDays));

        }
        else {Integer vacationDays = emp.getVacationsDays();}

        System.out.println("DO you want to change employee password? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            System.out.println("New password: ");
            String password = scanner.nextLine();
        }
        else {String password = emp.getPassword();}

        boolean shiftManager = ((GeneralEmployee)emp).isManager();
        if(shiftManager){
            System.out.println("This employee is a shift manager. Do you like to change it? y/n ");
            ans = scanner.nextLine();
            if (ans.equals("y")){
                shiftManager = false;
                System.out.println("Choose roles of the employee,\npress all the numbers of the roles you desire then enter: ");
                List<Role> RL = ((Manager) emp).getNetwork().getRoles();
                List<Role> newRL = null;
                int i = 1;
                for (Role r : RL) {
                    System.out.println(i + ". " + r.getRoleName());
                    i++;
                }
                String rolesLine = scanner.nextLine();
                int j=0;
                i = 1;
                for (Role r : RL) {
                    if(i==rolesLine.charAt(j)) {
                        newRL.add(r);
                        j++;
                    }
                    i++;
                    if(j>rolesLine.length())
                        break;
                }
            }
        }
        else {
            System.out.println("This employee is not a shift manager. Do you like to change it? y/n ");
            ans = scanner.nextLine();
            if (ans.equals("y")){
                shiftManager = true;
            }
        }

        System.out.println("DO you want to change employee branch? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String branch = "";
            do{
                System.out.println("New branch: ");
                branch = scanner.nextLine();
            }
            while (!((Manager) emp).getNetwork().checkBranch(branch));
        }
        else {Branch branch = ((GeneralEmployee)emp).getBranch();}


    }
}


