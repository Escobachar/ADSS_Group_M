package UnitTests;
import Domain.*;
import java.util.*;
import java.util.Date;
import org.junit.*;
import org.junit.jupiter.api.Assertions;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class HRManagerTest {
    public HRManager hrm;
    public Employee e;
    public List<Role> roles;

    public Role cashier;
    public Role storekeeper;
    public Branch branch;
    public Network network;


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
        this.hrm = new HRManager(1, "Tomer", "66666667", 30, new Date(2024, 4, 31),null,"half", 30);
    }

    @Test
    public void addEmployee0() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertTrue(branch.isExist(123456789));
    }
    public void addEmployee1() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Part", 18, this.roles, false, this.branch);
        assertTrue(branch.isExist(123456789));
    }
    public void addEmployee2() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect id

    }
    public void addEmployee3() {
        this.hrm.addEmployee(123456789, "Shai", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect name
    }
    public void addEmployee4() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "123", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails
    }

    public void addEmployee5() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12312312312312", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails2
    }

    public void addEmployee6() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "1234", 500, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect bankAccountDetails3
    }
    public void addEmployee7() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 0, new Date(2024, Calendar.JANUARY, 31), "Half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect salary
    }
    public void addEmployee8() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(0));//incorrect partOfJob
    }
    public void addEmployee9() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "half", 18, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect partOfJob
    }
    public void addEmployee10() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "half", 0, this.roles, false, this.branch);
        assertFalse(branch.isExist(123456789));//incorrect vacation
    }

    public void addEmployee11() {
        this.hrm.addEmployee(123456789, "Shai Hubashi", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "half", 0, this.roles, false, this.branch);
        this.hrm.addEmployee(123456789, "Tomer Cohen", "12345678", 500, new Date(2024, Calendar.JANUARY, 31), "half", 0, this.roles, false, this.branch);

        //assertTrue();
    }

    @Test
    public void UpdateEmployee0() {

    }
    public void UpdateEmployee1() {

    }

    @Test
    public void updateShiftsOfBranch() {

    }
}
