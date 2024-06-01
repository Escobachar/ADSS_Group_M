package Domain;

import java.util.Date;

abstract public class Employee {
    private int ID;
    private String name;
    private String bankAccountDetails;
    private int salary;
    private Date startOfEmployment;
    private Date endOfEmployment;
    private String partOfJob;
    private int vacationsDays;

    public Employee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays  ){
        this.ID=ID;
        this.name=name;
        this.bankAccountDetails=bankAccountDetails;
        this.salary=salary;
        this.startOfEmployment=startOfEmployment;
        this.endOfEmployment=endOfEmployment;
        this.partOfJob=partOfJob;
        this.vacationsDays=vacationsDays;
    }
    public int getID(){return  ID;}
    public Employee getEmployee(){return this;}

    public boolean equals(Object o){
        if(o instanceof Employee){
            Employee other = (Employee) o;
            return ID==other.ID;
        }
        return false;

    }

    public Employee(){}

}
