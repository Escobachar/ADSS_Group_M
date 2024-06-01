package UnitTests;
import Domain.*;
import java.util.*;
import java.util.Date;
import org.junit.*;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class generalEmployeeTest {
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
        network=new Network();
        branch = new Branch("Mazkeret", "Mazkeret Batya", network);
        cashier = new Role("cashier", null,branch);
        storekeeper = new Role("storekeeper", null,branch);
        roles = new LinkedList<>();
        roles.add(cashier);
        roles.add(storekeeper);
        network = new Network();
        ge = new GeneralEmployee(0, "shai", 66666666, 500, new Date(2024, 5, 31), "half", 1, roles, false, branch);
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
        assertFalse(Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()), "expected false");
    }

    @Test
    public void updateShifts1() {
        ge.updateShifts(ShiftsRequest1);
        assertTrue(Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()),"expected true");
    }

    @Test
    public void updateShifts2() {
        ge.updateShifts(ShiftsRequest2);
        assertFalse(Arrays.deepEquals(ShiftsRequest1,ge.getShiftsRequest()),"expected false");
    }
}