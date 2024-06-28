package Server;

import Domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class init {
    public static Network init(boolean init){
        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, new Date(2024, 6, 4), null, "Full", 18, "1111");
        Network network = new Network(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier",GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper",GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        DriverAccess.add("ShowAviStoreKeeper");
        network.addRole(new Role("driver",DriverAccess));
        if(init) {
            Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
            BranchManager bm = new BranchManager(222222222, "Tomer Cohen", "2222222222", 50, new Date(2024, 6, 4), null, "Half", 18, branch, "2222", network);
            List<Role> roleList = hrm.getNetwork().getRoles();
            List<Role> oneRole = new ArrayList<>();
            oneRole.add(roleList.get(1));
            GeneralEmployee ge1 = hrm.addGeneralEmployee(333333333, "Shahar Bar", "3333333333", 40, new Date(2024, 6, 4), null, "Full", 10, roleList, true, branch, "3333");
            GeneralEmployee ge2 = hrm.addGeneralEmployee(444444444, "Shelly", "444444444", 40, new Date(2024, 6, 4), null, "Half", 10, oneRole, false, branch, "4444");
        }
        return network;
    }
}
