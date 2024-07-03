package DataLayer;

import Domain.Branch;

import java.util.List;

public interface BranchRepository {
    void create(Branch branch);
    Branch get(String branchName);
    void update(Branch branch, String commands);
    void delete(Branch branch, String commands);
}
