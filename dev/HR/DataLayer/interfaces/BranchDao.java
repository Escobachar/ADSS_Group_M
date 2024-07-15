package HR.DataLayer.interfaces;

import HR.Domain.Branch;

import java.util.List;

public interface BranchDao {
    void create(Branch branch);
    Branch read(String branchName);
    void update(Branch branch);
    void update(int BMID,String branchName);
    void delete(String branchName);
    List<String> readAll();

}
