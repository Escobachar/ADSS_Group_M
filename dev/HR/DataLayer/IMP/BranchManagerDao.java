package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.BranchDao;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.Domain.BranchManager;
import HR.Domain.Employee;
import HR.Domain.Network;
import HR.Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

public class BranchManagerDao implements EmployeeDao {
    private static BranchDao branchDao = new BranchDaoImp();
    @Override
    public void create(Employee emp) {
        BranchManager bm = (BranchManager) emp;
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO BranchManager(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, branchName, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, bm.getID());
            prepare.setString(2, bm.getName());
            prepare.setString(3, bm.getBankAccountDetails());
            prepare.setInt(4, bm.getSalary());
            prepare.setString(5, bm.getStartOfEmployment());
            prepare.setString(6, bm.getEndOfEmployment());
            prepare.setString(7, bm.getPartOfJob());
            prepare.setInt(8, bm.getVacationsDays());
            prepare.setString(9, bm.getBranch().getBranchName());
            prepare.setString(10, bm.getPassword());

            prepare.executeUpdate();
            System.out.println("BranchManager has been added to BranchManager table.");

            //update into Branch table
            branchDao.update( bm.getID(), bm.getBranch().getBranchName());

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
    }

    @Override
    public Employee read(int ID) {
        BranchManager bm = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM BranchManager WHERE ID = ?";//is this the name?
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("name");
                    String bankAccountDetails = resultSet.getString("bankAccountDetails");
                    int salary = resultSet.getInt("salary");
                    String startOfEmployment = resultSet.getString("startOfEmployment");
                    String endOfEmployment = resultSet.getString("endOfEmployment");
                    String partOfJob = resultSet.getString("partOfJob");
                    int vacationsDays = resultSet.getInt("vacationsDays");
                    String branchName = resultSet.getString("branchName");
                    String password = resultSet.getString("password");

                    bm = new BranchManager(id, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, Network.getNetwork().getBranch(branchName), password);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return bm;
    }
    @Override
    public void update(Employee emp){
        delete(emp.getID());
        create(emp);
    }

    @Override
    public void delete(int ID) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM BranchManager WHERE ID = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            int deleteRows = prepare.executeUpdate();

            if (deleteRows > 0)
                System.out.println("BranchManager has been deleted from GeneralEmployee table.");
            else
                System.out.println("No BranchManager found with ID: " + ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public List<Employee> readAll(String branchName) {
        return null;
    }
}
