package Domain;

import java.util.*;
public class HRManager extends Manager{
    public HRManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays,String password) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
    }
    public HRManager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays,String password) {
        this(ID, name, bankAccountDetails, salary, startOfEmployment,null, partOfJob, vacationsDays,password);
    }
    public Branch addBranch(String name,String location, BranchManager bm){
        Branch branch = new Branch(name,location,this.getNetwork(),bm);
        this.getNetwork().addBranch(branch);
        return branch;
    }
    public void setBranchName(BranchManager brm,Branch branch){
        branch.setBranchManager(brm);
    }

}
