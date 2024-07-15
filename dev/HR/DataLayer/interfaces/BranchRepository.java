package HR.DataLayer.interfaces;

import HR.Domain.Branch;

public interface BranchRepository {
    void create(Branch branch);
    Branch get(String branchName);
    void NetworkBuilder(String branchName);
    void update(Branch branch, String commands);
    void delete(Branch branch, String commands);

}
