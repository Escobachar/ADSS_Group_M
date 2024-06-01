package Domain;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BranchManager extends Manager{
    private Branch branch;
    public BranchManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays,Branch branch) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
        this.branch=branch;
    }
    public void addEmployee(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays, List<Role> roles, boolean isManager){
       super.addEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,roles,isManager,branch);
    }
    public void addEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager){
        super.addEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch);
    }
    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager){
        super.UpdateEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch);
    }
    public void updateShiftsOfBranch(){
        super.updateShiftsOfBranch(branch);
    }
    public void updateShiftsOfBranch(HashMap<Role,Integer[][]> rolesOfShifts) {
        super.updateShiftsOfBranch(branch,rolesOfShifts);
    }
}
