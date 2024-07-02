package DataLayer;

import Domain.Employee;
import Domain.Driver;
import Domain.Network;
import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDao implements EmployeeDao {
    final static String stringEmployeeType = "Driver";

    @Override
    public void create(Employee emp) {
        Driver d = (Driver) emp;
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Driver(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password, branchName, driverLicense) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, d.getID());
            prepare.setString(2, d.getName());
            prepare.setString(3, d.getBankAccountDetails());
            prepare.setInt(4, d.getSalary());
            prepare.setString(5, d.getStartOfEmployment());
            prepare.setString(6, d.getEndOfEmployment());
            prepare.setString(7, d.getPartOfJob());
            prepare.setInt(8, d.getVacationsDays());
            prepare.setString(9, d.getPassword());
            prepare.setString(10, d.getBranch().getBranchName());
            prepare.setInt(11, d.getDriverLicense());
            prepare.executeUpdate();
            System.out.println("Driver has been added.");
            query = "INSERT INTO DriverLicenseTypes(ID, driverLicenseType) VALUES(? , ?)";
            for (String DLT : d.getDriverLicenseTypes()) {
                prepare = connection.prepareStatement(query);
                prepare.setInt(1, d.getID());
                prepare.setString(2, DLT);
                prepare.executeUpdate();
            }
            System.out.println("Driver has been added to DriverLicenseTypes.");
            //insert to EmployeeList table
            query = "INSERT INTO EmployeeList(branchName, empID, type) VALUES(?, ?, ?)";

            prepare = connection.prepareStatement(query);
            prepare.setString(1, d.getBranch().getBranchName());
            prepare.setInt(2, d.getID());
            prepare.setString(3, stringEmployeeType);
            prepare.executeUpdate();
            System.out.println("Driver has been added to EmployeeList.");
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        Utility.Close(connection);
    }

    @Override
    public Employee read(int ID) {
        Driver d = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM Driver WHERE ID = ?";
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
                    int isManager = resultSet.getInt("isManager");
                    String branch = resultSet.getString("branchName");
                    int driverLicense = resultSet.getInt("driverLicense");
                    //DriverLicenseTypes
                    List<String> driverLicenseTypes = new ArrayList<>();
                    {
                        query = "SELECT * FROM DriverLicenseTypes WHERE ID = ?";
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, ID);
                        try (ResultSet resultSet2 = statement.executeQuery()) {
                            while (resultSet2.next()) {
                                String driverLicenseType = resultSet2.getString("driverLicenseType");
                                driverLicenseTypes.add(driverLicenseType);
                            }
                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    d = new Driver(id, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, Network.getNetwork().getBranch(branch), password, driverLicense, driverLicenseTypes);
                }
            } catch (SQLException e) {
            System.out.println(e.getMessage());
            }
        } catch(SQLException e) {
        System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return d;
    }

    public void update(Employee emp){
        delete(emp.getID());
        create(emp);
    }
    @Override
    public void delete(int ID) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM Driver WHERE ID = ?";
        try {
            //Delete from Driver
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            int deleteRows = prepare.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("Driver has been deleted from Driver table.");
            } else {
                System.out.println("No Driver found with ID: " + ID);
            }

            //Delete from EmployeeList table
            query = "DELETE FROM EmployeeList WHERE empID = ?";
            prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            deleteRows = prepare.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("Driver has been deleted from EmployeeList table.");
            } else {
                System.out.println("No Driver found with ID: " + ID);
            }

            //Delete from DriverLicenseTypes&Drivers table
            query = "DELETE FROM DriverLicenseTypes WHERE ID = ?";
            prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            deleteRows = prepare.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("Driver has been deleted from DriverLicenseTypes&Driver table.");
            } else {
                System.out.println("No Driver found with ID: " + ID);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
}

