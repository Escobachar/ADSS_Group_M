package Domain;

import DataLayer.IMP.*;
import DataLayer.interfaces.*;


import java.net.NetworkInterface;
import java.util.*;
public class HRManager extends Manager{
    private EmployeeDao hRManagerDao=new HRManagerDao();
    private BranchDao branchDao=new BranchDaoImp();

    public HRManager(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String endOfEmployment, String partOfJob, int vacationsDays,String password) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
        this.getAccess().add("HRAddGeneralEmployee");
        this.getAccess().add("HRAssignBranchManager");
        this.getAccess().add("HRUpdateEmployeeDetails");
        this.getAccess().add("HRShowEmployeeDetails");
        this.getAccess().add("HRUpdateBranchShiftsOfWeek");
        this.getAccess().add("HRUpdateBranchRolesOfShifts");
        this.getAccess().add("HRShowBranchShiftsAvailability");
        this.getAccess().add("HRAddBranch");
        hRManagerDao.create(this);
    }
    public HRManager(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String partOfJob, int vacationsDays,String password) {
        this(ID, name, bankAccountDetails, salary, startOfEmployment,null, partOfJob, vacationsDays,password);
    }
    public Branch addBranch(String name,String location, BranchManager bm){
        Branch branch = new Branch(name,location,bm);
        Network.getNetwork().addBranch(branch);
        branchDao.create(branch);
        return branch;
    }
    public void setBranchName(BranchManager brm,Branch branch){
        branch.setBranchManager(brm);
    }
    public BranchManager addBranchManager(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment , String endOfEmployment, String partOfJob, int vacationsDays, Branch branch, String password){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,null,true,branch.getBranchName()))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            if(branch.getBranchManager()!=null) {
                branch.getBranchManager().setBranch(Network.getNetwork().getEmptyBranch());
                branchManagerDao.update(branch.getBranchManager());
            }
            BranchManager bm= new BranchManager(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,branch,password);
            el.add(bm);
            branchManagerDao.create(bm);
            return bm;
        }
        return null;
    }
    public void setBranch(Branch branch) {}
    public Branch getBranch() {return null;}

    public void DBUpdateDetails(){
        hRManagerDao.update(this);
    }
}
