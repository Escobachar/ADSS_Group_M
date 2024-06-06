
package Domain;
import java.util.*;

public class Branch {
    private Network network;
    private String branchName;
    private String location;
    private BranchManager branchManager;
    private List<Employee> employeesList;
    private Set<Integer>[][] employeesShifts;
    private HashMap<Role,Integer[][]> rolesOfShifts;
    private List<Set<Integer>[][]> historyEmployeesShifts;
    private HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability;

    //creating new branch with a manager
    public Branch(String name,String location,Network network,BranchManager brm){
        this.branchName=name;
        this.location=location;
        this.network=network;
        branchManager=brm;
        employeesList=new ArrayList<Employee>();
        employeesShifts=new Set[Network.shifts][Network.days];
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                employeesShifts[i][j]=new HashSet<>();
        rolesOfShifts = new HashMap<Role,Integer[][]>();
        shiftsAvailability = new HashMap<Role,Set<GeneralEmployee>[][]>();
        for(Role r: network.getRoles()){
            rolesOfShifts.put(r,new Integer[Network.days][Network.days]);
            shiftsAvailability.put(r,new HashSet[Network.days][Network.days]);
            Set<GeneralEmployee>[][] set=shiftsAvailability.get(r);
            for(int i=0;i<Network.shifts;i++)
                for(int j=0;j<Network.days;j++)
                    set=new HashSet[Network.days][Network.days];
        }
        historyEmployeesShifts = new LinkedList<Set<Integer>[][]>();
    }


    //creating new branch without manager(inserting him manually after creating it)
    public Branch(String name,String location,Network network){
        this(name,location,network,null);
    }

    public void setBranchManager(BranchManager brm){
        this.branchManager=brm;
    }
    public String getBranchName(){return branchName;}

    public Set<Role> getRoles(){
        return rolesOfShifts.keySet();
    }

    public void setShiftsAvailability(HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability){this.shiftsAvailability=shiftsAvailability;}
    public HashMap<Role,Integer[][]> getRolesOfShifts(){return this.rolesOfShifts;}

    public HashMap<Role,Set<GeneralEmployee>[][]> getShiftsAvailability(){return this.shiftsAvailability;}

    public void setEmployeesShifts(Set<Integer>[][] employeesShifts){
        this.employeesShifts=employeesShifts;
    }

    public void setEmployeesList(List<Employee> EmployeesList){this.employeesList=EmployeesList;}
    public List<Employee> getEmployeesList(){return employeesList;}

    public void setRolesOfShifts(HashMap<Role, Integer[][]> rolesOfShifts) {
        this.rolesOfShifts = rolesOfShifts;
    }
    public Employee getEmployee(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return e;
        return null;
    }

    public GeneralEmployee SearchGeneralEmployee(int id) {
        for (Employee e : employeesList)
            if ((e.getID() == id)&&(e instanceof GeneralEmployee)) return (GeneralEmployee)e;
        return null;
    }

    public boolean isExist(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return true;
        return false;
    }

    public boolean isExist(String name) {
        for (Employee e : employeesList)
            if (e.getName().equals(name)) return true;
        return false;
    }

    public String getEmployeeName(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return e.getName();
        return null;
    }
    public String getEmployeeBankAccountDetails(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return e.getBankAccountDetails();
        return null;
    }
    public Integer getEmployeeSalary(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return e.getSalary();
        return null;
    }
    public Set<Integer>[][] getEmployeesShifts(){return employeesShifts;}
}
