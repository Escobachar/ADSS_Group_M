package Server;

import Domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class init {
    public static Network init(boolean init){
        HRManager hrm = new HRManager(209165737, "Shai Hubashi", "123456789876", 50, new Date(2024, 6, 4), null, "Half", 18, "1111");
        Network network = new Network(hrm);
        if(init) {
            Branch Beer_Sheva = hrm.addBranch("Beer Sheva", "Beer Sheva",null);
            BranchManager bm = new BranchManager(209165737, "Tomer Cohen", "123456789877", 50, new Date(2024, 6, 4), null, "Half", 18,Beer_Sheva,"2222");
            List<String> none = new LinkedList<>();
            List<Role> geRoles = new LinkedList<>();
            Role cashier = new Role("cashier", none, Beer_Sheva);
            Role storekeeper = new Role("storekeeper", none, Beer_Sheva);
            geRoles.add(cashier);
            geRoles.add(storekeeper);
            GeneralEmployee ge1 = (GeneralEmployee) hrm.addGeneralEmployee(209165777, "Idan Amedy", "123456788888", 40, new Date(2024, 6, 4), null, "Half", 10, geRoles , true, Beer_Sheva, "3333");
        }
        return network;
    }
}
