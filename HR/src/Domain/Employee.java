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
    public Employee(){}
    public Integer getID(){return  ID;}
    public String getName(){return name;}
    public String getBankAccountDetails(){return bankAccountDetails;}
    public Integer getSalary(){return salary;}
    public Date getEndOfEmployment(){return endOfEmployment;}
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

    public Date getStartOfEmployment() {
        return startOfEmployment;
    }

    protected void setStartOfEmployment(Date startOfEmployment) {
        this.startOfEmployment=new Date(startOfEmployment.getTime());
    }
    protected void setEndOfEmployment(Date endOfEmployment) {
        if(endOfEmployment==null)
            this.endOfEmployment=null;
        else
            this.endOfEmployment=new Date(endOfEmployment.getTime());
    }

    protected void setPartOfJob(String partOfJob) {
        this.partOfJob= partOfJob;
    }

    protected void setVacationsDays(Integer vacationsDays) {
        this.vacationsDays=vacationsDays;
    }
}
