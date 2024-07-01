package UnitTests;
import Domain.*;
import java.util.*;
import java.util.Date;
import org.junit.*;

public class HRManagerTest {
    public HRManager hrm;
    public BranchManager bm;
    public GeneralEmployee ge1;
    public GeneralEmployee ge2;
    public Role cashier;
    public Role storekeeper;
    public List<Role> roles;
    public Branch branch;
    public Network network;
    public boolean[][] ShiftsRequest1;
    public boolean[][] ShiftsRequest2;
    public Set<Integer>[][] employeesShifts_allTrueForShai;
    public HashMap<Role, Integer[][]> rolesOfShifts;
    @Before
    public void initTest() {
        hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<String>();
        cashier= new Role("cashier",GeneralEmployeeAccess);
        storekeeper= new Role("storekeeper",GeneralEmployeeAccess);
        network.addRole(cashier);
        network.addRole(storekeeper);
        branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
        bm = new BranchManager(222222222, "Tomer Cohen", "2222222222", 50, "04-06-2024", null, "Half", 18, branch, "2222");
        List<Role> roleList = Network.getNetwork().getRoles();
        List<Role> oneRole = new ArrayList<>();
        oneRole.add(roleList.get(1));
        ge1 = hrm.addGeneralEmployee(333333333, "Shahar Bar", "3333333333", 40, "04-06-2024", null, "Full", 10, roleList, true, branch, "3333");
        ge2 = hrm.addGeneralEmployee(444444444, "Shelly", "444444444", 40, "04-06-2024", null, "Half", 10, oneRole, false, branch, "4444");

        rolesOfShifts= new HashMap<>();
        //need 1 role in every shift:
        for(Role r: network.getRoles()) {
            rolesOfShifts.put(r, new Integer[Network.shifts][Network.days]);
            for (int i = 0; i < Network.shifts; i++) {
                rolesOfShifts.get(r)[i] = new Integer[Network.days];
                for (int j = 0; j < Network.days; j++) {
                    rolesOfShifts.get(r)[i][j] = 3;
                }
            }
        }
        hrm.SetRolesOfShiftsOfBranch(branch,rolesOfShifts);


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
    /*
    @Test
    public void addEmployee1() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, "04-06-2024", "Full", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isEmployeeExist(123456789));//changed part of job
    }
    @Test
    public void addEmployee2() {
        this.hrm.addGeneralEmployee(123456, "Shai Hubashi", "12345678", 500, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456));//incorrect id

    }
    @Test
    public void addEmployee3() {
        this.hrm.addGeneralEmployee(123456789, "Shai3", "12345678", 500, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect name
    }
    @Test
    public void addEmployee4() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "123", 500, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails
    }
    @Test
    public void addEmployee5() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "1231231231231223232", 500, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails2
    }
    @Test
    public void addEmployee6() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12341234s", 500, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect bankAccountDetails3
    }
    @Test
    public void addEmployee7() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 0, "04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect salary
    }
    @Test
    public void addEmployee8() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500,"04-06-2024", "Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(0));//incorrect partOfJob
    }

    @Test
    public void addEmployee9() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, "04-06-2024", "Half", -1, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist(123456789));//incorrect vacation
    }
    @Test
    public void addEmployee10() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, "04-06-2024", "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addGeneralEmployee(123456789, "Tomer Cohen", "12345678", 500, "04-06-2024", "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertFalse(branch.isEmployeeExist("Tomer Cohen"));
    }

    @Test
    public void addEmployee11() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, "04-06-2024", "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.addGeneralEmployee(123456788, "Tomer Cohen", "12345678", 500, "04-06-2024", "Half", 0, this.roles, false, this.branch,"1234");

        Assert.assertTrue(branch.isEmployeeExist(123456789));
        Assert.assertTrue(branch.isEmployeeExist(123456788));

    }
    @Test
    public void UpdateEmployee0() {
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "12345678", 500, "04-06-2024" ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertFalse(branch.isEmployeeExist("Shai Hubashi change"));

    }
    @Test
    public void UpdateEmployee1() {
        this.hrm.addGeneralEmployee(123456789, "Shai Hubashi", "12345678", 500, "04-06-2024", "Half", 0, this.roles, false, this.branch,"1234");
        this.hrm.UpdateEmployee(123456789, "Shai Hubashi change", "123456789", 500, "04-06-2024" ,null,"Half", 18, this.roles, false, this.branch,"1234");
        Assert.assertTrue(branch.isEmployeeExist("Shai Hubashi change"));
        Assert.assertFalse(branch.isEmployeeExist("Shai Hubashi"));

    }
*/
    @Test
    public void UpdateShifts1() {//success
        ge1.updateShifts(ShiftsRequest1);
        hrm.updateShift(ge1, cashier, branch, 1,1);
        HashMap<Integer, Role> theShiftToTest= branch.getEmployeesShifts()[1][1];
        HashMap<String, Role> theWantedShift = new HashMap<>();
        theWantedShift.put("Shahar Bar",cashier);
        Assert.assertEquals(theShiftToTest,theWantedShift);
    }
    @Test
    public void UpdateShifts2() {//Fail
        ge1.updateShifts(ShiftsRequest2);//all false
        hrm.updateShift(ge1, cashier, branch, 1,1);//should fail
        HashMap<Integer, Role> theShiftToTest= branch.getEmployeesShifts()[1][1];
        HashMap<String, Role> theWantedShift = new HashMap<>();
        Assert.assertEquals(theShiftToTest,theWantedShift);
    }
}
