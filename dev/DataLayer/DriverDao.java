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
    @Override
    public void create(Employee emp) {
        Driver d = (Driver) emp;
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Driver(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password, isManager, branchName, driverLicense) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ? , ?, ?)";
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
            prepare.setBoolean(10, d.isManager());
            prepare.setString(11, d.getBranch().getBranchName());
            prepare.setString(12, d.getDriverLicense());
            prepare.executeUpdate();
            query = "INSERT INTO DriverLicenseTypes&Drivers(ID, driverLicenseTypes) VALUES(? , ?)";
            try {
                for (String driverLicenseTypes : d.getDriverLicenseTypes()) {
                    prepare = connection.prepareStatement(query);
                    prepare.setInt(1, d.getID());
                    prepare.setString(2, driverLicenseTypes);
                    prepare.executeUpdate();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            System.out.println("Driver has been added.");
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
                    String driverLicense = resultSet.getString("driverLicense");
                    //DriverLicenseTypes
                    List<String> driverLicenseTypes = new ArrayList<>();
                    {
                        query = "SELECT * FROM DriverLicenseTypes WHERE ID = ?";
                        statement.setInt(1, ID);
                        statement = connection.prepareStatement(query);
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
    } catch(
    SQLException e)

    {
        System.out.println(e.getMessage());
    }finally

    {
        try {
            if (connection != null)
                connection.close();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }
        return d;
    }

    @Override
    public void update(Employee emp) {
        /*
        Driver d = (Driver) emp;
        Connection connection = Utility.toConnect();
        String query = "UPDATE Driver SET name = ?, bankAccountDetails = ?, salary = ?, startOfEmployment = ?, endOfEmployment = ?, partOfJob = ?, vacationsDays = ?, branchName=?, password = ?, driverLicense = ? WHERE ID = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, d.getName());
            prepare.setString(2, d.getBankAccountDetails());
            prepare.setInt(3, d.getSalary());
            prepare.setString(4, d.getStartOfEmployment());
            prepare.setString(5, d.getEndOfEmployment());
            prepare.setString(6, d.getPartOfJob());
            prepare.setInt(7, d.getVacationsDays());
            prepare.setString(8, d.getBranch().getBranchName());
            prepare.setString(9, d.getPassword());
            prepare.setString(10, d.getDriverLicense());
            prepare.executeUpdate();

            //DriverLicenseTypes
            {
                query = "SELECT * FROM DriverLicenseTypes WHERE ID = ?";
                statement.setInt(1, ID);
                List<String> driverLicenseTypes = new ArrayList<>();
                statement = connection.prepareStatement(query);
                try (ResultSet resultSet2 = statement.executeQuery()) {
                    while (resultSet2.next()) {
                        String driverLicenseType = resultSet2.getString("driverLicenseType");
                        driverLicenseTypes.add(driverLicenseType);
                    }
                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
            }



            System.out.println("Driver has been Updated.");
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

         */
    }

    @Override
    public void delete(int ID) {
        //not used
    }
    }

