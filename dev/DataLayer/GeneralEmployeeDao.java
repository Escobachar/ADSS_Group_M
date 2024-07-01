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

    private Connection toConnect(){
        Connection connection=null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(url);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }

    @Override
    public void create(Employee emp) {
        GeneralEmployee ge = (GeneralEmployee) emp;
        Connection connection = toConnect();
        String query = "INSERT INTO GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, password, isManager, branchName) VALUES(?, ?, ?, ?, ?, ?, ?, ?, ? , ? ,?)";
        try {
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
        GeneralEmployee ge = null;
        Connection connection = toConnect();
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
                        Date startOfEmployment =Date.valueOf(resultSet.getString("startOfEmployment"));
                        Date endOfEmployment =Date.valueOf(resultSet.getString("endOfEmployment"));
                        String partOfJob =resultSet.getString("partOfJob");
                        int vacationsDays = resultSet.getInt("vacationsDays");
                        String password =resultSet.getString("password");
                        int isManager = resultSet.getInt("isManager");
                        String branch =resultSet.getString("branch");
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
                                query = "SELECT * FROM Role WHERE roleNme = ?";
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

                       ge = new GeneralEmployee(id, name, bankAccountDetails, salary,startOfEmployment,endOfEmployment,partOfJob, vacationsDays,roles, isManager==1,Network.getNetwork().getBranch(branch),password);
                    }
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            return ge;
        }



    @Override
    public void update(Employee emp) {
        GeneralEmployee ge = (GeneralEmployee) emp;
        Connection connection = toConnect();
        String query = "UPDATE GeneralEmployee SET name = ?, bankAccountDetails = ?, salary = ?, startOfEmployment = ?, endOfEmployment = ?, partOfJob = ?, vacationsDays = ?, password = ?, isManager=? , branchName=? WHERE ID = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, ge.getName());
            prepare.setString(2, ge.getBankAccountDetails());
            prepare.setInt(3, ge.getSalary());
            prepare.setString(4, ge.getStartOfEmployment().toString());
            prepare.setString(5, ge.getEndOfEmployment().toString());
            prepare.setString(6, ge.getPartOfJob());
            prepare.setInt(7, ge.getVacationsDays());
            prepare.setString(8, ge.getPassword());
            prepare.setBoolean(9, ge.isManager());
            prepare.setString(10, ge.getBranch().getBranchName());
            prepare.setInt(11, ge.getID());
            prepare.executeUpdate();
            System.out.println("Employee has been Updated.");
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
    public void delete(int ID) {
        //not used
    }
}
