import java.util.*;

public class Branch {
    private String location;
    private List<generalEmployee> EmployeesList;
    private List<Pair<Integer,boolean[][]>> EmployeesShifts;
    private List<Pair<Role,Integer>[][]> RolesOfShifts;
    private List<List<Pair<Integer,boolean[][]>>> HistoryEmployeesShifts;
    private List<Pair<Role,Set<generalEmployee>[][]>> shiftsAvailability;

    public void setShiftsAvailability(List<Pair<Role,Set<generalEmployee>[][]>> shiftsAvailability){this.shiftsAvailability=shiftsAvailability;}
    public List<Pair<Role,Set<generalEmployee>[][]>> getShiftsAvailability(){return this.shiftsAvailability;}

    public void setEmployeesList(List<generalEmployee> EmployeesList){this.EmployeesList=EmployeesList;}
    public List<generalEmployee> getEmployeesListList(){return EmployeesList;}


}
