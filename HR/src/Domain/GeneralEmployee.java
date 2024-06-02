package Domain;

import java.util.*;
public class GeneralEmployee extends Employee {

    private List<Role> roles;
    private boolean isManager;
    private boolean[][] ShiftsRequest;
    private Branch branch;

    public GeneralEmployee(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment , Date endOfEmployment, String partOfJob, int vacationsDays, List<Role> roles, boolean isManager, Branch branch  ){
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
        this.isManager=isManager;
        this.ShiftsRequest=new boolean[Network.shifts][Network.days];
        this.roles=roles;
        for(int i=0;i<ShiftsRequest.length;i++)
            for(int j=0;j<ShiftsRequest[i].length;j++)
                ShiftsRequest[i][j]=false;
        this.branch=branch;
    }
    public GeneralEmployee(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays, List<Role> roles, boolean isManager, Branch branch  ){
        this(ID, name, bankAccountDetails, salary, startOfEmployment, null, partOfJob, vacationsDays,roles,isManager,branch);

    }

    public void copyGeneralEmployee(GeneralEmployee other){
        this.setID(other.getID());
        this.setName(other.getName());
        this.setBankAccountDetails(other.getBankAccountDetails());
        this.setSalary(other.getSalary());
        this.setStartOfEmployment(other.getStartOfEmployment());
        this.setEndOfEmployment(other.getEndOfEmployment());
        this.setPartOfJob(other.getPartOfJob());
        this.setVacationsDays(other.getVacationsDays());
        this.roles = new LinkedList<>();
        this.roles.addAll(other.roles);
        this.isManager=other.isManager;
        this.ShiftsRequest=new boolean[Network.shifts][Network.days];
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                this.ShiftsRequest[i][j]=other.ShiftsRequest[i][j];
        this.branch=other.branch;
    }



    public boolean[][] getShiftsRequest(){return ShiftsRequest;}

    public void setRoles(List<Role> roles) {
        this.roles = roles;
    }



    public void updateShifts(boolean[][] shifts) {
        ShiftsRequest = shifts;
        HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability = branch.getShiftsAvailability();
        for (Role r : roles) {
            Set<GeneralEmployee>[][] shiftsOfTheWeek = shiftsAvailability.get(r);
            for (int i = 0; i < shiftsOfTheWeek.length; i++) {
                for (int j = 0; j < shiftsOfTheWeek[i].length; j++) {
                    if (ShiftsRequest[i][j])
                        shiftsOfTheWeek[i][j].add(this);
                    else
                        shiftsOfTheWeek[i][j].remove(this);
                }
            }
        }
    }
}
