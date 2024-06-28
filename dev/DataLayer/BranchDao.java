package DataLayer;

import Domain.Branch;
import Domain.Employee;

public interface BranchDao {
    void create(Branch branch);
    Branch read(String branchName);
    void update(Branch branch);
    void delete(String branchName);

}
