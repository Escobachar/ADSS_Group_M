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
        return !toCheck.equals("");
    }

    private static boolean onlyNumbersBetween(String toCheck, Integer min, Integer max) {
        for (int i = 0; i < toCheck.length(); i++)
            if (!(toCheck.charAt(i) > 47 && toCheck.charAt(i) < 58))
                return false;
        if ((Integer.parseInt(toCheck)>max)|(Integer.parseInt(toCheck)<min))
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

            //HR Manager
            if (access.contains("HRAddGeneralEmployee")) {
                System.out.println("HR add general employee");
            }
            if (access.contains("HRAddBranchManager")) {
                System.out.println("HR add branch manager");
            }
            if (access.contains("HRUpdateGeneralEmployeeDetails")) {
                System.out.println("HR update general employee details");
            }
            if (access.contains("HRUpdateBranchManagerDetails")) {
                System.out.println("HR update branch manager details");
            }
            if (access.contains("HRShowGeneralEmployeeDetails")) {
                System.out.println("HR show general employee details");
            }
            if (access.contains("HRShowBranchManagerDetails")) {
                System.out.println("HR show branch manager details");
            }
            if (access.contains("HRUpdateBranchShifts")) {
                System.out.println("HR update branch shifts");
            }
            if (access.contains("HRUpdateBranchRolesOfShifts")) {
                System.out.println("HR update branch roles of shifts");
            }
            if (access.contains("HRShowBranchShiftsAvailability")) {
                System.out.println("HR show branch shifts availability per role");
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
                    BMUpdateRolesOfShifts((BranchManager)emp);
                    break;
                case "Update shifts of the week":
                    UpdateShiftsOfWeek((BranchManager)emp);
                    break;
                //HR Manager
                case "HR add general employee":
                    AddGeneralEmployee((HRManager)emp);
                    break;
                case "HR add branch manager":
                    //HRAddBranchManager((HRManager)emp);
                    break;
                case "HR update general employee details":
                    UpdateGeneralEmployeeDetails((HRManager)emp);
                    break;
                case "HR update branch manager details":
                    //HRUpdateBranchManagerDetails((HRManager)emp);
                    break;
                case "HR show general employee details":
                    //HRShowGeneralEmployeeDetails((HRManager)emp);
                    break;
                case "HR show branch manager details":
                    //HRShowBranchManagerDetails((HRManager)emp);
                    break;
                case "HR update branch shifts":
                    //HRUpdateBranchShifts((HRManager)emp);
                    break;
                case "HR update branch roles of shifts":
                    HRUpdateBranchRolesOfShifts((HRManager)emp);
                    break;
                case "HR show branch shifts availability per role":
                    //HRShowBranchShiftsAvailability((HRManager)emp);
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
                if (ge.getBranch().getEmployeesShifts()[i][j].get(ge.getName())!=null)
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
    private static void shiftPrinter(Integer[][] shifts){
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i + "   ");
        }
        for (int j = 0; j < Network.shifts; j++) {
            System.out.print("\n"+(j+1)+"   ");
            for (int i = 0; i < Network.days; i++) {
                System.out.print("|" + shifts[j][i]+"   ");
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
        }
        System.out.println();
    }
    private static void updateShifts(GeneralEmployee emp) {
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
    private static void BMUpdateRolesOfShifts(BranchManager emp){
        String branch = emp.getBranch().getBranchName();
        UpdateRolesOfShifts(emp, branch);
    }
    /*
    private static void UpdateRolesOfShifts(BranchManager emp) {
        HashMap<Role, Integer[][]> rolesOfShifts = emp.getBranch().getRolesOfShifts();
        printRoleOfShifts(rolesOfShifts);
        int i = emp.getNetwork().getRoles().size();
        List<Role> ls = emp.getNetwork().getRoles();
        Scanner scanner = new Scanner(System.in);
        String stringRoleToChange = "";
        System.out.println("What role would you like to change? If finish enter 0.");
        stringRoleToChange = scanner.nextLine();
        while (!stringRoleToChange.equals("0")) {
            while (!isRole(stringRoleToChange, emp.getNetwork().getRoles())) {
                System.out.println("Invalid answer, try again: ");
                stringRoleToChange = scanner.nextLine();
            }
            String day = "";
            String numOfEmployees = "";
            System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n");
            day = scanner.nextLine();
            if (!(onlyNumbers(day)))
                System.out.println("Please send a number between 1-7.");
            else {
                int theDay = Integer.parseInt(day);
                if (theDay < 1 || theDay > 7)
                    System.out.println("Please send a number between 1-7.");
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
                            System.out.println("Please enter a number for the desired number of employees in the shift.");
                            numOfEmployees = scanner.nextLine();
                            Role roleToChange = emp.getNetwork().getRole(stringRoleToChange);
                            rolesOfShifts.get(roleToChange)[theShift - 1][theDay - 1] = Integer.parseInt(numOfEmployees);
                            System.out.println("New roles of shifts: ");
                            printRoleOfShifts(rolesOfShifts);
                        }
                    }
                }
            }
            System.out.println("What role would you like to change? Enter the matching number. If finish enter 0.");
            stringRoleToChange = scanner.nextLine();
        }
    }*/
    private static void UpdateRolesOfShifts(Manager emp, String branch) {
        HashMap<Role, Integer[][]> rolesOfShifts = emp.getNetwork().getBranch(branch).getRolesOfShifts();
        printRoleOfShifts(rolesOfShifts);
        int i = emp.getNetwork().getRoles().size();
        List<Role> ls = emp.getNetwork().getRoles();
        Scanner scanner = new Scanner(System.in);
        String stringRoleToChange = "";
        System.out.println("What role would you like to change? If finish enter 0.");
        stringRoleToChange = scanner.nextLine();
        while (!stringRoleToChange.equals("0")) {
            while (!isRole(stringRoleToChange, emp.getNetwork().getRoles())) {
                System.out.println("Invalid answer, try again: ");
                stringRoleToChange = scanner.nextLine();
            }
            String day = "";
            String numOfEmployees = "";
            System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n");
            day = scanner.nextLine();
            if (!(onlyNumbers(day)))
                System.out.println("Please send a number between 1-7.");
            else {
                int theDay = Integer.parseInt(day);
                if (theDay < 1 || theDay > 7)
                    System.out.println("Please send a number between 1-7.");
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
                            System.out.println("Please enter a number for the desired number of employees in the shift.");
                            numOfEmployees = scanner.nextLine();
                            Role roleToChange = emp.getNetwork().getRole(stringRoleToChange);
                            rolesOfShifts.get(roleToChange)[theShift - 1][theDay - 1] = Integer.parseInt(numOfEmployees);
                            System.out.println("New roles of shifts: ");
                            printRoleOfShifts(rolesOfShifts);
                        }
                    }
                }
            }
            System.out.println("What role would you like to change? Enter the matching number. If finish enter 0.");
            stringRoleToChange = scanner.nextLine();
        }
        return;
    }
    private static boolean isRole(String role, List<Role> roleList) {
        for (Role r : roleList) {
            if (role.equals(r.getRoleName())) {
                return true;
            }
        }
        return false;
    }
    private static void printRoleOfShifts(HashMap<Role, Integer[][]> rolesOfShifts){
        for (Role r : rolesOfShifts.keySet()) {
            System.out.println(r.getRoleName());
            shiftPrinter(rolesOfShifts.get(r));
        }
    }
    private static void ShowShiftAvailability(BranchManager emp){
        for(Role r : emp.getNetwork().getRoles()){
            Set<GeneralEmployee>[][] shiftAvi = emp.getBranch().getShiftsAvailability().get(r);
            System.out.println(r.getRoleName()+":");
            ShiftAviPrinter(shiftAvi);
        }
    }
    private static int maxNameLength(Set<GeneralEmployee> gel) {
        int maxNameLength=0;
        for(GeneralEmployee ge : gel)
            maxNameLength=Math.max(maxNameLength,ge.getName().length());
        return maxNameLength;
    }
    private static void ShiftAviPrinter(Set<GeneralEmployee>[][] gel){
        int maxSet=0,maxNameLength=0;
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                maxSet = Math.max(gel[i][j].size(), maxSet);
                maxNameLength=Math.max(maxNameLength,maxNameLength(gel[i][j]));
            }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i);
            for(int space=0;space<maxNameLength;space++)
                System.out.print(" ");

        }
        Iterator<GeneralEmployee>[][] empList = new Iterator[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++) {
                empList[i][j]= gel[i][j].iterator();
            }

        for (int i = 0; i < Network.shifts; i++) {
            System.out.print("\n"+(i+1)+"  ");
            for(int m=0;m<maxSet;m++) {
                for (int j = 0; j < Network.days; j++) {
                    String thisName="";
                    if(empList[i][j].hasNext()) {
                        thisName = empList[i][j].next().getName();
                        System.out.print(thisName);
                    }
                    for(int space=0;space<maxNameLength-thisName.length();space++)
                        System.out.print(" ");
                    System.out.print("  ");
                    }
                System.out.println("  ");
                }
            }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
            for(int space=0;space<maxNameLength;space++)
                System.out.print("-");
        }
        System.out.println();
    }
    private static void UpdateShiftsOfWeek(BranchManager emp) {
        emp.getBranch().addToHistory(emp.getBranch().getEmployeesShifts());
        emp.getBranch().setEmployeesShifts(new HashMap[Network.shifts][Network.days]);
        HashMap<String,Role>[][] ShiftsOfWeek=emp.getBranch().getEmployeesShifts();
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                ShiftsOfWeek[i][j]=new HashMap<>();
        for (Role r : emp.getNetwork().getRoles()) {
            boolean nextRole=false;
            while(!nextRole) {
                Set<GeneralEmployee>[][] shiftAvi = emp.getBranch().getShiftsAvailability().get(r);
                System.out.println(r.getRoleName() + ":");
                System.out.println("Shift Availability:");
                ShiftAviPrinter(shiftAvi);
                System.out.println("Number of needed employees in the role - " + r.getRoleName() + " :");
                shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));

                String day = "";
                System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n8.next Role/exit if last Role");
                Scanner scanner = new Scanner(System.in);
                day = scanner.nextLine();
                if (!(onlyNumbers(day)))
                    System.out.println("Please send a number between 1-8.");
                else {
                    int theDay = Integer.parseInt(day);
                    if (theDay < 1 || theDay > 8)
                        System.out.println("Please send a number between 1-8.");
                    else if (theDay == 8) {
                        nextRole = true;
                    } else {
                        System.out.println("Which shift?\n1.Morning\n2.Evening\n3.Back to day selection");
                        String shift = scanner.nextLine();
                        if (!(onlyNumbers(day)))
                            System.out.println("Not a valid option, select only numbers.");
                        else {
                            int theShift = Integer.parseInt(shift);
                            if (theShift < 1 || theShift > 3)
                                System.out.println("Please send a number between 1-3.");
                            else if (theShift != 3) {
                                if (shiftAvi[theShift - 1][theDay - 1].isEmpty())
                                    System.out.println("No one here");
                                else {
                                    boolean done = false;
                                    while (!done) {
                                        System.out.println("Please enter the number of the employee to add/remove from the list of the selected shift:");
                                        int i = 1;
                                        for (GeneralEmployee ge : shiftAvi[theShift - 1][theDay - 1]) {
                                            System.out.println(i + ". " + ge.getName());
                                            i++;
                                        }
                                        Integer select = -1;
                                        do {
                                            select = 1;
                                            String scanned = scanner.nextLine();
                                            while (!(onlyNumbers(scanned))) {
                                                System.out.print("selected employee not valid or not found.\nselect another: ");
                                                scanned = scanner.nextLine();
                                                if (onlyNumbers(scanned))
                                                    select = Integer.parseInt(scanned);
                                            }
                                        } while ((select > i || select < 1));
                                        i = 1;
                                        GeneralEmployee selectedEmployee = null;
                                        for (GeneralEmployee ge : shiftAvi[theShift - 1][theDay - 1]) {
                                            if (i == select)
                                                selectedEmployee = ge;
                                            i++;
                                        }


                                        HashMap<String, Role> aDay = ShiftsOfWeek[theShift - 1][theDay - 1];
                                        if (aDay.containsKey(selectedEmployee.getName()))
                                            aDay.remove(selectedEmployee.getName());
                                        else
                                            aDay.put(selectedEmployee.getName(), r);


                                        String answer = "";
                                        do {
                                            System.out.print("are you done with this day?(y/n): ");
                                            answer = scanner.nextLine();
                                        } while (!answer.equals("y") & !answer.equals("n"));

                                        if (answer.equals("y"))
                                            done = true;
                                        else {
                                            System.out.println(r.getRoleName() + ":");
                                            System.out.println("Shift Availability:");
                                            ShiftAviPrinter(shiftAvi);
                                            System.out.println("Number of needed employees:");
                                            shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));
                                        }
                                        System.out.println("current shifts:");
                                        ShiftsOfWeekPrinter(emp.getBranch());

                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static int maxStringLength(HashMap<String,Role> hm) {
        int maxStringLength = 0;
        for (String name : hm.keySet())
            maxStringLength = Math.max(maxStringLength, (name+" - "+hm.get(name).getRoleName()).length());
        return maxStringLength;
    }
    private static void ShiftsOfWeekPrinter(Branch branch) {
        HashMap<String,Role>[][] shifts = branch.getEmployeesShifts();
        int maxHash=0,maxStringLength=0;
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                maxHash = Math.max(shifts[i][j].size(), maxHash);
                maxStringLength=Math.max(maxStringLength,maxStringLength(shifts[i][j]));
            }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i);
            for(int space=0;space<maxStringLength;space++)
                System.out.print(" ");
        }

        Iterator<String>[][] NamesList = new Iterator[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++) {
                NamesList[i][j]= shifts[i][j].keySet().iterator();
            }

        for (int i = 0; i < Network.shifts; i++) {
            System.out.print("\n"+(i+1)+"  ");
            for(int m=0;m<maxHash;m++) {
                for (int j = 0; j < Network.days; j++) {
                    String thisName="";
                    if(NamesList[i][j].hasNext()) {
                        thisName = NamesList[i][j].next();
                        System.out.print(thisName+" - "+shifts[i][j].get(thisName).getRoleName());
                    }
                    for(int space=0;space<maxStringLength-thisName.length();space++)
                        System.out.print(" ");
                }
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("--");
            for(int space=0;space<maxStringLength;space++)
                System.out.print("-");
        }
        System.out.println();
    }

    //HR Manager
    private static void HRUpdateBranchRolesOfShifts(HRManager emp){
        Scanner scanner = new Scanner(System.in);
        String branch = "";
        System.out.println("Which branch would you like to update? To return to the main menu enter 0.");
        for (Branch b: emp.getNetwork().getBranchList()){
            System.out.println(b.getBranchName());
        }
        branch = scanner.nextLine();
        while (!branch.equals("0")){
            while (!isBranch(branch, emp)){
                System.out.println("Invalid answer, try again: ");
                branch = scanner.nextLine();
            }
            UpdateRolesOfShifts(emp, branch);
            System.out.println("Which branch would you like to update? To return to the main menu enter 0.");
            for (Branch b: emp.getNetwork().getBranchList()){
                System.out.println(b.getBranchName());
            }
            branch = scanner.nextLine();
        }
    }

    public static boolean isBranch(String branch, HRManager emp){
        for (Branch b: emp.getNetwork().getBranchList()) {
            if (b.getBranchName().equals(branch))
                return true;
        }
        return false;
    }
}


