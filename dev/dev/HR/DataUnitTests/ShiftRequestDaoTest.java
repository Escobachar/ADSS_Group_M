package HR.DataUnitTests;

import HR.DataLayer.IMP.GeneralEmployeeDao;
import HR.DataLayer.IMP.HRManagerDao;
import HR.DataLayer.IMP.NetworkRepositoryImp;
import HR.DataLayer.IMP.ShiftRequestDaoImp;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.DataLayer.interfaces.NetworkRepository;
import HR.DataLayer.interfaces.ShiftRequestDao;
import HR.Domain.*;
import HR.Server.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ShiftRequestDaoTest {
    public boolean[][] shiftsRequest;
    ShiftRequestDao ShiftRequestDao=new ShiftRequestDaoImp();
    EmployeeDao GeneralDao = new GeneralEmployeeDao();
    public GeneralEmployee ge1;


    @Before
    public void setUp() {
        NetworkRepository NR = new NetworkRepositoryImp();
        NR.delete();//delete all data before the test
        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        EmployeeDao HRMDao = new HRManagerDao();
        HRMDao.create(hrm);
        Network network = Network.createNewNetwork(hrm);
        network.addRole(new Role("shift manager", new LinkedList<>()));

        shiftsRequest=new boolean[Network.shifts][Network.days];
        for(int i=0;i<shiftsRequest.length;i++)
            for(int j=0;j<shiftsRequest[i].length;j++)
                shiftsRequest[i][j]=false;
        shiftsRequest[1][1]=true;
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        network.addRole(new Role("driver", DriverAccess));
        hrm.addBranch("", "", null);
        Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
        List<Role> roleList = Network.getNetwork().getRoles();

        ge1 = new GeneralEmployee(333333333, "Shahar Bar", "9999999999", 50, "04-06-2024", null, "Half", 18, roleList, true, branch, "3333");
        GeneralDao.create(ge1);
    }

    @Test
    public void create(){
        ShiftRequestDao.create(shiftsRequest, 333333333);
        Connection connection = Utility.toConnect();
        String query = "SELECT COUNT(*) FROM ShiftsRequests";
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
    public void delete() {
        ShiftRequestDao.create(shiftsRequest, 333333333);
        ShiftRequestDao.delete(333333333);

        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM ShiftsRequests";
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
