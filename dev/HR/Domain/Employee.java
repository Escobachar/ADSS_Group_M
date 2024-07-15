package Domain;

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

abstract public class Employee {
    private Set<String> access;
    private int ID;
    private String name;
    private String bankAccountDetails;
    private int salary;
    private String startOfEmployment;
    private String endOfEmployment;
    private String partOfJob;
    private int vacationsDays;
    private String password;

    public Employee(int ID,String name, String bankAccountDetails, int salary,String startOfEmployment ,String endOfEmployment,String partOfJob,int vacationsDays,String password ){
        this.ID=ID;
        this.name=name;
        this.bankAccountDetails=bankAccountDetails;
        this.salary=salary;
        this.startOfEmployment=startOfEmployment;
        this.endOfEmployment=endOfEmployment;
        this.partOfJob=partOfJob;
        this.vacationsDays=vacationsDays;
        access=new HashSet<>();
        access.add("ShowYourDetails");
        access.add("Logoff");
        this.password=password;
    }
    public Employee(){}
    public Set<String> getAccess(){
        return access;
    }
    public void setAccess(Set<String> access) {
        this.access=access;
    }
    public String getPassword(){
        return password;
    }

    public abstract void setBranch(Branch branch);
    public abstract Branch getBranch();
    public Integer getID(){return  ID;}
    public String getName(){return name;}
    public String getBankAccountDetails(){return bankAccountDetails;}
    public Integer getSalary(){return salary;}
    public String getEndOfEmployment(){return endOfEmployment;}
    public String getPartOfJob(){return partOfJob;}
    public Integer getVacationsDays(){return vacationsDays;}
    public Employee getEmployee(){return this;}

    public boolean equals(Object o){
        if(o instanceof Employee){
            Employee other = (Employee) o;
            return ID==other.ID;
        }
        return false;
    }

    public void setID(Integer id) {
        this.ID=id;
    }

    public void setName(String name) {
        this.name= name;
    }

    public void setBankAccountDetails(String bankAccountDetails) {
        this.bankAccountDetails= bankAccountDetails;
    }

    public void setSalary(Integer salary) {
        this.salary=salary;
    }

    public String getStartOfEmployment() {
        return startOfEmployment;
    }

    public void setStartOfEmployment(String startOfEmployment) {
        this.startOfEmployment=startOfEmployment;
    }
    public void setEndOfEmployment(String endOfEmployment) {
            this.endOfEmployment=endOfEmployment;
    }

    public void setPartOfJob(String partOfJob) {
        this.partOfJob= partOfJob;
    }

    public void setVacationsDays(Integer vacationsDays) {
        this.vacationsDays=vacationsDays;
    }
    public void setPassword(String password) {this.password=password;}

    public abstract void DBUpdateDetails();

}
