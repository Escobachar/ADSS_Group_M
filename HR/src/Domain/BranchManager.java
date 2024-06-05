package Domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BranchManager extends Manager{
    private Branch branch;
    public BranchManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays,Branch branch,String password) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
        this.branch=branch;
        branch.setBranchManager(this);
        this.getAccess().add("AddGeneralEmployee");
        this.getAccess().add("UpdateGeneralEmployeeDetails");
        this.getAccess().add("UpdateGeneralEmployeeRoles");
        this.getAccess().add("ShowEmployeeDetails");
        this.getAccess().add("UpdateBranchShifts");
        this.getAccess().add("UpdateRolesOfShifts");
        this.getAccess().add("ShowShiftsAvailability");
    }
    public Branch getBranch() {return branch;}

    public void addGeneralEmployee(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays, List<Role> roles, boolean isManager, String password){
       super.addGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager, String password){
        super.addGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager, String password){
        super.UpdateEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void updateShiftsOfBranch(){
        super.updateShiftsOfBranch(branch);
    }
    public void updateRolesOfShiftsOfBranch(HashMap<Role,Integer[][]> rolesOfShifts) {
        super.updateRolesOfShiftsOfBranch(branch,rolesOfShifts);
    }
}
