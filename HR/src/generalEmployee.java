import java.util.*;
public class generalEmployee extends Employee {
    private List<Role> roles;
    private boolean isManager;
    private boolean[][] ShiftsRequest;
    private Branch branch;

    public generalEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch  ){
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
        this.isManager=isManager;
        this.ShiftsRequest=new boolean[7][2];
        for(int i=0;i<ShiftsRequest.length;i++)
            for(int j=0;j<ShiftsRequest[i].length;j++)
                ShiftsRequest[i][j]=false;
        this.branch=branch;
    }


    public void updateShifts(boolean[][] shifts) {
        ShiftsRequest = shifts;
        List<Pair<Role, Set<generalEmployee>[][]>> shiftsAvailability = branch.getShiftsAvailability();
        for (Role r : roles) {
            for (Pair p : shiftsAvailability) {
                if (p.first.equals(r)) {
                    for (int i = 0; i < ShiftsRequest.length; i++) {
                        for (int j = 0; j < ShiftsRequest[i].length; j++) {
                            if (ShiftsRequest[i][j])
                                ((Set<generalEmployee>[][]) p.second)[i][j].add(this);
                            else
                                ((Set<generalEmployee>[][]) p.second)[i][j].remove(this);
                        }
                    }
                }
            }
            branch.setShiftsAvailability(shiftsAvailability);
        }
    }
}
