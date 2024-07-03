package DataLayer;

import Domain.GeneralEmployee;
import Domain.Role;

import java.util.HashMap;
import java.util.Set;

public interface ShiftAvailabilityDao {
    void create(String branchName, HashMap<Role, Set<GeneralEmployee>[][]> shiftAvailability);
    HashMap<Role,Integer[][]> read(String branchName);
    void update(String branchName, HashMap<Role,Set<GeneralEmployee>[][]> shiftAvailability);
    void delete(String branchName);
}
