package DataLayer;

import Domain.Employee;

public interface EmployeeDao {
    void create(Employee emp);
    Employee read(int ID);
    void update(Employee emp);
    void delete(int ID);

}
