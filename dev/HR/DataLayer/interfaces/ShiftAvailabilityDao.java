package HR.DataLayer.interfaces;

import HR.Domain.GeneralEmployee;
import HR.Domain.Role;

import java.util.HashMap;
import java.util.Set;

public interface ShiftAvailabilityDao {
    void create(String branchName, HashMap<Role, Set<GeneralEmployee>[][]> shiftAvailability);
    HashMap<Role,Set<GeneralEmployee>[][]>  read(String branchName);
    void update(String branchName, HashMap<Role,Set<GeneralEmployee>[][]> shiftAvailability);
    void delete(String branchName);
    void update(boolean add,String BranchName, Role r, int ID, int day, int shift);
}
