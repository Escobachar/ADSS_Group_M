package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.BranchDao;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.Domain.Branch;
import HR.Domain.BranchManager;
import HR.Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BranchDaoImp implements BranchDao {
    private static EmployeeDao BranchManagerDao = new BranchManagerDao();
    @Override
    public void create(Branch branch) {
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Branch(branchName, location, BMID) VALUES(?, ?, ?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branch.getBranchName());
            prepare.setString(2, branch.getLocation());
            if(branch.getBranchManager()!=null)
                prepare.setInt(3, branch.getBranchManager().getID());
            else
                prepare.setInt(3, 0);
            prepare.executeUpdate();
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

    public void update(int BMID,String branchName){
        Connection connection = Utility.toConnect();
        String query = "UPDATE Branch SET BMID = ? WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, BMID);
            prepare.setString(2, branchName);
            prepare.executeUpdate();
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    @Override
    public void delete(String branchName) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM Branch WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public List<String> readAll() {
        List<String> branchNames = new ArrayList<String>();
        Connection connection = Utility.toConnect();
        String query = "SELECT branchName FROM Branch";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            try (ResultSet resultSet = statement.executeQuery()){
                while (resultSet.next()) {
                    String s = resultSet.getString("branchName");
                    if(!s.equals(""))
                        branchNames.add(s);
                }
            }catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return branchNames;
    }


}
