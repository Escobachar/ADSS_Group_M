package DataLayer;

import Domain.Branch;
import Domain.Employee;
import java.util.stream.*;
import java.util.List;

public class BranchRepositoryImp implements BranchRepository {
    private BranchDao branchDao=new BranchDaoImp();
    private EmployeeDao branchManagerDao = new BranchManagerDao();
    private EmployeeDao HRManagerDao = new HRManagerDao();
    private EmployeeDao driverDao = new DriverDao();
    private EmployeeDao generalEmployeeDao = new GeneralEmployeeDao();

    @Override
    public Branch get(String branchName) {
        Branch branch = null;
        branch = branchDao.read(branchName);
        List<Employee> employeeList = Stream.concat(driverDao.readAll(branchName).stream(), generalEmployeeDao.readAll(branchName).stream()).toList();

    }

    @Override
    public void create(Branch branch) {

    }

    @Override
    public void update(Branch branch) {
//todo
    }

    @Override
    public void delete(String branchName) {
//todo
    }
}
