package HR.DataLayer.interfaces;

import HR.Domain.Role;

import java.util.HashMap;

public interface EmployeeShiftsDao {
    void create(String branchName, HashMap<Integer,Role>[][] employeeShifts);
    HashMap<Integer,Role>[][]  read(String branchName);
    void update(String branchName, HashMap<Integer,Role>[][] employeeShifts);
    void update(boolean add,String branchName, Integer id, Role r, int day, int shift);
    void delete(String branchName);

}
