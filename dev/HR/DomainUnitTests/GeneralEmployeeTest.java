package HR.DomainUnitTests;

import HR.DataLayer.IMP.HRManagerDao;
import HR.DataLayer.interfaces.EmployeeDao;
import HR.Domain.*;
import java.util.*;
import org.junit.*;

public class GeneralEmployeeTest {
    public HRManager hrm;
    public GeneralEmployee ge;
    public Branch branch;
    public Network network;
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public List<Role> roles;

    @Before
    public void initTest() {
        hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        EmployeeDao HRMDao = new HRManagerDao();
        HRMDao.create(hrm);
        network =  Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("shift manager", GeneralEmployeeAccess));
        network.addRole(new Role("cashier",GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper",GeneralEmployeeAccess));
        Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
        BranchManager bm = new BranchManager(222222222, "Tomer Cohen", "2222222222", 50, "04-06-2024", null, "Half", 18, branch, "2222");
        List<Role> roleList = Network.getNetwork().getRoles();
        List<Role> oneRole = new ArrayList<>();
        oneRole.add(roleList.get(1));
        GeneralEmployee ge1 = hrm.addGeneralEmployee(333333333, "Shahar Bar", "3333333333", 40, "04-06-2024", null, "Full", 10, roleList, true, branch, "3333");
        GeneralEmployee ge2 = hrm.addGeneralEmployee(444444444, "Shelly", "444444444", 40, "04-06-2024", null, "Half", 10, oneRole, false, branch, "4444");

        for (int i = 0; i < Network.shifts; i++) {
            for (int j = 0; j < Network.days; j++) {
                ShiftsRequest1[i][j] = true;
                ShiftsRequest2[i][j] = false;
            }
        }
    }

    @Test
    public void updateShifts0() {
        Assert.assertFalse("expected false",Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()));
    }

    @Test
    public void updateShifts1() {
        ge.updateShifts(ShiftsRequest1);
        Assert.assertTrue("expected true",Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()));
    }

    @Test
    public void updateShifts2() {
        ge.updateShifts(ShiftsRequest2);
        Assert.assertFalse("expected false",Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()));
    }
}