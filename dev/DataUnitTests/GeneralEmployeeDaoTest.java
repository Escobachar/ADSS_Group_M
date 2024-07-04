package DataUnitTests;


import Domain.*;
import DataLayer.IMP.*;
import DataLayer.interfaces.EmployeeDao;
import DataLayer.interfaces.NetworkRepository;
import Server.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

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
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public List<Role> roleList;
    EmployeeDao GeneralDao = new GeneralEmployeeDao();
    EmployeeDao DriverDao = new DriverDao();
    EmployeeDao BranchManagerDao = new BranchManagerDao();
    EmployeeDao HRMDao = new HRManagerDao();

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
        //BranchManager bm = hrm.addBranchManager(222222222, "Tomer Cohen", "2222222222", 50, "04-06-2024", null, "Half", 18, branch, "2222");
        roleList = Network.getNetwork().getRoles();
        //List<Role> oneRole = new ArrayList<>();
        //oneRole.add(roleList.get(1));

        ge1 = new GeneralEmployee(333333333, "Shahar Bar", "9999999999", 50, "04-06-2024", null, "Half", 18, roleList, true, branch, "3333");
/*
        GeneralEmployee ge2 = new GeneralEmployee(333333333, "Shahar Bar", "9999999999", 50, "04-06-2024", null, "Half", 18, roleList, true, branch, "3333");
        hrm.addGeneralEmployee(444444444, "Shelly atanelov", "9999995555", 35, "05-06-2024", null, "Full", 18, oneRole, false, branch, "4444");
        hrm.addGeneralEmployee(333333333, "Shahar Bar", "9999999999", 50, "04-06-2024", null, "Half", 18, roleList, true, branch, "3333");
        List<String> licenseTypes = new ArrayList<>();
        licenseTypes.add("A");
        licenseTypes.add("B");
        hrm.addDriver(555555555, "Illy Hason", "9999999999", 35, "05-06-2024", null, "Full", 18, branch, "5555", 1234567, licenseTypes);
*/
    }


    @Test
        public void create() {
        int addedRows = 0;
        GeneralDao.create(ge1);

        Connection connection = Utility.toConnect();
        String query = "SELECT COUNT(*) FROM GeneralEmployee";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            addedRows = prepare.executeUpdate();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);

        Assert.assertEquals(1, addedRows);

    }
}
