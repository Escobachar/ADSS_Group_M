package DataLayer.interfaces;

import Domain.Role;

import java.util.HashMap;
import java.util.List;

public interface HistoryOfEmployeesShiftsDao {
    void create(String branchName, HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts);
    HashMap<String, HashMap<Integer,Role>[][]> read(String branchName);
    void update(String branchName, HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts);
    void delete(String branchName);
}
