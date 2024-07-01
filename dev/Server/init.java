package Server;

import DataLayer.*;
import Domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class init {
    public static Network init(boolean init) {

        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        DriverAccess.add("ShowAviStoreKeeper");
        network.addRole(new Role("driver", DriverAccess));
        if (init) {
            Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
            BranchManager bm = new BranchManager(222222222, "Tomer Cohen", "2222222222", 50,"04-06-2024", null, "Half", 18, branch, "2222");
            List<Role> roleList = Network.getNetwork().getRoles();
            List<Role> oneRole = new ArrayList<>();
            oneRole.add(roleList.get(1));
            EmployeeDao test = new GeneralEmployeeDao();


            test.delete(444444444);
            //test.update(333333333, "Shahar Bar", "9999999999", 50,"04-06-2024",null,"Half",18 , roleList, true, branch,"3333" );
            //hrm.addGeneralEmployee(444444444, "Shelly atanelov", "9999995555", 35,"05-06-2024",null,"Full",18 , roleList, false, branch,"4444" );


            //GeneralEmployee ge2 = (GeneralEmployee)test.read(444444444);

        }
        return network;
    }
}
