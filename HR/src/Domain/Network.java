package Domain;

import java.util.*;
public class Network {
    public static int days =7;
    public static int shifts = 2;
    private List<Branch> branchList;
    public List<Branch> getBranchList(){return branchList;}

    public void addBranch(Branch branch){
        branchList.add(branch);
    }

    public Network()
    {
     branchList=new LinkedList<Branch>();
    }

    public static boolean CheckAddEmployee(int ID,String name, String bankAccountDetails, int salary,
                                           Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,
                                           boolean isManager,Branch branch)
    {
        //ID - 9 numbers
        //name - only letters and spaces
        // bankAccountDetails - 8-15 numbers only
        //salary - >0
        //dates - check for invalid dates
        //partOfJob - "Full" or "Half"
        //vacationsDays - >0
        int count=0;
        for(int i=ID;i>0;i=i/10, count++);
        if(count!=9)
            return false;

        for(int i=0;i<name.length();i++)
            if(name.charAt(i)!=' ' && !(name.charAt(i)>64 && name.charAt(i)<90) && !(name.charAt(i)>96 && name.charAt(i)<123))
                return false;



        return true;
    }

    
}
