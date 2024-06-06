package Domain;

import java.util.*;
public class Network {
    public static int days =7;
    public static int shifts = 2;
    private List<Branch> branchList;
    private HRManager HRmanager;
    private List<Role> roles;//list of all roles in the network, programmers my add more if customer need
    public List<Branch> getBranchList(){return branchList;}

    public Network(HRManager HRmanager) {
        this.HRmanager = HRmanager;
        HRmanager.setNetwork(this);
        branchList=new LinkedList<Branch>();
        roles = new LinkedList<>();
        addRole(new Role("Shift Manager",new ArrayList<>()));
    }

    public boolean addRole(Role r){
        if(roles.contains(r))
            return false;
        for(Branch branch:branchList) {
            HashMap<Role, Integer[][]> rolesOfShifts = branch.getRolesOfShifts();
            rolesOfShifts.put(r, new Integer[Network.shifts][Network.days]);
            HashMap<Role, Set<GeneralEmployee>[][]> shiftsAvailability = branch.getShiftsAvailability();
            shiftsAvailability.put(r, new Set[Network.shifts][Network.days]);
            for (int i = 0; i < Network.shifts; i++)
                for (int j = 0; j < Network.days; j++)
                    shiftsAvailability.get(r)[i][j] = new HashSet<GeneralEmployee>();
        }
        roles.add(r);
        return true;
    }

    public List<Role> getRoles(){return this.roles; }
    public void addBranch(Branch branch){
        branchList.add(branch);
    }
    public Employee SearchByID(int ID){
        Employee emp=null;
        for(Branch br:branchList) {
            emp = br.getEmployee(ID);
            if(emp!=null)
                return emp;
        }
        return emp;
    }

    public Branch getBranch(String name){
        for(Branch b:branchList) {
            if(b.getBranchName().equals(name))
                return b;
        }
        return null;
    }

    public static boolean checkGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,
                                               Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,
                                               boolean isManager,String branch, Network network){

        return (CheckID(ID) & CheckName(name) & CheckBankAccountDetails(bankAccountDetails) & checkSalary(salary) & checkPartOfJob(partOfJob)
                & checkVacationsDays(vacationsDays) & checkRoles(roles) & network.checkBranch(branch));
    }
    public static boolean CheckID(Integer ID){
        //ID - 9 numbers
        if(ID==null)
            return false;
        int count=0;
        for(int i=ID;i>0;i=i/10, count++);
        return count == 9;
    }
    public static boolean CheckName(String name){
        //name - only letters and spaces
        for(int i=0;i<name.length();i++) {
            if (name.charAt(i) != ' ' && !(name.charAt(i) > 64 && name.charAt(i) < 90) && !(name.charAt(i) > 96 && name.charAt(i) < 123))
                return false;
        }
        return true;
    }
    public static boolean CheckBankAccountDetails(String bankAccountDetails) {
        // bankAccountDetails - 8-15 numbers only
        if (bankAccountDetails.length() < 8 || bankAccountDetails.length() > 15)
            return false;
        for (int i = 0; i < bankAccountDetails.length(); i++) {
            if (!(bankAccountDetails.charAt(i) > 47 && bankAccountDetails.charAt(i) < 58))
                return false;
        }
        return true;
    }
    public static boolean checkSalary(int salary){
        //salary - >0
        return salary > 0;
    }
    public static boolean checkPartOfJob(String partOfJob){
        //partOfJob - "Full" or "Half"
        return partOfJob.equals("Half") || partOfJob.equals("Full");
    }
    public static boolean checkVacationsDays(int vacationsDays){
        //vacationsDays - >0
        return vacationsDays >= 0;
    }
    public static boolean checkRoles(List<Role> roles){
        //roles - not empty
        return !roles.isEmpty();
     }
    public static boolean checkCreateDate(String createDate){
        if(createDate.length()!=10)
            return false;
        for (int i = 0; i < createDate.length(); i++) {
            if ((!(createDate.charAt(i) > 47 && createDate.charAt(i) < 58)) || createDate.charAt(i) != 32)
                return false;
            if ((i == 2 || i==5) && createDate.charAt(i) != 32)
                return false;
        }
        return true;
    }
    public boolean checkBranch(String branch){
        for(Branch br:branchList) {
            if(br.getBranchName().equals(branch))
                return true;
        }
        return false;
    }
    private static boolean onlyNumbers(String toCheck) {
        for (int i = 0; i < toCheck.length(); i++)
            if (!(toCheck.charAt(i) > 47 && toCheck.charAt(i) < 58))
                return false;
        return true;
    }

    public static boolean checkSalary(String salary){
        //salary - >0 & all numbers
        if (onlyNumbers(salary)){
            return checkSalary(Integer.parseInt(salary));
        }
        return false;
    }

    public static boolean checkVacationsDays(String vacationDays){
        //vacationsDays - >0 & all numbers
        if(onlyNumbers(vacationDays)){
            return checkVacationsDays(Integer.parseInt(vacationDays));
        }
        return false;
    }
    
}
