package Server;

import DataLayer.IMP.*;
import DataLayer.interfaces.EmployeeDao;
import DataLayer.interfaces.NetworkRepository;
import Domain.*;

import java.util.*;

public class init {
    public static Network init() {

        NetworkRepository NR = new NetworkRepositoryImp();
        NR.delete();//delete

        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        network.addRole(new Role("driver", DriverAccess));
        hrm.addBranch("", "", null);


        List<Role> roleList = Network.getNetwork().getRoles();
        roleList.remove(network.getRole("driver"));
        List<Role> oneRole = new ArrayList<>();
        oneRole.add(roleList.get(1));
        List<String> licenseTypes = new ArrayList<>();
        licenseTypes.add("A");
        licenseTypes.add("B");

        EmployeeDao GeneralDao = new GeneralEmployeeDao();
        EmployeeDao DriverDao = new DriverDao();
        EmployeeDao BranchManagerDao = new BranchManagerDao();
        EmployeeDao HRMDao = new HRManagerDao();

        boolean[][] shiftRequest1 = new boolean[Network.shifts][Network.days];//can not work
        boolean[][] shiftRequest2 = new boolean[Network.shifts][Network.days];//can work always
        boolean[][] shiftRequest3 = new boolean[Network.shifts][Network.days];//can work only morning

        for (int i = 0; i < Network.shifts; i++) {
            for (int j = 0; j < Network.days; j++) {
                shiftRequest1[i][j] = false;
                shiftRequest2[i][j] = true;
                if(i == 0) shiftRequest3[i][j] = true;
                else shiftRequest3[i][j] = false;
            }
        }

        //branch1
        Branch branch1 = hrm.addBranch("Beer Sheva", "Beer Sheva", null);

        BranchManager bm1 = hrm.addBranchManager(222222222, "Tomer Cohen", "2222222222", 50,"04-06-2024", null, "Half", 18, branch1, "2222");

        GeneralEmployee ge1_b1 = hrm.addGeneralEmployee(444444444, "Shelly atanelov", "9999995555", 35,"05-06-2024",null,"Full",18 , oneRole, false, branch1,"4444" );
        GeneralEmployee ge2_b1 = hrm.addGeneralEmployee(333333333, "Shahar Bar", "9999999999", 50,"04-06-2024",null,"Half",18 , roleList, true, branch1,"3333");
        Driver d1_b1 = hrm.addDriver(555555555, "Illy Hason", "9999999999", 35,"05-06-2024",null,"Full",18 , branch1,"5555",1234567, licenseTypes);
        GeneralEmployee ge3_b1 = hrm.addGeneralEmployee(111222111, "Yuval Bahar", "9999995555", 50,"05-06-2024",null,"Full",18 , roleList, true, branch1,"1212" );

        ge1_b1.updateShifts(shiftRequest1);
        ge2_b1.updateShifts(shiftRequest2);
        d1_b1.updateShifts(shiftRequest3);
        ge3_b1.updateShifts(shiftRequest2);

        //branch2
        Branch branch2 = hrm.addBranch("Mazkeret Batya", "Mazkeret Batya", null);

        BranchManager bm2 = hrm.addBranchManager(666666666, "Yael Hubashi", "6666666666", 50,"04-06-2024", null, "Half", 18, branch2, "6666");

        GeneralEmployee ge1_b2 = hrm.addGeneralEmployee(777777777, "Efrat Hubashi", "9999995555", 50,"05-06-2024",null,"Full",18 , oneRole, false, branch2,"7777" );
        GeneralEmployee ge2_b2 = hrm.addGeneralEmployee(888888888, "Eran Hubashi", "9999999999", 50,"04-06-2024",null,"Half",18 , roleList, true, branch2,"8888");
        Driver d1_b2 = hrm.addDriver(999999999, "Omri Hubashi", "9999999999", 35,"05-06-2024",null,"Full",18 , branch2,"9999",1234567, licenseTypes);
        GeneralEmployee ge3_b2 = hrm.addGeneralEmployee(111000111, "Moka Hubashi", "9999995555", 50,"05-06-2024",null,"Full",18 , roleList, true, branch2,"1010" );

        ge1_b2.updateShifts(shiftRequest1);
        ge2_b2.updateShifts(shiftRequest2);
        d1_b2.updateShifts(shiftRequest3);
        ge3_b2.updateShifts(shiftRequest2);

            /*
            GeneralEmployee shelly = (GeneralEmployee)GeneralDao.read(444444444);
            System.out.println(shelly.getName());
            shelly.setSalary(50);
            GeneralDao.update(shelly);
            shelly = (GeneralEmployee)GeneralDao.read(444444444);
            System.out.println("update was good? :"+ (shelly.getSalary()==50));

            GeneralEmployee Shahar = (GeneralEmployee)GeneralDao.read(333333333);
            System.out.println(Shahar.getName());
            branch = hrm.addBranch("yavne", "yavne", null);
            bm = hrm.addBranchManager(888888888, "ido Cohen", "888888888", 35,"04-06-2024", null, "Half", 18, branch, "8888");
            Shahar.setBranch(branch);
            GeneralDao.update(Shahar);
            Shahar = (GeneralEmployee)GeneralDao.read(333333333);
            System.out.println("update was good? :"+ (Shahar.getBranch().getBranchName().equals("yavne")));

            HRManager shai = (HRManager)HRMDao.read(111111111);
            System.out.println(shai.getName());
            shai.setSalary(100);
            HRMDao.update(shai);
            shai = (HRManager)HRMDao.read(111111111);
            System.out.println("update was good? :"+ (shai.getSalary()==100));

            BranchManager Tomer = (BranchManager)BranchManagerDao.read(222222222);
            System.out.println(Tomer.getName());
            Tomer.setBranch(branch);
            BranchManagerDao.update(Tomer);
            Tomer = (BranchManager)BranchManagerDao.read(222222222);
            System.out.println("update was good? :"+ (Tomer.getBranch().getBranchName().equals("yavne")));

            Driver Illy = (Driver)DriverDao.read(555555555);
            System.out.println(Illy.getName());
            licenseTypes = new ArrayList<>();
            licenseTypes.add("C");
            licenseTypes.add("D");
            Illy.setDriverLicenseTypes(licenseTypes);
            DriverDao.update(Illy);
            Illy = (Driver)DriverDao.read(555555555);
            System.out.println("update was good? :"+ (Illy.getDriverLicenseTypes().get(0).equals("C")));
*/

        return network;
    }
}

