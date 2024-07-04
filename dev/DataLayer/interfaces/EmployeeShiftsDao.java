package DataLayer.interfaces;

import Domain.Role;

import java.util.HashMap;

public interface EmployeeShiftsDao {
    void create(String branchName, HashMap<Integer,Role>[][] employeeShifts);
    HashMap<Integer,Role>[][]  read(String branchName);
    void update(String branchName, HashMap<Integer,Role>[][] employeeShifts);
    void delete(String branchName);
}
