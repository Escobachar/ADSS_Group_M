package HR.DataLayer.IMP;


import java.sql.*;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

import HR.DataLayer.interfaces.*;
import HR.DataLayer.interfaces.ShiftRequestDao;
import HR.Server.Utility;
import HR.Domain.GeneralEmployee;
import HR.Domain.Employee;
import HR.Domain.Network;
import HR.Domain.Role;
//import java.text.SimpleDateFormat;

public class GeneralEmployeeDao implements EmployeeDao {
    final static String stringEmployeeType = "GeneralEmployee";
    ShiftRequestDao shiftRequestDao = new ShiftRequestDaoImp();

    @Override
    public void create(Employee emp) {
        GeneralEmployee ge = (GeneralEmployee) emp;
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password, isManager, branchName) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ge.getID());
            prepare.setString(2, ge.getName());
            prepare.setString(3, ge.getBankAccountDetails());
            prepare.setInt(4, ge.getSalary());
            prepare.setString(5, ge.getStartOfEmployment());
            prepare.setString(6, ge.getEndOfEmployment());
            prepare.setString(7, ge.getPartOfJob());
            prepare.setInt(8, ge.getVacationsDays());
            prepare.setString(9, ge.getPassword());
            prepare.setBoolean(10, ge.isManager());
            prepare.setString(11, ge.getBranch().getBranchName());
            prepare.executeUpdate();

            //insert to GeneralEmployeeRole table
            for(Role role: ge.getRoles()) {
                query = "INSERT INTO GeneralEmployeeRole(ID, roleName) VALUES(?, ?)";

                prepare = connection.prepareStatement(query);
                prepare.setInt(1, ge.getID());
                prepare.setString(2,role.getRoleName() );
                prepare.executeUpdate();
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public Employee read(int ID) {
        GeneralEmployee ge = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM GeneralEmployee WHERE ID = ?";
        try {
                PreparedStatement statement = connection.prepareStatement(query);
                statement.setInt(1, ID);
                try (ResultSet resultSet = statement.executeQuery()) {
                    if (resultSet.next()) {
                        int id = resultSet.getInt("ID");
                        String name = resultSet.getString("name");
                        String bankAccountDetails = resultSet.getString("bankAccountDetails");
                        int salary = resultSet.getInt("salary");
                        String startOfEmployment =resultSet.getString("startOfEmployment");
                        String endOfEmployment = resultSet.getString("endOfEmployment");
                        String partOfJob =resultSet.getString("partOfJob");
                        int vacationsDays = resultSet.getInt("vacationsDays");
                        String password =resultSet.getString("password");
                        int isManager = resultSet.getInt("isManager");
                        String branch =resultSet.getString("branchName");
                        //Roles
                        query = "SELECT * FROM GeneralEmployeeRole WHERE ID = ?";
                        List<Role> roles = new ArrayList<>();
                        statement = connection.prepareStatement(query);
                        statement.setInt(1, ID);
                        try (ResultSet resultSet2 = statement.executeQuery()) {
                            while (resultSet2.next()) {
                                String roleName = resultSet2.getString("roleName");
                                List<String> access = new ArrayList<>();
                                //Access
                                query = "SELECT * FROM Role WHERE roleName = ?";
                                statement = connection.prepareStatement(query);
                                statement.setString(1, roleName);
                                try (ResultSet resultSet3 = statement.executeQuery()) {
                                    while (resultSet3.next()) {
                                        access.add(resultSet3.getString("access"));
                                    }
                                }
                                Role role = new Role(roleName,access);
                                roles.add(role);
                            }

                        } catch (SQLException e) {
                            System.out.println(e.getMessage());
                        }

                        ge = new GeneralEmployee(id, name, bankAccountDetails, salary,startOfEmployment, endOfEmployment, partOfJob, vacationsDays,roles, isManager==1,Network.getNetwork().getBranch(branch),password);
                        ge.updateShiftsWithoutDao(shiftRequestDao.read(id));
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            Utility.Close(connection);
            return ge;
        }

    @Override
    public void update(Employee emp) {
        delete(emp.getID());
        create(emp);
    }

    @Override
    public void delete(int ID) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM GeneralEmployee WHERE ID = ?";
        try {
            //Delete from GeneralEmployee
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            int deleteRows = prepare.executeUpdate();

            //Delete from GeneralEmployeeRole table
            query = "DELETE FROM GeneralEmployeeRole WHERE ID = ?";
            prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            deleteRows = prepare.executeUpdate();

            //Delete from ShiftsRequests table
            query = "DELETE FROM ShiftsRequests WHERE ID = ?";
            prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            deleteRows = prepare.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public  List<Employee> readAll(String branchName) {
        List<Employee> list = new ArrayList<>();
        GeneralEmployee ge = null;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM GeneralEmployee WHERE branchName = ?";
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
                    int isManager = resultSet.getInt("isManager");
                    String branch = resultSet.getString("branchName");

                    //Roles
                    List<Role> roleList = new ArrayList<>();
                    query = "SELECT * FROM GeneralEmployeeRole WHERE ID = ?";
                    statement = connection.prepareStatement(query);
                    statement.setInt(1, id);
                    try (ResultSet resultSet2 = statement.executeQuery()) {
                        while (resultSet2.next()) {
                            String roleName = resultSet2.getString("roleName");
                            List<String> access = new ArrayList<>();
                            //Access
                            query = "SELECT * FROM Role WHERE roleName = ?";
                            statement = connection.prepareStatement(query);
                            statement.setString(1, roleName);
                            try (ResultSet resultSet3 = statement.executeQuery()) {
                                while (resultSet3.next()) {
                                    access.add(resultSet3.getString("access"));
                                }
                            }
                            Role role = new Role(roleName, access);
                            roleList.add(role);
                        }
                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    ge = new GeneralEmployee(id, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roleList, isManager == 1, Network.getNetwork().getBranch(branch), password);
                    ge.updateShifts(shiftRequestDao.read(id));
                    list.add(ge);
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return list;
    }
}