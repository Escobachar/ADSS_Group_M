package Domain;

import java.util.*;
public class Role {
    private String roleName;
    private List<String> access;

    public boolean equals (Object o){
        if(o instanceof Role)
        {
            Role r=(Role)o;
            return r.roleName.equals(this.roleName);
        }
        return false;
    }
    public Role(String name,List<String> access,Branch branch){
        roleName=name;
        this.access=access;
        for(Role r:branch.getShiftsAvailability().keySet()){
            if(r.roleName.equals(name))
                return ;
        }
        HashMap<Role,Integer[][]> rolesOfShifts =  branch.getRolesOfShifts();
        rolesOfShifts.put(this,new Integer[Network.shifts][Network.days]);
        HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability=branch.getShiftsAvailability();
        shiftsAvailability.put(this,new Set[Network.shifts][Network.days]);
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                shiftsAvailability.get(this)[i][j] = new HashSet<GeneralEmployee>();
    }


}
