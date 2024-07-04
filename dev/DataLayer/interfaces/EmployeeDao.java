package DataLayer.interfaces;

import Domain.Employee;

import java.util.List;

public interface EmployeeDao {
    void create(Employee emp);
    Employee read(int ID);
    void update(Employee emp);
    void delete(int ID);
    List<Employee> readAll(String branchName);

}
