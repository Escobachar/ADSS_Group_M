
package Domain;
import DataLayer.*;

import java.util.*;

public class Branch {
    private String branchName;
    private String location;
    private BranchManager branchManager;
    private List<Employee> employeesList;
    private HashMap<Integer,Role>[][] employeesShifts;
    private HashMap<Role,Integer[][]> rolesOfShifts;
    private List<HashMap<Integer,Role>[][]> historyEmployeesShifts;
    private HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability;

    public BranchRepository branchRepository= new BranchRepositoryImp();
    public EmployeeDao generalEmployeeDao = new GeneralEmployeeDao();
    public EmployeeDao driverDao = new DriverDao();
    public EmployeeDao BMDao = new BranchManagerDao();

    //creating new branch with a manager
    public Branch(String name,String location,BranchManager brm){
        this.branchName=name;
        this.location=location;
        branchManager=brm;
        employeesList=new ArrayList<Employee>();
        employeesShifts=new HashMap[Network.shifts][Network.days];
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                employeesShifts[i][j]=new HashMap<>();
        rolesOfShifts = new HashMap<Role,Integer[][]>();
        shiftsAvailability = new HashMap<Role,Set<GeneralEmployee>[][]>();
        for(Role r: Network.getNetwork().getRoles()){
            rolesOfShifts.put(r,new Integer[Network.shifts][Network.days]);
            shiftsAvailability.put(r,new HashSet[Network.shifts][Network.days]);
            Set<GeneralEmployee>[][] setList=shiftsAvailability.get(r);
            for(int i=0;i<Network.shifts;i++) {
                rolesOfShifts.get(r)[i]=new Integer[Network.days];
                for (int j = 0; j < Network.days; j++) {
                    setList[i][j] = new HashSet<>();
                    rolesOfShifts.get(r)[i][j]=0;
                }
            }

        }
        for(int i=0;i<Network.shifts;i++)
            for (int j = 0; j < Network.days; j++)
                rolesOfShifts.get(Network.getNetwork().getRole("shift manager"))[i][j]=1;


        historyEmployeesShifts = new LinkedList<>();
    }
    //creating new branch without manager(inserting him manually after creating it)
    public Branch(String name,String location){
        this(name,location,null);
    }
    public BranchManager getBranchManager(){return branchManager;}
    public void setBranchManager(BranchManager brm){
        this.branchManager=brm;
    }
    public String getBranchName(){return branchName;}
    public List<Role> getRoles(){
        return Network.getNetwork().getRoles();
    }

    public void setShiftsAvailability(HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability){this.shiftsAvailability=shiftsAvailability;}
    public HashMap<Role,Integer[][]> getRolesOfShifts(){return this.rolesOfShifts;}
    public HashMap<Role,Set<GeneralEmployee>[][]> getShiftsAvailability(){return this.shiftsAvailability;}
    public void setEmployeesShifts(HashMap<Integer,Role>[][] employeesShifts){
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
        if(branchManager.getID()==id)
            return branchManager;
        return null;
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
    public HashMap<Integer,Role>[][] getEmployeesShifts(){return employeesShifts;}

    public GeneralEmployee SearchGeneralEmployee(int id) {
        for (Employee e : employeesList)
            if ((e.getID() == id)&&(e instanceof GeneralEmployee)) return (GeneralEmployee)e;
        return null;
    }
    public boolean isEmployeeExist(int id) {
        for (Employee e : employeesList)
            if (e.getID() == id) return true;
        return false;
    }
    public boolean isEmployeeExist(String name) {
        for (Employee e : employeesList)
            if (e.getName().equals(name)) return true;
        return false;
    }
    public void addToHistory(HashMap<Integer,Role>[][] employeesShifts) {
        historyEmployeesShifts.add(employeesShifts);
    }

    //db

    public void DBaddGeneralEmployee(GeneralEmployee ge) {
        generalEmployeeDao.create(ge);
    }
    public void DBaddDriver(Driver d) {
        driverDao.create(d);
    }
    public void DBaddBranchManager(BranchManager bm) {
        BMDao.create(bm);
    }

    public String getLocation() {
        return location;
    }

    public void setHistoryEmployeesShifts(List<HashMap<Integer,Role>[][]> historyEmployeesShifts) {
        this.historyEmployeesShifts = historyEmployeesShifts;
    }
    public List<HashMap<Integer,Role>[][]> getHistoryEmployeesShifts(){return historyEmployeesShifts;}
}
