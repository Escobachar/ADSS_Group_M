package HR.DataUnitTests;


import HR.Domain.*;
import HR.DataLayer.IMP.*;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.DataLayer.interfaces.NetworkRepository;
import HR.Server.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.ResultSet;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class GeneralEmployeeDaoTest {
    public HRManager hrm;
    public GeneralEmployee ge1;
    public Branch branch;
    public Network network;
    public List<Role> roleList;
    EmployeeDao GeneralDao = new GeneralEmployeeDao();

    @Before
    public void initTest() {
        NetworkRepository NR = new NetworkRepositoryImp();
        NR.delete();//delete all data before the test

        hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        network.addRole(new Role("driver", DriverAccess));
        hrm.addBranch("", "", null);
        Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
        roleList = Network.getNetwork().getRoles();

        ge1 = new GeneralEmployee(333333333, "Shahar Bar", "9999999999", 50, "04-06-2024", null, "Half", 18, roleList, true, branch, "3333");
    }


    @Test
        public void create() {
        GeneralDao.create(ge1);
        Connection connection = Utility.toConnect();
        String query = "SELECT COUNT(*) FROM GeneralEmployee";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            resultSet.next();
            int rowCount = resultSet.getInt(1);
            Assert.assertEquals(1, rowCount);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Test
    public void read() {
        GeneralDao.create(ge1);
        GeneralEmployee test = (GeneralEmployee) GeneralDao.read(333333333);

        Assert.assertEquals(ge1.getName(), test.getName());
    }

    @Test
    public void delete() {
        GeneralDao.create(ge1);
        GeneralDao.delete(333333333);

        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM GeneralEmployee";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            resultSet.next();
            rowCount = resultSet.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);

        Assert.assertEquals(0, rowCount);
    }

}



