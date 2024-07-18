package HR.Presentetion;

import HR.Domain.*;
import HR.Server.Utility;
import Suppliers.PresentationLayer.SuppliersMain;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.*;

public class Login {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        Network network=HR.Server.init.init(false);

        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();
        while(true) {
            Employee emp=null;
            boolean IDFound= true;
            do{
                while (!(Utility.onlyNumbers(ID)) || !IDFound) {
                    System.out.print("ID not valid or not found.\nEnter  ID: ");
                    ID = scanner.nextLine();
                    if(Utility.onlyNumbers(ID))
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

    private static void Menu(Employee emp) {
        boolean login = true;
        System.out.println("Hello " + emp.getName());
        String[] options = new String[emp.getAccess().size() + 1];
        int i = 1;
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
            //Driver
            if (access.contains("ShowAviStoreKeeper")) {
                System.out.println(i + ". Show lists of storekeepers in your shifts");
                options[i] = "ShowAviStoreKeeper";
                i++;
            }
            //BranchManager
            if (access.contains("AddGeneralEmployee")) {
                System.out.println(i + ". Add employee");
                options[i] = "AddGeneralEmployee";
                i++;
            }
            if (access.contains("AddDriver")) {
                System.out.println(i + ". Add driver");
                options[i] = "AddDriver";
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
            if (access.contains("OpenSupplierMenu")) {
                System.out.println(i + ". open supplier menu");
                options[i] = "OpenSupplierMenu";
                i++;
            }

            //HR Manager
            if (access.contains("HRAddGeneralEmployee")) {
                System.out.println(i + ". add general employee");
                options[i] = "HRAddGeneralEmployee";
                i++;
            }
            if (access.contains("HRAssignBranchManager")) {
                System.out.println(i + ". assign branch manager");
                options[i] = "HRAssignBranchManager";
                i++;
            }
            if (access.contains("HRUpdateEmployeeDetails")) {
                System.out.println(i + ". update employee details");
                options[i] = "HRUpdateEmployeeDetails";
                i++;
            }
            if (access.contains("HRShowEmployeeDetails")) {
                System.out.println(i + ". show employee details");
                options[i] = "HRShowEmployeeDetails";
                i++;
            }
            if (access.contains("HRUpdateBranchShiftsOfWeek")) {
                System.out.println(i + ". update branch shifts");
                options[i] = "HRUpdateBranchShiftsOfWeek";
                i++;
            }
            if (access.contains("HRUpdateBranchRolesOfShifts")) {
                System.out.println(i + ". update branch needed roles for shifts");
                options[i] = "HRUpdateBranchRolesOfShifts";
                i++;
            }
            if (access.contains("HRShowBranchShiftsAvailability")) {
                System.out.println(i + ". show branch shifts availability per role");
                options[i] = "HRShowBranchShiftsAvailability";
                i++;
            }
            if (access.contains("HRAddBranch")) {
                System.out.println(i + ". add a new branch");
                options[i] = "HRAddBranch";
                i++;
            }
            if (access.contains("HROpenSupplierMenu")) {
                System.out.println(i + ". open supplier menu");
                options[i] = "HROpenSupplierMenu";
                i++;
            }

            System.out.println(i + ". Logoff");
            options[i] = "Logoff";

            String action = "s";
            int actionIndex;
            do {
                while (!Utility.onlyNumbers(action)) {
                    action = scanner.nextLine();
                    if (!Utility.onlyNumbers(action))
                        System.out.println("Not valid number");
                }
                actionIndex = Integer.parseInt(action);
            } while (actionIndex > options.length || actionIndex < 1);

            switch (options[actionIndex]) {
                case "ShowYourDetails":
                    showYourDetails(emp);
                    break;
                //General Employee
                case "updateShifts":
                    updateShifts((GeneralEmployee) emp);
                    break;
                case "getShifts":
                    getGeneralEmployeeShifts((GeneralEmployee) emp);
                    break;
                case "ShowShiftReq":
                    getShiftsReq((GeneralEmployee) emp);
                    break;
                //Driver
                case "ShowAviStoreKeeper":
                    ShowAviStoreKeeper((Driver) emp);
                    break;
                //Branch Manager
                case "AddGeneralEmployee":
                    AddEmployee((BranchManager) emp, 'g');
                    break;
                case "AddDriver":
                    AddEmployee((BranchManager) emp, 'd');
                    break;
                case "UpdateGeneralEmployeeDetails":
                    UpdateEmployeeDetails((BranchManager) emp);
                    break;
                case "ShowEmployeeDetails":
                    ShowDetailsOnGeneralEmployee((BranchManager) emp);
                    break;
                case "ShowShiftsAvailability":
                    ShowShiftAvailability((BranchManager) emp);
                    break;
                case "UpdateRolesOfShifts":
                    UpdateRolesOfShifts((BranchManager) emp);
                    break;
                case "UpdateBranchShifts":
                    UpdateShiftsOfWeek((BranchManager) emp);
                    break;
                case "OpenSupplierMenu":
                    OpenSupplierMenu((BranchManager) emp);
                    break;
                //HR Manager
                case "HRAddGeneralEmployee":
                    HRAddGeneralEmployee((HRManager) emp);
                    break;
                case "HRAssignBranchManager":
                    HRAssignBranchManager((HRManager) emp);
                    break;
                case "HRUpdateEmployeeDetails":
                    HRUpdateEmployeeDetails((HRManager) emp);
                    break;
                case "HRShowEmployeeDetails":
                    HRShowEmployeeDetails((HRManager) emp);
                    break;
                case "HRUpdateBranchShiftsOfWeek":
                    HRUpdateBranchShiftsOfWeek((HRManager) emp);
                    break;
                case "HRUpdateBranchRolesOfShifts":
                    HRUpdateBranchRolesOfShifts((HRManager) emp);
                    break;
                case "HRShowBranchShiftsAvailability":
                    HRShowBranchShiftsAvailability((HRManager) emp);
                    break;
                case "HRAddBranch":
                    HRaddBranch((HRManager) emp);
                    break;
                case "HROpenSupplierMenu":
                    HROpenSupplierMenu((HRManager) emp);
                    break;
                case "Logoff":
                    login = false;
                    break;

                default:
                    System.out.println("not a valid action\n");
            }
            i = 1;
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
                System.out.println("shift manager");
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
            if(emp instanceof Driver){
                Driver d = (Driver) emp;
                System.out.println("drivers License: " + d.getDriverLicense());
                System.out.print("drivers license types: ");
                String out="";
                for(String s :d.getDriverLicenseTypes())
                    out+=s+", ";
                System.out.println(out.substring(0, out.length()-2));
            }

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
        Utility.ShiftsOfWeekPrinter(emp.getBranch());
    }
    private static void updateShifts(GeneralEmployee emp) {
        String day = "";
        getShiftsReq(emp);
        while (true) {
            System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Back to main menu");
            Scanner scanner = new Scanner(System.in);
            day = scanner.nextLine();
            if (!(Utility.onlyNumbers(day)))
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
                    if (!(Utility.onlyNumbers(day)))
                        System.out.println("Not a valid option, select only numbers.");
                    else {
                        int theShift = Integer.parseInt(shift);
                        if (theShift < 1 || theShift > 3)
                            System.out.println("Please send a number between 1-"+(Network.shifts+1)+".");
                        else if(theShift != 3)
                            emp.updateShift(theShift - 1,theDay - 1);


                            System.out.println("your new shifts request:");
                            getShiftsReq(emp);
                            System.out.println();

                    }
                }
            }
        }
    }
    private static void getShiftsReq(GeneralEmployee emp){
        System.out.println("your current shifts request:");
        GeneralEmployee ge = (GeneralEmployee) emp;
        String[][] shifts = new String[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++)
                if (ge.getShiftsRequest()[i][j])
                    shifts[i][j] = "yes";
                else
                    shifts[i][j] = "no ";
        Utility.shiftPrinter(shifts);
    }
    //Driver
    private static void ShowAviStoreKeeper(Driver emp) {
        HashMap<Integer,Role>[][] employeesShifts = emp.getBranch().getEmployeesShifts();
        List<String>[][] storeKeepersShifts= new List[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++){
                HashMap<Integer,Role> aShift=employeesShifts[i][j];
                if(aShift.containsKey(emp.getID()))
                {
                    storeKeepersShifts[i][j]=new LinkedList<>();
                    for(Integer ID:aShift.keySet())
                        if(aShift.get(ID).getRoleName().equals("storekeeper"))
                            storeKeepersShifts[i][j].add(emp.getBranch().getEmployeeName(ID));
                }
            }
        Utility.storekeepersPrinter(storeKeepersShifts);
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
                if (Utility.onlyNumbers(salaryScan))
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
            if(Utility.onlyNumbers(Id))
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
            if(Utility.onlyNumbers(salaryScan))
                salary=Integer.parseInt(salaryScan);
        }while(!Network.checkSalary(salary));

        String startOfEmp=null;
        do{
            if(startOfEmp!=null)
                System.out.println("Start of employment not valid");
            System.out.println("Start of employment(dd-mm-yyyy): ");
            startOfEmp = scanner.nextLine();
        }while(!Network.checkCreateDate(startOfEmp));

        String endOfEmp=null;
        do{
            if(endOfEmp!=null)
                System.out.println("end of employment not valid");
            System.out.println("end of employment(dd-mm-yyyy) or TBD: ");
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
            if(Utility.onlyNumbers(vacationDaysScan))
                vacationDays=Integer.parseInt(vacationDaysScan);
        }while(!Network.checkVacationsDays(vacationDays));

        System.out.print("Password: ");
        String password = scanner.nextLine();
        if(type=='g' ) {
            System.out.print("Is he a shift manager?(yes/no) ");
            String shiftManager = scanner.nextLine();
            boolean SM = true;
            List<Role> newRL = new ArrayList<>();
            if (shiftManager.equals("no")) {
                SM = false;
                System.out.println("Choose roles of the employee");
                List<Role> RL = null;
                RL = Network.getNetwork().getRoles();
                RL.remove(Network.getNetwork().getRole("shift manager"));
                RL.remove(Network.getNetwork().getRole("driver"));
                int i = 1;
                for (Role r : RL) {
                    System.out.println(i + ". " + r.getRoleName());
                    i++;
                }

                String rolesLine = null;
                do {
                    if(rolesLine!=null)
                        System.out.println("Roles not valid");
                    System.out.print("press all the numbers of the roles you desire then enter: ");
                    rolesLine = scanner.nextLine();
                }while(!Utility.onlyNumbers(rolesLine));

                i = 1;
                for (Role r : RL) {
                    int j = 0;
                    while (rolesLine.length()>j && !(""+i).equals(""+rolesLine.charAt(j))) {
                        j++;
                    }
                    if(rolesLine.length()!=j)
                        newRL.add(r);
                    i++;
                }
            }
            else
                newRL=Network.getNetwork().getRoles();

            Branch branch = ((BranchManager) emp).getBranch();
            emp.addGeneralEmployee(id, name, bankAccount, salary, startOfEmp, endOfEmp, partOfJob, vacationDays, newRL, SM, branch, password);
        }
            else if(type=='d'){
                Integer license=null;
                do {
                    if(license!=null)
                        System.out.println("drivers license not valid");
                    license=-1;
                    System.out.println("drivers license: ");
                    String licenseScan = scanner.nextLine();
                    if(Utility.onlyNumbers(licenseScan))
                        license=Integer.parseInt(licenseScan);
                }while(!Network.CheckDriverLicense(license));

                String typesString=null;
                do {
                    if(typesString!=null)
                        System.out.println("types not valid");
                    System.out.print("drivers license types (if want many type A,B,C...): ");
                    typesString = scanner.nextLine();
                }while(!Network.CheckDriverLicenseTypes(typesString));
                List<String> types = new LinkedList<>();
            String t="";
                for(int i=0;i<typesString.length();i++) {
                    if (typesString.charAt(i)==',') {
                        types.add(t);
                        t="";
                    }
                    else{
                        t+=typesString.charAt(i);
                    }
                }
            types.add(t);
            Branch branch = ((BranchManager) emp).getBranch();
            emp.addDriver(id, name, bankAccount, salary, startOfEmp, endOfEmp, partOfJob, vacationDays, branch, password, license, types);
            }
        else if(type=='m') {
            Branch branch = branchSelect();
            if(branch!=null)
                ((HRManager)emp).addBranchManager(id, name, bankAccount, salary, startOfEmp, endOfEmp, partOfJob, vacationDays,branch,password);

        }
        else if(type=='b') {//case where we start a new branch
            Branch branch = Network.lookForNewBranch(Network.getNetwork());
            if (branch != null)
                ((HRManager) emp).addBranchManager(id, name, bankAccount, salary, startOfEmp, endOfEmp, partOfJob, vacationDays, branch, password);
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
            if(Utility.onlyNumbers(Id))
                id=Integer.parseInt(Id);

        }while(!Network.CheckID(id));
        if(emp instanceof BranchManager) {
            Employee e = ((BranchManager) emp).getBranch().getEmployee(id);
            if((e instanceof BranchManager && e.getID().equals(emp.getID()) )|| (e instanceof HRManager )|| (e instanceof GeneralEmployee && ((GeneralEmployee)e).getBranch()!=((BranchManager) emp).getBranch()))
                System.out.println("access denied");
            else
                changeEmployeeDetails(emp,Network.getNetwork().SearchByID(id));
        }
        else
            changeEmployeeDetails(emp,Network.getNetwork().SearchByID(id));
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
                System.out.print("New start of employment(mm-dd-yyyy): ");
                start = scanner.nextLine();
            } while (!Network.checkCreateDate(start));
            e.setStartOfEmployment(start);
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
                System.out.print("New end of employment(mm-dd-yyyy): ");
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
                e.setEndOfEmployment(end);
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
                    List<Role> RL = Network.getNetwork().getRoles();
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
        if(e instanceof Driver) {
            Driver d = (Driver) e;

            System.out.println("employee's drivers license: "+d.getDriverLicense());
            System.out.print("DO you want to change? y/n ");
            ans = scanner.nextLine();
            if (ans.equals("y")){
                Integer license = null;
                do{
                    if(license!=null)
                        System.out.println("drivers license not valid");
                    System.out.println("drivers license: ");
                    String licenseScan = scanner.nextLine();
                    if(Utility.onlyNumbers(licenseScan))
                        license=Integer.parseInt(licenseScan);
                }while(!Network.CheckDriverLicense(license));
                d.setDriverLicense(license);
            }

            System.out.print("employee's drivers license types: ");
            String out="";
            for(String s :d.getDriverLicenseTypes())
                out+=s+", ";
            System.out.println(out.substring(0, out.length()-2));
            System.out.print("DO you want to change? y/n ");
            ans = scanner.nextLine();
            if (ans.equals("y")){
                String typesString=null;
                do {
                    if(typesString!=null)
                        System.out.println("types not valid");
                    System.out.print("drivers license types (if want many type A,B,C...): ");
                    typesString = scanner.nextLine();
                }while(!Network.CheckDriverLicenseTypes(typesString));
                List<String> types = new LinkedList<>();
                for(int i=0;i<typesString.length();i++)
                    if(i%2==0)
                        types.add(""+typesString.charAt(i));
                d.setDriverLicenseTypes(types);
            }
        }
        else if(emp instanceof HRManager && !(e instanceof HRManager)) {
            System.out.println("employee's branch: "+e.getBranch().getBranchName());
            System.out.print("DO you want to change? y/n ");
            ans = scanner.nextLine();
            if (ans.equals("y")) {
                Branch b = branchSelect();
                e.setBranch(b);
            }
        }
        e.DBUpdateDetails();
        System.out.println("successfully updated");
    }
    private static void UpdateRolesOfShifts(BranchManager emp) {
        HashMap<Role, Integer[][]> rolesOfShifts = emp.getBranch().getRolesOfShifts();
        Utility.printRoleOfShifts(rolesOfShifts);
        int i = Network.getNetwork().getRoles().size();
        List<Role> ls = Network.getNetwork().getRoles();
        Scanner scanner = new Scanner(System.in);
        boolean done=false;
        while(!done) {
            System.out.println("What role would you like to change?");
            Role r = Utility.RolesSelect(Network.getNetwork());
            if(r==null)
                done=true;
            else {
                boolean doneRole = false;
                while (!doneRole) {
                    String day = "";
                    String numOfEmployees = null;
                    System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7. go back");
                    day = scanner.nextLine();
                    if (!(Utility.onlyNumbers(day)))
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
                            if (!(Utility.onlyNumbers(day)))
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
                                        if (Utility.onlyNumbers(numOfEmployees))
                                            numberOfEmployees = Integer.parseInt(numOfEmployees);

                                    } while (numberOfEmployees < 0);

                                    emp.UpdateRolesOfShiftsOfBranch(emp.getBranch(),r,theShift-1,theDay-1,Integer.parseInt(numOfEmployees));

                                    System.out.println("New roles of shifts: ");
                                    Utility.shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static void ShowShiftAvailability(BranchManager emp){
        for(Role r : Network.getNetwork().getRoles()){
            Set<GeneralEmployee>[][] shiftAvi = emp.getBranch().getShiftsAvailability().get(r);
            System.out.println(r.getRoleName()+":");
            Utility.ShiftAviPrinter(shiftAvi);
        }
    }
    private static void UpdateShiftsOfWeek(BranchManager emp) {
        LocalDate today = LocalDate.now(); // Get the current date
        LocalDate sunday = today.with(TemporalAdjusters.previousOrSame(DayOfWeek.SUNDAY)); // Get the date of the most recent Sunday, including today if it is Sunday
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy"); // Define the desired date format
        String dateOfWeek = sunday.format(formatter); // Format the date as a string

        emp.getBranch().addToHistory(dateOfWeek, emp.getBranch().getEmployeesShifts());
        emp.getBranch().setEmployeesShifts(new HashMap[Network.shifts][Network.days]);
        HashMap<Integer,Role>[][] ShiftsOfWeek=emp.getBranch().getEmployeesShifts();
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                ShiftsOfWeek[i][j]=new HashMap<>();

        boolean storekeeperWas=false;
        for (Role r : Network.getNetwork().getRoles()) {
            boolean nextRole=false;
            if(r.getRoleName().equals("storekeeper"))
                if(storekeeperWas)
                    r=Network.getNetwork().getRole("driver");
            if(r.getRoleName().equals("driver"))
                if(!storekeeperWas) {
                    r=Network.getNetwork().getRole("storekeeper");
                    storekeeperWas=true;
                }
            while(!nextRole) {
                Set<GeneralEmployee>[][] shiftAvi = emp.getBranch().getShiftsAvailability().get(r);
                System.out.println(r.getRoleName() + ":");
                System.out.println("Shift Availability:");
                Utility.ShiftAviPrinter(shiftAvi);
                System.out.println("Number of needed employees in the role - " + r.getRoleName() + " :");
                Utility.shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));

                String day = "";
                System.out.println("Which day to change?\n1.Sunday\n2.Monday\n3.Tuesday\n4.Wednesday\n5.Thursday\n6.Friday\n7.Saturday\n8.next Role\n9.exit");
                Scanner scanner = new Scanner(System.in);
                day = scanner.nextLine();
                if (!(Utility.onlyNumbers(day)))
                    System.out.println("Please send a number between 1-9.");
                else {
                    int theDay = Integer.parseInt(day);
                    if (theDay < 1 || theDay > 9)
                        System.out.println("Please send a number between 1-9.");
                    else if (theDay == 8) {
                        if(r.getRoleName().equals("shift manager")) {
                            if (emp.ShiftManagerCheck())
                                nextRole = true;
                            else
                                System.out.println("Please assign a shift manager in everyday ");
                        }
                        else if(r.getRoleName().equals("driver")) {
                            if (emp.DriverCheck())
                                nextRole = true;
                            else
                                System.out.println("not possible to assign a driver in a day without a storekeeper");
                        }
                        else
                            nextRole = true;

                    } else if (theDay == 9)
                        return;
                    else{
                        System.out.println("Which shift?\n1.Morning\n2.Evening\n3.Back to day selection");
                        String shift = scanner.nextLine();
                        if (!(Utility.onlyNumbers(day)))
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
                                            while (!(Utility.onlyNumbers(scanned))) {
                                                System.out.print("selected employee not valid or not found.\nselect another: ");
                                                scanned = scanner.nextLine();
                                                if (Utility.onlyNumbers(scanned))
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

                                        emp.updateShift(selectedEmployee,r,emp.getBranch(),theShift - 1,theDay - 1);

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
                                            Utility.ShiftAviPrinter(shiftAvi);
                                            System.out.println("Number of needed employees:");
                                            Utility.shiftPrinter(emp.getBranch().getRolesOfShifts().get(r));
                                        }
                                        System.out.println("current shifts:");
                                        Utility.ShiftsOfWeekPrinter(emp.getBranch());
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
    private static void OpenSupplierMenu(BranchManager emp ){
        String branchName= emp.getBranch().getBranchName();
        SuppliersMain.suppliersMain(branchName);
    }

    //HR Manager
    private static Branch branchSelect(){
        Scanner scanner = new Scanner(System.in);
        String SelectedBranch = "s";
        System.out.println("Select branch:");
        Branch[] branches = new Branch[Network.getNetwork().getBranchList().size()+1];
        int i=1;
        for (Branch b: Network.getNetwork().getBranchList()){
            System.out.println(i+". "+b.getBranchName());
            branches[i]=b;
            i++;
        }
        System.out.println(i+". return back");

        int SelectedBranchIndex;
        do{
            while(!Utility.onlyNumbers(SelectedBranch)) {
                SelectedBranch = scanner.nextLine();
                if (!Utility.onlyNumbers(SelectedBranch))
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
        Branch b=branchSelect();
        if(b!=null)
            UpdateRolesOfShifts(b.getBranchManager());
    }
    private static void HRAddGeneralEmployee(HRManager emp) {
        Branch b=branchSelect();
        if(b!=null)
            AddEmployee(b.getBranchManager(),'g');
    }
    private static void HRShowBranchShiftsAvailability(HRManager emp) {
        Branch b=branchSelect();
        if(b!=null)
            ShowShiftAvailability(b.getBranchManager());
    }
    private static void HRUpdateBranchShiftsOfWeek(HRManager emp) {
        Branch b=branchSelect();
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
                if (Utility.onlyNumbers(salaryScan))
                    id = Integer.parseInt(salaryScan);
            } while (!Network.CheckID(id));
            Employee e=Network.getNetwork().SearchByID(id);
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
        Branch b=branchSelect();
        if(b!=null)
            AddEmployee(emp,'m');
    }
    private static void HRaddBranch(HRManager emp){
        System.out.println("Adding new branch:");
        Scanner scanner = new Scanner(System.in);

        String name=null;
        do {
            if(name!=null)
                System.out.println("name not valid");
            System.out.println("Name: ");
            name = scanner.nextLine();
        }while(!Network.CheckName(name));

        String location=null;
        do {
            if(location!=null)
                System.out.println("location not valid");
            System.out.println("location: ");
            location = scanner.nextLine();
        }while(!Network.CheckName(location));

        emp.addBranch(name,location,null);
        System.out.println("add a new Manager to the branch:");
        AddEmployee(emp,'b');
        System.out.println("branch added successfully");

    }
    private static void HROpenSupplierMenu(HRManager emp ){
        Branch b=branchSelect();
        if(b!=null)
            OpenSupplierMenu(b.getBranchManager());
    }
}



