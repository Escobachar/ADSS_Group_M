package Server;

import DataLayer.*;
import Domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class init {
    public static Network init(boolean init) {

        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, new Date(2024, 6, 4), null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        DriverAccess.add("ShowAviStoreKeeper");
        network.addRole(new Role("driver", DriverAccess));
        if (init) {
            Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
            BranchManager bm = new BranchManager(222222222, "Tomer Cohen", "2222222222", 50, new Date(2024, 6, 4), null, "Half", 18, branch, "2222", network);
            List<Role> roleList = hrm.getNetwork().getRoles();
            List<Role> oneRole = new ArrayList<>();
            oneRole.add(roleList.get(1));
            EmployeeDao test = new GeneralEmployeeDao();
            GeneralEmployee ge1 = (GeneralEmployee)test.read(333333333);
            GeneralEmployee ge2 = (GeneralEmployee)test.read(444444444);        }
        return network;
    }
}
