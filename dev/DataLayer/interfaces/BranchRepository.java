package DataLayer.interfaces;

import Domain.Branch;

public interface BranchRepository {
    void create(Branch branch);
    Branch get(String branchName);
    void update(Branch branch, String commands);
    void delete(Branch branch, String commands);
}
