import java.util.Date;

public class Employee {
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

}
