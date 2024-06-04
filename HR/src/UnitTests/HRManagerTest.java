package UnitTests;
import Domain.*;
import java.util.*;
import java.util.Date;
import org.junit.*;

public class HRManagerTest {
    public HRManager hrm;
    public Employee e;
    public List<Role> roles;
    public Role cashier;
    public Role storekeeper;
    public Branch branch;
    public Network network;
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public Set<Integer>[][] employeesShifts_allTrueForShai;

    @Before
    public void initTest() {
        this.network = new Network();
        this.branch = new Branch("Mazkeret", "Mazkeret Batya", this.network);
        this.cashier = new Role("cashier", (List)null, this.branch);
        this.storekeeper = new Role("storekeeper", (List)null, this.branch);
        this.roles = new LinkedList();
        this.roles.add(this.cashier);
        this.roles.add(this.storekeeper);
        this.network = new Network();
        //this.e = new GeneralEmployee(0, "shai", 66666666, 500, new Date(2024, 5, 31), "half", 1, this.roles, false, this.branch);
        this.hrm = new HRManager(123456788, "Tomer", "123456789", 30, new Date(2024, 4, 31),null,"half", 30,"1234");
        ShiftsRequest1 = new boolean[Network.shifts][Network.days];
        ShiftsRequest2 = new boolean[Network.shifts][Network.days];
        employeesShifts_allTrueForShai = new Set[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++) {
            for (int j = 0; j < Network.days; j++) {
                ShiftsRequest1[i][j] = true;
                ShiftsRequest2[i][j] = false;
                employeesShifts_allTrueForShai[i][j]= new HashSet<Integer>();
                employeesShifts_allTrueForShai[i][j].add(123456789);

            }
        }

    }

    @Test
    public void addEmployee0() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isExist(123456789));
    }
    @Test
    public void addEmployee1() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Full", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isExist(123456789));//changed part of job
    }
    @Test
    public void addEmployee2() {
        this.hrm.addEmployee(123456, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456));//incorrect id

    }
    @Test
    public void addEmployee3() {
        this.hrm.addEmployee(123456789, "Shai3", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect name
    }
    @Test
    public void addEmployee4() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "123", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails
    }
    @Test
    public void addEmployee5() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "1231231231231223232", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails2
    }
    @Test
    public void addEmployee6() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12341234s", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails3
    }
    @Test
    public void addEmployee7() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 0, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect salary
    }
    @Test
    public void addEmployee8() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(0));//incorrect partOfJob
    }

    @Test
    public void addEmployee9() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", -1, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist(123456789));//incorrect vacation
    }
    @Test
    public void addEmployee10() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addEmployee(123456789, "Tomer Cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertFalse(branch.isExist("Tomer Cohen"));
    }

    @Test
    public void addEmployee11() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addEmployee(123456788, "Tomer Cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertTrue(branch.isExist(123456789));
        Assert.assertTrue(branch.isExist(123456788));

    }
    @Test
    public void UpdateEmployee0() {
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "12345678", 500, new Date(2024, Calendar.JANUARY, 31) ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isExist("Shai Hubashi change"));

    }
    @Test
    public void UpdateEmployee1() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "123456789", 500, new Date(2024, Calendar.JANUARY, 31) ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isExist("Shai Hubashi change"));
        Assert.assertFalse(branch.isExist("Shai Hubashi"));

    }

    @Test
    public void updateShiftsOfBranch() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        GeneralEmployee shai = this.branch.getGeneralEmployee(123456789);
        shai.updateShifts(ShiftsRequest1);

        this.hrm.updateShiftsOfBranch(branch);

        Assert.assertArrayEquals(this.branch.getEmployeesShifts(),employeesShifts_allTrueForShai);
    }
}
