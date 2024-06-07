package Domain;

import java.util.*;
public class HRManager extends Manager{
    public HRManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays,String password) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password,null);
        this.getAccess().add("HRAddGeneralEmployee");
        this.getAccess().add("HRAddBranchManager");
        this.getAccess().add("HRUpdateGeneralEmployeeDetails");
        this.getAccess().add("HRUpdateBranchManagerDetails");
        this.getAccess().add("HRShowGeneralEmployeeDetails");
        this.getAccess().add("HRShowBranchManagerDetails");
        this.getAccess().add("HRUpdateBranchShifts");
        this.getAccess().add("HRUpdateBranchRolesOfShifts");
        this.getAccess().add("HRShowBranchShiftsAvailability");
    }
    public HRManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays,String password) {
        this(ID, name, bankAccountDetails, salary, startOfEmployment,null, partOfJob, vacationsDays,password);
    }
    public Branch addBranch(String name,String location, BranchManager bm){
        Branch branch = new Branch(name,location,this.getNetwork(),bm);
        this.getNetwork().addBranch(branch);
        branch.getEmployeesList().add(this);
        return branch;
    }
    public void setBranchName(BranchManager brm,Branch branch){
        branch.setBranchManager(brm);
    }
    public BranchManager addBranchManager(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment,String partOfJob,int vacationsDays,Branch branch,String password){
        return addBranchManager(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,branch,password);
    }

    public BranchManager addBranchManager(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,Branch branch,String password){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,null,true,branch.getBranchName(),getNetwork()))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            BranchManager bm= new BranchManager(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,branch,password,getNetwork());
            el.add(bm);
            return bm;
        }
        return null;
    }
    public boolean addRoletoNetwork(Role r){
        return getNetwork().addRole(r);
    }



}
