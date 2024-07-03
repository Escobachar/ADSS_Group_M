package DataLayer;

import Domain.Branch;
import Domain.BranchManager;
import Domain.HRManager;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class BranchDaoImp implements BranchDao {
    EmployeeDao BranchManagerDao = new BranchManagerDao();
    @Override
    public void create(Branch branch) {
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Branch(branchName, location, BMID) VALUES(?, ?, ?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branch.getBranchName());
            prepare.setString(2, branch.getLocation());
            prepare.setInt(3, branch.getBranchManager().getID());
            prepare.executeUpdate();
            System.out.println("Branch has been added to Branch.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public Branch read(String branchName) {
        Branch branch = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM Branch WHERE branchName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String name = resultSet.getString("branchName");
                    String location = resultSet.getString("location");
                    int BMID = resultSet.getInt("BMID");
                    BranchManager BM = (BranchManager) BranchManagerDao.read(BMID);
                    branch = new Branch(name, location, BM);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return branch;
    }

    @Override
    public void update(Branch branch) {
        delete(branch.getBranchName());
        create(branch);
    }

    @Override
    public void delete(String branchName) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM Branch WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();

            if (deleteRows > 0)
                System.out.println("branch has been deleted from Branch table.");
            else
                System.out.println("No branch found with branchName: " + branchName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
}
