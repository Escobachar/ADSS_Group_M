package DataLayer.IMP;

import DataLayer.interfaces.EmployeeDao;
import DataLayer.interfaces.ShiftRequestDao;
import Domain.Employee;
import Domain.Driver;
import Domain.Network;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class DriverDao implements EmployeeDao {
    final static String stringEmployeeType = "Driver";
    ShiftRequestDao shiftRequestDao = new ShiftRequestDaoImp();

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
                    d.updateShifts(shiftRequestDao.read(id));
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

            //Delete from ShiftsRequests table
            query = "DELETE FROM ShiftsRequests WHERE ID = ?";
            prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            deleteRows = prepare.executeUpdate();

            if (deleteRows > 0) {
                System.out.println("ShiftsRequests has been deleted from ShiftsRequests table.");
            } else {
                System.out.println("No ShiftsRequests found with ID: " + ID);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Override
    public List<Employee> readAll(String branchName){
        List<Employee> list = null;
        Driver d = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM Driver WHERE branchName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int id = resultSet.getInt("ID");
                    String name = resultSet.getString("name");
                    String bankAccountDetails = resultSet.getString("bankAccountDetails");
                    int salary = resultSet.getInt("salary");
                    String startOfEmployment = resultSet.getString("startOfEmployment");
                    String endOfEmployment = resultSet.getString("endOfEmployment");
                    String partOfJob = resultSet.getString("partOfJob");
                    int vacationsDays = resultSet.getInt("vacationsDays");
                    String password = resultSet.getString("password");
                    String branch = resultSet.getString("branchName");
                    int driverLicense = resultSet.getInt("driverLicense");
                    //DriverLicenseTypes
                    List<String> driverLicenseTypes = new ArrayList<>();
                    {
                        query = "SELECT * FROM DriverLicenseTypes WHERE ID = ?";
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, id);
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
                    d.updateShifts(shiftRequestDao.read(id));
                    list.add(d);
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch(SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return list;
    }
}

