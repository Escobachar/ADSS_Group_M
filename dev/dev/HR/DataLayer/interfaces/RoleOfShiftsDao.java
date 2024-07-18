package HR.DataLayer.interfaces;

import  HR.Domain.*;

import java.util.HashMap;

public interface RoleOfShiftsDao {
    void create(String branchName, HashMap<Role,Integer[][]> roleOfShifts);
    HashMap<Role,Integer[][]> read(String branchName);
    void update(String branchName, HashMap<Role,Integer[][]> newRoleOfShifts);
    void update(String branchName, String roleName, int day, int shift, int numOfEmployees);
    void delete(String branchName);

}
