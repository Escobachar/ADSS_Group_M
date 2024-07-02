package DataLayer;

import  Domain.*;

import java.util.HashMap;

public interface RoleOfShiftsDao {
    void create(String branchName, HashMap<Role,Integer[][]> hashMap);
    HashMap<Role,Integer[][]> read(String branchName);
    void update();
    void delete();
}
