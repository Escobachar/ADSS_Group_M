package Server;

import Domain.*;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;

public class init {
    public static Network init(boolean init){
        HRManager hrm = new HRManager(111111111, "HR Manager", "11111111111", 50, new Date(2024, 6, 4), null, "Full", 18, "1111");
        Network network = new Network(hrm);
        if(init) {
            Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva",null);
            BranchManager bm = new BranchManager(222222222, "Branch Manager", "2222222222", 50, new Date(2024, 6, 4), null, "Half", 18,branch,"2222");
            List<Role> roleList = hrm.getNetwork().getRoles();
            List<Role> oneJob = new ArrayList<>();
            oneJob.add(roleList.get(0));
            GeneralEmployee ge1 = hrm.addGeneralEmployee(333333333, "General Employee first", "3333333333", 40, new Date(2024, 6, 4), null, "Full", 10, roleList , true, branch, "3333");
            GeneralEmployee ge2 = hrm.addGeneralEmployee(444444444, "General Employee second", "444444444", 40, new Date(2024, 6, 4), null, "Half", 10, roleList , true, branch, "4444");
        }
        return network;
    }
}
