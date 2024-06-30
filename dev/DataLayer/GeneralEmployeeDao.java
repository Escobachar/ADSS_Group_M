package DataLayer;


import java.sql.*;
import java.sql.ResultSet;
import Domain.GeneralEmployee;
import Domain.Employee;


public class GeneralEmployeeDao implements EmployeeDao {
    String url  = "jdbc:sqlite:C:\\uni\\D\\nitoz\\testing\\dev\\DataLayer\\DataBase.db";

    public GeneralEmployeeDao() {
        Connection connection=null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    @Override
    public void create(Employee emp) {
        GeneralEmployee ge = (GeneralEmployee) emp;
        Connection connection=null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
            String query = "INSERT INTO GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password, isManager, branchName) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,?)";
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ge.getID());
            prepare.setString(2, ge.getName());
            prepare.setString(3, ge.getBankAccountDetails());
            prepare.setInt(4, ge.getSalary());
            prepare.setString(5, ge.getStartOfEmployment().toString());
            prepare.setString(6, ge.getEndOfEmployment().toString());
            prepare.setString(7, ge.getPartOfJob());
            prepare.setInt(8, ge.getVacationsDays());
            prepare.setString(9, ge.getPassword());
            prepare.setBoolean(10,ge.isManager());
            prepare.setString(11, ge.getBranch().getBranchName());
            prepare.executeUpdate();
            System.out.println("Employee has been added.");

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
    @Override
    public Employee read(int ID) {
        return null;//todo
    }
/*
    @Override
    public Employee read(int ID) {
        return null;//todo
        String query = "SELECT * FROM Employees WHERE ID = ?";

        try (PreparedStatement prepare = connection.prepareStatement(query));
             PreparedStatement statement = connection.prepareStatement(query)) {

            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("name");
                    String department = resultSet.getString("department");
                    double salary = resultSet.getDouble("salary");
                    // Populate other fields as necessary

                    employee = new Employee(id, name, department, salary);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
            // Handle the exception as needed, e.g., throw a custom exception or log the error
        }
        return employee;
    }
    }*/

    @Override
    public void update(Employee emp) {
//todo
    }

    @Override
    public void delete(int ID) {
//todo
    }
}
