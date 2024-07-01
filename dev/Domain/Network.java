package Domain;

import com.sun.source.tree.NewArrayTree;

import java.util.*;
public class Network {
    private static Network network;

    public static int days =6;
    public static int shifts = 2;
    private List<Branch> branchList;
    private HRManager HRmanager;
    private List<Role> roles;//list of all roles in the network, programmers my add more if customer need
    public List<Branch> getBranchList(){return branchList;}


    private Network(HRManager HRmanager)
    {
        this.HRmanager = HRmanager;
        HRmanager.setNetwork(this);
        branchList=new LinkedList<Branch>();
        roles = new LinkedList<>();
        addRole(new Role("shift manager",new ArrayList<>()));
    }
    public static Network createNewNetwork(HRManager HRmanager){
        network = new Network(HRmanager);
        return network;
    }


    public static Network getNetwork()
    {
        return network;
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
    public List<Role> getRoles(){return this.roles;}
    public Role getRole(String role){
        for (Role r : roles) {
            if (role.equals(r.getRoleName())) {
                return r;
            }
        }
        return null;
    }
    public Branch getBranch(String name){
        for(Branch b:branchList) {
            if(b.getBranchName().equals(name))
                return b;
        }
        return null;
    }

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
    public boolean isBranchExist(String branch){
        for(Branch br:branchList) {
            if(br.getBranchName().equals(branch))
                return true;
        }
        return false;
    }

    public static boolean checkGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,
                                               Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,
                                               boolean isManager,String branch, Network network){

        return (CheckID(ID) & CheckName(name) & CheckBankAccountDetails(bankAccountDetails) & checkSalary(salary) & checkPartOfJob(partOfJob)
                & checkVacationsDays(vacationsDays) & network.isBranchExist(branch));
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
        return salary > 30;
    }
    public static boolean checkPartOfJob(String partOfJob){
        //partOfJob - "Full" or "Half"
        return partOfJob.equals("Half") || partOfJob.equals("Full");
    }
    public static boolean checkVacationsDays(int vacationsDays){
        //vacationsDays - >0
        return vacationsDays >= 0;
    }
    public static boolean checkCreateDate(String createDate){
        boolean good=true;
        try{
            Date testing = new Date(createDate);
        }catch(Exception e){
            good=false;
        }
        return good;
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
    public static Branch lookForNewBranch(Network network){
        for(Branch b: network.branchList)
            if(b.getBranchManager()==null)
                return b;
        return null;
    }
}
