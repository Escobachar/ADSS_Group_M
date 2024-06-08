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
        String[] options = new String[emp.getAccess().size()+1];
        int i=1;
        Scanner scanner = new Scanner(System.in);
        while (login) {
            System.out.println("\nactions menu:\n");
            Set<String> access = emp.getAccess();
            System.out.println(i + ". Show your details");
            options[i] = "ShowYourDetails";
            i++;
            //GeneralEmployee
            if (access.contains("updateShifts")) {
                System.out.println(i + ". Update your shifts");
                options[i] = "updateShifts";
                i++;
            }
            if (access.contains("getShifts")) {
                System.out.println(i + ". Show this week shifts");
                options[i] = "getShifts";
                i++;
            }
            if (access.contains("ShowShiftReq")) {
                System.out.println(i + ". Show your shifts requests");
                options[i] = "ShowShiftReq";
                i++;
            }
            //BranchManager
            if (access.contains("AddGeneralEmployee")) {
                System.out.println(i + ". Add employee");
                options[i] = "AddGeneralEmployee";
                i++;
            }
            if (access.contains("UpdateBranchShifts")) {//this
                System.out.println(i + ". Update shifts of the week");
                options[i] = "UpdateBranchShifts";
                i++;
            }
            if (access.contains("UpdateGeneralEmployeeDetails")) {
                System.out.println(i + ". Update details of the employee");
                options[i] = "UpdateGeneralEmployeeDetails";
                i++;
            }
            if (access.contains("ShowEmployeeDetails")) {
                System.out.println(i + ". Show details on an employee");
                options[i] = "ShowEmployeeDetails";
                i++;
            }
            if (access.contains("UpdateRolesOfShifts")) {
                System.out.println(i + ". Update roles for next shifts");
                options[i] = "UpdateRolesOfShifts";
                i++;
            }
            if (access.contains("ShowShiftsAvailability")) {
                System.out.println(i + ". Show shifts availability per role");
                options[i] = "ShowShiftsAvailability";
                i++;
            }

            //HR Manager
            if (access.contains("HRAddGeneralEmployee")) {
                System.out.println(i + ". add general employee");
                options[i] = "HRAddGeneralEmployee";
                i++;
            }
            if (access.contains("HRAssignBranchManager")) {
                System.out.println(i+ ". assign branch manager");
                options[i] ="HRAssignBranchManager";
                i++;
            }
            if (access.contains("HRUpdateEmployeeDetails")) {
                System.out.println(i+ ". update employee details");
                options[i] ="HRUpdateEmployeeDetails";
                i++;
            }
            if (access.contains("HRShowEmployeeDetails")) {
                System.out.println(i+ ". show employee details");
                options[i] ="HRShowEmployeeDetails";
                i++;
            }
            if (access.contains("HRUpdateBranchShiftsOfWeek")) {
                System.out.println(i+ ". update branch shifts");
                options[i] ="HRUpdateBranchShiftsOfWeek";
                i++;
            }
            if (access.contains("HRUpdateBranchRolesOfShifts")) {
                System.out.println(i+ ". update branch needed roles for shifts");
                options[i] ="HRUpdateBranchRolesOfShifts";
                i++;
            }
            if (access.contains("HRShowBranchShiftsAvailability")) {
                System.out.println(i+ ". show branch shifts availability per role");
                options[i] ="HRShowBranchShiftsAvailability";
                i++;
            }

            System.out.println(i + ". Logoff");
            options[i] = "Logoff";

            String action = "s";
            int actionIndex;
            do{
                while(!onlyNumbers(action)) {
                    action = scanner.nextLine();
                    if (!onlyNumbers(action))
                        System.out.println("Not valid number");
                }
                actionIndex=Integer.parseInt(action);
            }while(actionIndex>options.length || actionIndex<1);

            switch (options[actionIndex]) {
                case "ShowYourDetails":
                    showYourDetails(emp);
                    break;
                //General Employee
                case "updateShifts":
                    updateShifts((GeneralEmployee)emp);
                    break;
                case "getShifts":
                    getGeneralEmployeeShifts((GeneralEmployee)emp);
                    break;
                case "ShowShiftReq":
                    getShiftsReq((GeneralEmployee)emp);
                    break;
                //Branch Manager
                case "AddGeneralEmployee":
                    AddEmployee((BranchManager) emp,'g');
                    break;
                case "UpdateGeneralEmployeeDetails":
                    UpdateEmployeeDetails((BranchManager)emp);
                    break;
                case "ShowEmployeeDetails":
                    ShowDetailsOnGeneralEmployee((BranchManager) emp);
                    break;
                case "ShowShiftsAvailability":
                    ShowShiftAvailability((BranchManager)emp);
                    break;
                case "UpdateRolesOfShifts":
                    UpdateRolesOfShifts((BranchManager)emp);
                    break;
                case "UpdateBranchShifts":
                    UpdateShiftsOfWeek((BranchManager)emp);
                    break;
                //HR Manager
                case "HRAddGeneralEmployee":
                    HRAddGeneralEmployee((HRManager)emp);
                    break;
                case "HRAssignBranchManager":
                    HRAssignBranchManager((HRManager)emp);
                    break;
                case "HRUpdateEmployeeDetails":
                    HRUpdateEmployeeDetails((HRManager)emp);
                    break;
                case "HRShowEmployeeDetails":
                    HRShowEmployeeDetails((HRManager)emp);
                    break;
                case "HRUpdateBranchShiftsOfWeek":
                    HRUpdateBranchShiftsOfWeek((HRManager)emp);
                    break;
                case "HRUpdateBranchRolesOfShifts":
                    HRUpdateBranchRolesOfShifts((HRManager)emp);
                    break;
                case "HRShowBranchShiftsAvailability":
                    HRShowBranchShiftsAvailability((HRManager)emp);
                    break;
                case "Logoff":
                    login = false;
                    break;

                default:
                    System.out.println("not a valid action\n");
            }
            i=1;
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
                        System.out.println(r.getRoleName());
                }
            }
            System.out.println("Shifts requests for next week:");
            getShiftsReq(ge);
            System.out.println("Branch: " + ge.getBranch().getBranchName());
        }
        else if(emp instanceof BranchManager){
            BranchManager bm = (BranchManager) emp;
            System.out.println("Branch: " + bm.getBranch().getBranchName());
            System.out.println("Role: Branch Manager");
        }
        else if(emp instanceof HRManager){
            System.out.println("Role: HR Manager");
        }
    }
    //General Employee
    private static void getGeneralEmployeeShifts(GeneralEmployee emp) {
        ShiftsOfWeekPrinter(emp.getBranch());
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
            System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Back to main menu");
            Scanner scanner = new Scanner(System.in);
            day = scanner.nextLine();
            if (!(onlyNumbers(day)))
                System.out.println("Please send a number between 1-"+(Network.days+1)+".");
            else {
                int theDay = Integer.parseInt(day);
                if (theDay < 1 || theDay > 7)
                    System.out.println("Please send a number between 1-"+(Network.days+1)+".");
                else if (theDay == Network.days+1)
                    return;
                else {
                    System.out.println("Which shift?\n1.Morning\n2.Evening\n3.Back to day selection");
                    String shift = scanner.nextLine();
                    if (!(onlyNumbers(day)))
                        System.out.println("Not a valid option, select only numbers.");
                    else {
                        int theShift = Integer.parseInt(shift);
                        if (theShift < 1 || theShift > 3)
                            System.out.println("Please send a number between 1-"+(Network.shifts+1)+".");
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
    private static void getShiftsReq(GeneralEmployee emp){
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
    private static Role RolesSelect(Network network){
        Scanner scanner = new Scanner(System.in);
        String SelectedRole = "s";
        System.out.println("Select role:");
        Role[] roles = new Role[network.getRoles().size()+1];
        int i=1;
        for (Role r: network.getRoles()){
            if(!r.getRoleName().equals("Shift Manager")) {
                System.out.println(i + ". " + r.getRoleName());
                roles[i] = r;
                i++;
            }
        }
        System.out.println(i+". return back");

        int SelectedRoleIndex;
        do{
            while(!onlyNumbers(SelectedRole)) {
                SelectedRole = scanner.nextLine();
                if (!onlyNumbers(SelectedRole))
                    System.out.println("Not valid number");
            }
            SelectedRoleIndex=Integer.parseInt(SelectedRole);
        }while(SelectedRoleIndex>roles.length+1 || SelectedRoleIndex<1);

        if (SelectedRoleIndex!=i)
            return roles[SelectedRoleIndex];
        else
            return null;

    }
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
    private static void AddEmployee(Manager emp,char type) {
        System.out.println("Adding new employee:");
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
        if(type=='g') {
            System.out.println("Is he a shift manager?: ");
            String shiftManager = scanner.nextLine();
            boolean SM = true;
            List<Role> newRL = new ArrayList<>();
            if (shiftManager.equals("no")) {
                SM = false;
                System.out.println("Choose roles of the employee,\npress all the numbers of the roles you desire then enter: ");
                List<Role> RL = null;
                RL = ((Manager) emp).getNetwork().getRoles();
                int i = 1;
                for (Role r : RL) {
                    System.out.println(i + ". " + r.getRoleName());
                    i++;
                }
                String rolesLine = scanner.nextLine();
                int j = 0;
                i = 1;
                for (Role r : RL) {
                    if (i == rolesLine.charAt(j)) {
                        newRL.add(r);
                        j++;
                    }
                    i++;
                    if (j > rolesLine.length())
                        break;
                }
            }
            Branch branch = ((BranchManager)emp).getBranch();
            emp.addGeneralEmployee(id, name, bankAccount, salary, new Date(startOfEmp), new Date(endOfEmp), partOfJob, vacationDays, newRL, SM, branch, password);
        }
        else if(type=='m') {
            Branch branch = branchSelect(emp.getNetwork());
            if(branch!=null)
                ((HRManager)emp).addBranchManager(id, name, bankAccount, salary, new Date(startOfEmp), new Date(endOfEmp), partOfJob, vacationDays,branch,password);

        }
    }
    private static void UpdateEmployeeDetails(Manager emp) {
        System.out.println("Update employee:");
        Scanner scanner = new Scanner(System.in);
        Integer id=null;
        String Id=null;
        boolean goodid=false;
        do {
            if(Id!=null)
                System.out.println("ID not valid");
            System.out.println("ID of employee: ");
            Id = scanner.nextLine();
            if(onlyNumbers(Id))
                id=Integer.parseInt(Id);

        }while(!Network.CheckID(id));
        if(emp instanceof BranchManager) {
            Employee e = ((BranchManager) emp).getBranch().getEmployee(id);
            if((e instanceof BranchManager && e.getID().equals(emp.getID()) )|| (e instanceof HRManager )|| (e instanceof GeneralEmployee && ((GeneralEmployee)e).getBranch()!=((BranchManager) emp).getBranch()))
                System.out.println("access denied");
            else
                changeEmployeeDetails(emp,emp.getNetwork().SearchByID(id));
        }
        else
            changeEmployeeDetails(emp,emp.getNetwork().SearchByID(id));
    }
    private static void changeEmployeeDetails(Manager emp,Employee e){
        Scanner scanner = new Scanner(System.in);
        System.out.println("employee's name: "+e.getName());
        System.out.print("DO you want to change? y/n ");
        String ans = scanner.nextLine();
        if (ans.equals("y")){
            String name = null;
            do{
                if(name!=null)
                    System.out.println("name not valid");
                System.out.print("New name: ");
                name = scanner.nextLine();
            } while (!Network.CheckName(name));
            e.setName(name);
        }

        System.out.println("employee's bank account details: "+e.getBankAccountDetails());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String bank = null;
            do{
                if(bank!=null)
                    System.out.println("bank account not valid");
                System.out.print("New bank details: ");
                bank = scanner.nextLine();
            } while (!Network.CheckBankAccountDetails(bank));
            e.setBankAccountDetails(bank);
        }

        System.out.println("employee's salary: "+e.getSalary());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String salary = null;
            do{
                if(salary!=null)
                    System.out.println("salary not valid");
                System.out.print("New salary: ");
                salary = scanner.nextLine();
            } while (!Network.checkSalary(salary));
            e.setSalary(Integer.parseInt(salary));
        }

        System.out.println("employee's start of employment: "+e.getStartOfEmployment());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String start = null;
            do{
                if(start!=null)
                    System.out.println("start of employment not valid");
                System.out.print("New start of employment: ");
                start = scanner.nextLine();
            } while (!Network.checkCreateDate(start));
            e.setStartOfEmployment(new Date(start));
        }

        System.out.println("employee's end of employment: "+e.getEndOfEmployment());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        boolean good=false;
        if (ans.equals("y")){
            String end = null;
            do{
                if(end!=null)
                    System.out.println("end of employment not valid");
                System.out.print("New end of employment: ");
                end = scanner.nextLine();
                if(end.equals("TBD")) {
                    good=true;
                }
                else
                    good=Network.checkCreateDate(end);
            } while (!good);
            if(end.equals("TBD"))
                e.setEndOfEmployment(null);
            else
                e.setEndOfEmployment(new Date(end));
        }

        System.out.println("employee's part of job: "+e.getPartOfJob());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String part = null;
            do{
                if(part!=null)
                    System.out.println("part of job not valid");
                System.out.print("New part of job: ");
                part = scanner.nextLine();
            } while (!Network.checkPartOfJob(part));
            e.setPartOfJob(part);
        }

        System.out.println("employee's number vacations days: "+e.getVacationsDays());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            String vacations = null;
            do{
                if(vacations!=null)
                    System.out.println("number of vacations days not valid");
                System.out.print("New number of vacations days: ");
                vacations = scanner.nextLine();
            } while (!Network.checkVacationsDays(vacations));
            e.setVacationsDays(Integer.parseInt(vacations));
        }

        System.out.println("employee's password: "+e.getPassword());
        System.out.print("DO you want to change? y/n ");
        ans = scanner.nextLine();
        if (ans.equals("y")){
            System.out.print("New password: ");
            String password = scanner.nextLine();
            e.setPassword(password);
        }

        if(e instanceof GeneralEmployee) {
            GeneralEmployee ge = (GeneralEmployee) e;
            if (ge.isManager()) {
                System.out.print("This employee is a shift manager.\n would you like to change it? y/n ");
                ans = scanner.nextLine();
                if (ans.equals("y")) {
                    ge.setManager(false);
                    System.out.print("Choose roles of the employee,\npress all the numbers of the roles you desire then enter: ");
                    List<Role> RL = ((Manager) emp).getNetwork().getRoles();
                    List<Role> newRL = null;
                    int i = 1;
                    for (Role r : RL) {
                        System.out.println(i + ". " + r.getRoleName());
                        i++;
                    }
                    String rolesLine = scanner.nextLine();
                    int j = 0;
                    i = 1;
                    for (Role r : RL) {
                        if (i == rolesLine.charAt(j)) {
                            newRL.add(r);
                            j++;
                        }
                        i++;
                        if (j > rolesLine.length())
                            break;
                    }
                }
            } else {
                System.out.print("This employee is not a shift manager.\n would you like to change it? y/n ");
                ans = scanner.nextLine();
                if (ans.equals("y")) {
                    ge.setManager(true);
                }
            }
        }
            if(emp instanceof HRManager && !(e instanceof HRManager)) {
                System.out.println("employee's branch: "+e.getBranch().getBranchName());
                System.out.print("DO you want to change? y/n ");
                ans = scanner.nextLine();
                if (ans.equals("y")) {
                    Branch b = branchSelect(emp.getNetwork());
                    e.setBranch(b);
                }
            }

        System.out.println("successfully updated");
    }
    private static void UpdateRolesOfShifts(BranchManager emp) {
        HashMap<Role, Integer[][]> rolesOfShifts = emp.getBranch().getRolesOfShifts();
        printRoleOfShifts(rolesOfShifts);
        int i = emp.getNetwork().getRoles().size();
        List<Role> ls = emp.getNetwork().getRoles();
        Scanner scanner = new Scanner(System.in);
        boolean done=false;
        while(!done) {
            System.out.println("What role would you like to change?");
            Role r = RolesSelect(emp.getNetwork());
            if(r==null)
                done=true;
            else {
                boolean doneRole = false;
                while (!doneRole) {
                    String day = "";
                    String numOfEmployees = null;
                    System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7. go back");
                    day = scanner.nextLine();
                    if (!(onlyNumbers(day)))
                        System.out.println("Please send a number between 1-7.");
                    else {
                        int theDay = Integer.parseInt(day);
                        if (theDay < 1 || theDay > 7)
                            System.out.println("Please send a number between 1-7.");
                        else if (theDay == 7)
                            doneRole = true;
                        else{
                            System.out.println("Which shift?\n1.Morning\n2.Evening\n3.Back to day selection");
                            String shift = scanner.nextLine();
                            if (!(onlyNumbers(day)))
                                System.out.println("Not a valid option, select only numbers.");
                            else {
                                int theShift = Integer.parseInt(shift);
                                if (theShift < 1 || theShift > 3)
                                    System.out.println("Please send a number between 1-3.");
                                else {
                                    int numberOfEmployees = -1;
                                    do {
                                        if (numOfEmployees != null)
                                            System.out.println("number of employees not valid");
                                        System.out.print("Please enter a number for the desired number of employees in the shift: ");
                                        numOfEmployees = scanner.nextLine();
                                        if (onlyNumbers(numOfEmployees))
                                            numberOfEmployees = Integer.parseInt(numOfEmployees);

                                    } while (numberOfEmployees < 0);

                                    rolesOfShifts.get(r)[theShift - 1][theDay - 1] = Integer.parseInt(numOfEmployees);
                                    System.out.println("New roles of shifts: ");
                                    shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));
                                }
                            }
                        }
                    }
                }
            }
        }
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
                System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n8.next Role\n9.exit");
                Scanner scanner = new Scanner(System.in);
                day = scanner.nextLine();
                if (!(onlyNumbers(day)))
                    System.out.println("Please send a number between 1-8.");
                else {
                    int theDay = Integer.parseInt(day);
                    if (theDay < 1 || theDay > 8)
                        System.out.println("Please send a number between 1-8.");
                    else if (theDay == 8) {
                        if(ShiftManagerCheck(emp.getBranch()))
                            nextRole = true;
                        else
                            System.out.println("Please put assign in everyday a shift manager");
                    } else if (theDay == 9)
                        return;
                    else{
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
                                        else {
                                            int maxEmployees=emp.getBranch().rolesOfShifts().get(r)[theShift - 1][theDay - 1];
                                            aDay.keySet().size();
                                            if(aDay.keySet().size()==maxEmployees)
                                                System.out.print("max employees in this day for this role reached.");
                                            else
                                                aDay.put(selectedEmployee.getName(), r);
                                        }
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
    private static boolean ShiftManagerCheck(Branch branch) {
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                Role shiftManager=branch.getBranchManager().getNetwork().getRole("Shift Manager");
                if(!branch.getEmployeesShifts()[i][j].containsValue(shiftManager))
                    return false;
            }
        return true;
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
    private static Branch branchSelect(Network network){
        Scanner scanner = new Scanner(System.in);
        String SelectedBranch = "s";
        System.out.println("Select branch:");
        Branch[] branches = new Branch[network.getBranchList().size()+1];
        int i=1;
        for (Branch b: network.getBranchList()){
            System.out.println(i+". "+b.getBranchName());
            branches[i]=b;
            i++;
        }
        System.out.println(i+". return back");

        int SelectedBranchIndex;
        do{
            while(!onlyNumbers(SelectedBranch)) {
                SelectedBranch = scanner.nextLine();
                if (!onlyNumbers(SelectedBranch))
                    System.out.println("Not valid number");
            }
            SelectedBranchIndex=Integer.parseInt(SelectedBranch);
        }while(SelectedBranchIndex>branches.length+1 || SelectedBranchIndex<1);

        if (SelectedBranchIndex!=i)
            return branches[SelectedBranchIndex];
        else
            return null;

    }
    private static void HRUpdateBranchRolesOfShifts(HRManager emp){
        Branch b=branchSelect(emp.getNetwork());
        if(b!=null)
            UpdateRolesOfShifts(b.getBranchManager());
    }
    private static void HRAddGeneralEmployee(HRManager emp) {
        Branch b=branchSelect(emp.getNetwork());
        if(b!=null)
            AddEmployee(b.getBranchManager(),'g');
    }
    private static void HRShowBranchShiftsAvailability(HRManager emp) {
        Branch b=branchSelect(emp.getNetwork());
        if(b!=null)
            ShowShiftAvailability(b.getBranchManager());
    }
    private static void HRUpdateBranchShiftsOfWeek(HRManager emp) {
        Branch b=branchSelect(emp.getNetwork());
        if(b!=null)
            UpdateShiftsOfWeek(b.getBranchManager());
    }
    private static void HRShowEmployeeDetails(HRManager emp) {
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
            Employee e=emp.getNetwork().SearchByID(id);
            if(e!=null) {
                showYourDetails(e);
                return ;
            }
        }
    }
    private static void HRUpdateEmployeeDetails(HRManager emp) {
        UpdateEmployeeDetails(emp);
    }
    private static void HRAssignBranchManager(HRManager emp) {
        Branch b=branchSelect(emp.getNetwork());
        if(b!=null)
            AddEmployee(emp,'m');

    }
}


