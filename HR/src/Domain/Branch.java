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
    private List<HashMap<Integer,boolean[][]>> historyEmployeesShifts;
    private HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability;

    public Branch(String name,String location,Network network){
        this.branchName=name;
        this.location=location;
        this.network=network;
        branchManager=new BranchManager(0,"no Branch Manager",0,0,new Date(0,0,0),null,"half",0,this);
        employeesList=new ArrayList<Employee>();
        employeesShifts=new Set[Network.shifts][Network.days];
        rolesOfShifts = new HashMap<Role,Integer[][]>();
        historyEmployeesShifts = new LinkedList<HashMap<Integer,boolean[][]>>();
        shiftsAvailability = new HashMap<Role,Set<GeneralEmployee>[][]>();
        network.addBranch(this);
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

    public boolean isExist(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return true;
        return false;
    }

}
