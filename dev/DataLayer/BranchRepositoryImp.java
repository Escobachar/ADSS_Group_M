package DataLayer;

import Domain.Branch;

public class BranchRepositoryImp implements BranchRepository {
    private BranchDao branchDao=new BranchDaoImp();
    private EmployeeDao branchManagerDao = new BranchManagerDao();
    private EmployeeDao HRManagerDao = new HRManagerDao();
    private EmployeeDao driverDao = new DriverDao();
    private EmployeeDao generalEmployeeDao = new GeneralEmployeeDao();

    @Override
    public Branch get(String branchName) {
        return null;//todo
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
