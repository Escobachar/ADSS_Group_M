package UnitTests;
import Domain.*;
import java.util.*;
import java.util.Date;
import org.junit.*;

public class HRManagerTest {
    public HRManager hrm;
    public Employee e;
    public List<Role> roles;
    public Branch branch;
    public Network network;
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public Set<Integer>[][] employeesShifts_allTrueForShai;

    @Before
    public void initTest() {
        hrm = new HRManager(111111111, "shelly", "11111111111", 50, new Date(2024, 6, 4), null, "Full", 18, "1111");
        this.network = new Network(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier",GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper",GeneralEmployeeAccess));
        this.branch = hrm.addBranch("Beer Sheva", "Beer Sheva",null);
        BranchManager bm = new BranchManager(123456788,"tomer cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), null,"half", 18,branch ,"1234",network);
        roles=network.getRoles();
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
    public void addEmployee1() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Full", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isEmployeeExist(123456789));//changed part of job
    }
    @Test
    public void addEmployee2() {
        this.hrm.addGeneralEmployee(123456, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456));//incorrect id

    }
    @Test
    public void addEmployee3() {
        this.hrm.addGeneralEmployee(123456789, "Shai3", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect name
    }
    @Test
    public void addEmployee4() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "123", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails
    }
    @Test
    public void addEmployee5() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "1231231231231223232", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails2
    }
    @Test
    public void addEmployee6() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12341234s", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails3
    }
    @Test
    public void addEmployee7() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 0, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect salary
    }
    @Test
    public void addEmployee8() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(0));//incorrect partOfJob
    }

    @Test
    public void addEmployee9() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", -1, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect vacation
    }
    @Test
    public void addEmployee10() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addGeneralEmployee(123456789, "Tomer Cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertFalse(branch.isEmployeeExist("Tomer Cohen"));
    }

    @Test
    public void addEmployee11() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addGeneralEmployee(123456788, "Tomer Cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertTrue(branch.isEmployeeExist(123456789));
        Assert.assertTrue(branch.isEmployeeExist(123456788));

    }
    @Test
    public void UpdateEmployee0() {
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "12345678", 500, new Date(2024, Calendar.JANUARY, 31) ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist("Shai Hubashi change"));

    }
    @Test
    public void UpdateEmployee1() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "123456789", 500, new Date(2024, Calendar.JANUARY, 31) ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isEmployeeExist("Shai Hubashi change"));
        Assert.assertFalse(branch.isEmployeeExist("Shai Hubashi"));

    }
}
