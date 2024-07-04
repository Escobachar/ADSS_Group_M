package DataLayer.interfaces;

import  Domain.*;

import java.util.HashMap;

public interface RoleOfShiftsDao {
    void create(String branchName, HashMap<Role,Integer[][]> roleOfShifts);
    HashMap<Role,Integer[][]> read(String branchName);
    void update(String branchName, HashMap<Role,Integer[][]> newRoleOfShifts);
    void delete(String branchName);
}
