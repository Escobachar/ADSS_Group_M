import java.util.*;
public class HRManager extends Employee{
    private List<String> access;
    private Network network;
    public HRManager(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays  ) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
    }

    public void addEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch  ){
        List<generalEmployee> gel=branch.getEmployeesListList();
        gel.add(new generalEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch));
        branch.setEmployeesList(gel);
    }
}
