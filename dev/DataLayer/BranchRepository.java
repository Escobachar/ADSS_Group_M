package DataLayer;

import Domain.Branch;

public interface BranchRepository {
    Branch get(String branchName);
    void create(Branch branch);
    void update(Branch branch);
    void delete(String branchName);
}
