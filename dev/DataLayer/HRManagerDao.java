package DataLayer;

import Domain.*;
import Server.Utility;
import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class HRManagerDao implements EmployeeDao{
    @Override
    public void create(Employee emp) {
        HRManager hrm = (HRManager) emp;
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, hrm.getID());
            prepare.setString(2, hrm.getName());
            prepare.setString(3, hrm.getBankAccountDetails());
            prepare.setInt(4, hrm.getSalary());
            prepare.setString(5, hrm.getStartOfEmployment());
            prepare.setString(6, hrm.getEndOfEmployment());
            prepare.setString(7, hrm.getPartOfJob());
            prepare.setInt(8, hrm.getVacationsDays());
            prepare.setString(9, hrm.getPassword());

            prepare.executeUpdate();
            System.out.println("HRManager has been added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public Employee read(int ID) {
        HRManager hrm = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM HRManager WHERE ID = ?";
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
                    String password = resultSet.getString("password");

                    hrm = new HRManager(id, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password);
                }
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return hrm;
    }

    @Override
    public void update(Employee emp){
        delete(emp.getID());
        create(emp);
    }

    @Override
    public void delete(int ID) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM HRManager WHERE ID = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            int deleteRows = prepare.executeUpdate();

            if (deleteRows > 0)
                System.out.println("HRManager has been deleted from HRManager table.");
            else
                System.out.println("No HRManager found with ID: " + ID);
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
