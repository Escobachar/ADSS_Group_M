package DataLayer;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import Domain.GeneralEmployee;
import Domain.Employee;


public class GeneralEmployeeDao implements EmployeeDao {
    private final GeneralEmployee generalEmployee;

    public GeneralEmployeeDao(GeneralEmployee generalEmployee) {
        this.generalEmployee = generalEmployee;
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String url = "jdbc:sqlite:C:\\uni\\D\\nitoz\\HR\\dev\\DataLayer\\DataBase.db";
            connection = DriverManager.getConnection(url);

            System.out.println("Connection to SQLite has been established.");

        } catch (SQLException | ClassNotFoundException e) {
            System.out.println(e.getMessage());
        } finally {
            try {
                if (connection != null) {
                    connection.close();
                }
            } catch (SQLException ex) {
                System.out.println(ex.getMessage());
            }
        }
    }


    @Override
    public void create(Employee emp) {
        //todo
    }

    @Override
    public Employee read(int ID) {
        return null;//todo
    }

    @Override
    public void update(Employee emp) {
//todo
    }

    @Override
    public void delete(int ID) {
//todo
    }
}
