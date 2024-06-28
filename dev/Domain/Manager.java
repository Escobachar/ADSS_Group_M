package Domain;

import java.util.*;

abstract public class Manager extends Employee{
    private Network network;

    public Manager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment , Date endOfEmployment, String partOfJob, int vacationsDays,String password,Network network  ) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
        this.network=network;
    }
    public void addGeneralEmployee(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays,
                                   List<Role> roles, boolean isManager, Branch branch, String password){
        addGeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, null, partOfJob, vacationsDays, roles, isManager, branch, password);
    }
    public GeneralEmployee addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,
                                              int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch.getBranchName(),network))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            GeneralEmployee ge= new GeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch,password);
            el.add(ge);
            return ge;
        }
        return null;
    }
    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password  ) {
        if ((Network.checkGeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch.getBranchName(),network))&& network.SearchByID(ID)!=null) {
            List<Employee> gel = branch.getEmployeesList();
            branch.SearchGeneralEmployee(ID).copyGeneralEmployee(new GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch, password));
        }
    }
    public void SetRolesOfShiftsOfBranch(Branch branch, HashMap<Role,Integer[][]> rolesOfShifts) {
        branch.setRolesOfShifts(rolesOfShifts);
    }

    public Network getNetwork(){return network;}
    public void setNetwork(Network network){this.network=network;}

    public void updateShift(GeneralEmployee selectedEmployee, Role r,Branch b,int shift,int day) {
        HashMap<Integer, Role> theShift = b.getEmployeesShifts()[shift][day];
        if (theShift.containsKey(selectedEmployee.getName()))
            theShift.remove(selectedEmployee.getName());
        else {
            int maxEmployees = b.getRolesOfShifts().get(r)[shift][day];
            if (theShift.keySet().size() == maxEmployees)
                System.out.print("max employees in this day for this role reached.");
            else
                if(selectedEmployee.getShiftsRequest()[shift][day])//check if the selected employee checked in his request for this shift
                    if(!selectedEmployee.getRoles().contains(r))//check if the employee has the Role that we looked at to change in the shift
                        System.out.print("error: Employee isn't the requested role to put in the shift.");
                    else
                        theShift.put(selectedEmployee.getID(), r);
                else
                    System.out.print("error: Employee didn't put his request here.");
        }

    }

    public void UpdateRolesOfShiftsOfBranch(Branch branch, Role r, int theShift, int theDay, int numOfEmployees) {
        if(!r.getRoleName().equals("Shift Manager"))
            branch.getRolesOfShifts().get(r)[theShift][theDay]=numOfEmployees;
        else
            System.out.print("error: cant change shift manager number of needed employees.");
    }
    public boolean ShiftManagerCheck(Branch branch) {
        Role shiftManager=branch.getBranchManager().getNetwork().getRole("Shift Manager");
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                if(!branch.getEmployeesShifts()[i][j].containsValue(shiftManager))
                    return false;
        return true;
    }

    public boolean DriverCheck(Branch branch) {
        Role driver=branch.getBranchManager().getNetwork().getRole("driver");
        Role storekeeper=branch.getBranchManager().getNetwork().getRole("storekeeper");
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                if(branch.getEmployeesShifts()[i][j].containsValue(driver))
                    if(!branch.getEmployeesShifts()[i][j].containsValue(storekeeper))
                        return false;
        return true;
    }
}
