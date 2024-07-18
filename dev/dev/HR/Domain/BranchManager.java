package HR.Domain;

import HR.DataLayer.IMP.BranchManagerDao;
import HR.DataLayer.interfaces.EmployeeDao;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class BranchManager extends Manager{
    private Branch branch;
    private EmployeeDao branchManagerDao= new BranchManagerDao();
    public BranchManager(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String endOfEmployment, String partOfJob, int vacationsDays,Branch branch,String password) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
        this.branch=branch;
        if(branch!=null) {
            branch.setBranchManager(this);
            this.getAccess().add("AddGeneralEmployee");
            this.getAccess().add("AddDriver");
            this.getAccess().add("UpdateGeneralEmployeeDetails");
            this.getAccess().add("ShowEmployeeDetails");
            this.getAccess().add("UpdateBranchShifts");
            this.getAccess().add("UpdateRolesOfShifts");
            this.getAccess().add("ShowShiftsAvailability");
            this.getAccess().add("OpenSupplierMenu");

            branch.getEmployeesList().add(this);
        }
    }
    public Branch getBranch() {return branch;}
    public void setBranch(Branch branch) {this.branch=branch;}
    public void addGeneralEmployee(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String partOfJob, int vacationsDays, List<Role> roles, boolean isManager, String password){
       super.addGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void addDriver(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String partOfJob, int vacationsDays,String password, Integer driverLicense,List<String> driverLicenseTypes){
        super.addDriver(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,branch, password, driverLicense,driverLicenseTypes);
    }
    public void addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager, String password){
        super.addGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager, String password){
        super.UpdateEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch, password);
    }
    public void SetRolesOfShiftsOfBranch(HashMap<Role,Integer[][]> rolesOfShifts) {
        super.SetRolesOfShiftsOfBranch(branch,rolesOfShifts);
    }
    public void UpdateRolesOfShiftsOfBranch(Branch branch, Role r, int theShift, int theDay, int numOfEmployees) {
        super.UpdateRolesOfShiftsOfBranch(this.branch,r,theShift,theDay,numOfEmployees);
    }
    public boolean ShiftManagerCheck() {
        return super.ShiftManagerCheck(branch);
    }
    public boolean DriverCheck() {
        return super.DriverCheck(branch);
    }

    public void DBUpdateDetails(){
        branchManagerDao.update(this);
    }
}
