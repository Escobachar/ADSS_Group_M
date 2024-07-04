package DataLayer.interfaces;

import Domain.Role;

import java.util.HashMap;
import java.util.List;

public interface HistoryOfEmployeesShiftsDao {
    void create(String branchName, List<HashMap<Integer,Role>[][]> historyEmployeesShifts);
    List<HashMap<Integer,Role>[][]> read(String branchName);
    void update(String branchName, List<HashMap<Integer,Role>[][]> historyEmployeesShifts);
    void delete(String branchName);
}
