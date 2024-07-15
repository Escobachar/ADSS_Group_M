package Domain;

import DataLayer.IMP.*;
import DataLayer.interfaces.*;

import java.util.*;

abstract public class Manager extends Employee{
    protected EmployeeDao branchManagerDao = new BranchManagerDao();
    protected EmployeeDao generalEmployeeDao = new GeneralEmployeeDao();
    protected EmployeeDao driverDao = new DriverDao();
    protected RoleOfShiftsDao roleOfShiftsDao = new RoleOfShiftsDaoImp();
    protected EmployeeShiftsDao employeeShiftsDao = new EmployeeShiftsDaoImp();

    public Manager(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment , String endOfEmployment, String partOfJob, int vacationsDays,String password  ) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
    }
    public void addGeneralEmployee(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String partOfJob, int vacationsDays,
                                   List<Role> roles, boolean isManager, Branch branch, String password){
        addGeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, null, partOfJob, vacationsDays, roles, isManager, branch, password);
    }
    public GeneralEmployee addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,
                                              int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch.getBranchName()))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            GeneralEmployee ge= new GeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch,password);
            el.add(ge);
            generalEmployeeDao.create(ge);
            return ge;
        }
        return null;
    }

    public Driver addDriver (int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,
                                              int vacationsDays,Branch branch,String password, Integer driverLicense, List<String> driverLicenseTypes){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkDriver(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,driverLicense,branch.getBranchName()))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            Driver d= new Driver(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,branch, password, driverLicense, driverLicenseTypes);
            el.add(d);
            driverDao.create(d);
            return d;
        }
        return null;
    }

    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password  ) {
        if ((Network.checkGeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch.getBranchName()))&& Network.getNetwork().SearchByID(ID)!=null) {
            List<Employee> gel = branch.getEmployeesList();
            branch.SearchGeneralEmployee(ID).copyGeneralEmployee(new GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch, password));
        }
    }

    public void SetRolesOfShiftsOfBranch(Branch branch, HashMap<Role,Integer[][]> rolesOfShifts) {
        branch.setRolesOfShifts(rolesOfShifts);
    }

    public void updateShift(GeneralEmployee ge, Role r,Branch b,int shift,int day) {
        HashMap<Integer, Role> theShift = b.getEmployeesShifts()[shift][day];
        if (theShift.containsKey(ge.getName())) {
            theShift.remove(ge.getName());
            employeeShiftsDao.update(false,b.getBranchName(),ge.getID(),r,day,shift);
        }
        else {
            int maxEmployees = b.getRolesOfShifts().get(r)[shift][day];
            if (theShift.keySet().size() == maxEmployees)
                System.out.print("max employees in this day for this role reached.");
            else
                if(ge.getShiftsRequest()[shift][day])//check if the selected employee checked in his request for this shift
                    if(!ge.getRoles().contains(r))//check if the employee has the Role that we looked at to change in the shift
                        System.out.print("error: Employee isn't the requested role to put in the shift.");
                    else {
                        theShift.put(ge.getID(), r);
                        employeeShiftsDao.update(true,b.getBranchName(),ge.getID(),r,day,shift);
                    }
                else
                    System.out.print("error: Employee didn't put his request here.");
        }

    }

    public void UpdateRolesOfShiftsOfBranch(Branch branch, Role r, int theShift, int theDay, int numOfEmployees) {
        if(!r.getRoleName().equals("Shift Manager")) {
            branch.getRolesOfShifts().get(r)[theShift][theDay] = numOfEmployees;
            roleOfShiftsDao.update(branch.getBranchName(),r.getRoleName(),theDay,theShift,numOfEmployees);
        }
        else
            System.out.print("error: cant change shift manager number of needed employees.");
    }

    public boolean ShiftManagerCheck(Branch branch) {
        Role shiftManager=Network.getNetwork().getRole("Shift Manager");
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                if(!branch.getEmployeesShifts()[i][j].containsValue(shiftManager))
                    return false;
        return true;
    }

    public boolean DriverCheck(Branch branch) {
        Role driver=Network.getNetwork().getRole("driver");
        Role storekeeper=Network.getNetwork().getRole("storekeeper");
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                if(branch.getEmployeesShifts()[i][j].containsValue(driver))
                    if(!branch.getEmployeesShifts()[i][j].containsValue(storekeeper))
                        return false;
        return true;
    }
}
