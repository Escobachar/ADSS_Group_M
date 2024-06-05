package UnitTests;

import Domain.*;
import java.util.*;
import org.junit.*;

public class GeneralEmployeeTest {
    public GeneralEmployee ge;
    public Role cashier;
    public Role storekeeper;
    public Branch branch;
    public Network network;
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public List<Role> roles;

    @Before
    public void initTest() {
        HRManager hrm = new HRManager(315213215, "tomer", "66666666", 500, new Date(2024, 5, 31), "half", 1,"1234");
        network=new Network(hrm);
        branch = new Branch("Mazkeret", "Mazkeret Batya", network);
        cashier = new Role("cashier", null,branch);
        storekeeper = new Role("storekeeper", null,branch);
        roles = new LinkedList<>();
        roles.add(cashier);
        roles.add(storekeeper);
        ge = new GeneralEmployee(0, "shai", "66666666", 500, new Date(2024, 5, 31), "half", 1, roles, false, branch,"1234");
        ShiftsRequest1 = new boolean[Network.shifts][Network.days];
        ShiftsRequest2 = new boolean[Network.shifts][Network.days];

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