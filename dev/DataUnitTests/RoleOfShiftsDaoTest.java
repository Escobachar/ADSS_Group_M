package DataUnitTests;

import DataLayer.IMP.RoleOfShiftsDaoImp;
import DataLayer.interfaces.RoleOfShiftsDao;
import Domain.GeneralEmployee;
import Domain.HRManager;
import Domain.Network;
import Domain.Role;
import Server.Utility;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoleOfShiftsDaoTest {
    public HashMap<Role,Integer[][]> rolesOfShifts;
    public RoleOfShiftsDao roleOfShiftsDao = new RoleOfShiftsDaoImp();

    @Before
    public void setUp() {
        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        rolesOfShifts = new HashMap<Role,Integer[][]>();
        for(Role r: Network.getNetwork().getRoles()) {
            rolesOfShifts.put(r, new Integer[Network.shifts][Network.days]);
            for (int i = 0; i < Network.shifts; i++) {
                rolesOfShifts.get(r)[i] = new Integer[Network.days];
                for (int j = 0; j < Network.days; j++) {
                    rolesOfShifts.get(r)[i][j] = 1;
                }
            }
        }
    }

    @Test
    public void create() {
        roleOfShiftsDao.create("branch",rolesOfShifts);
        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM RoleOfShifts";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            resultSet.next();
            rowCount = resultSet.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);

        Assert.assertEquals(0,rowCount);
    }
    @Test
    public void update(){
        roleOfShiftsDao.create("branch",rolesOfShifts);
        roleOfShiftsDao.update("branch","cashier",1,1,1);
        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM RoleOfShifts";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            ResultSet resultSet = prepare.executeQuery();
            resultSet.next();
            rowCount = resultSet.getInt(1);

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);

        Assert.assertEquals(1, rowCount);

    }

    @Test
    public void delete() {
        roleOfShiftsDao.create("branch",rolesOfShifts);
        roleOfShiftsDao.delete("branch");

        Connection connection = Utility.toConnect();
        int rowCount = 0;
        String query = "SELECT COUNT(*) FROM RoleOfShifts";
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
