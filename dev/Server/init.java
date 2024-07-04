package Server;

import DataLayer.IMP.*;
import DataLayer.interfaces.EmployeeDao;
import DataLayer.interfaces.NetworkRepository;
import Domain.*;

import java.util.ArrayList;
import java.util.List;

public class init {
    public static Network init(boolean init) {

        NetworkRepository NR = new NetworkRepositoryImp();
        NR.delete();//delete?

        HRManager hrm = new HRManager(111111111, "Shai Hubashi", "11111111111", 50, "04-06-2024", null, "Full", 18, "1111");
        Network network = Network.createNewNetwork(hrm);
        List<String> GeneralEmployeeAccess = new ArrayList<>();
        network.addRole(new Role("cashier", GeneralEmployeeAccess));
        network.addRole(new Role("storekeeper", GeneralEmployeeAccess));
        List<String> DriverAccess = new ArrayList<>();
        network.addRole(new Role("driver", DriverAccess));
        hrm.addBranch("", "", null);
        if (init) {
            Branch branch = hrm.addBranch("Beer Sheva", "Beer Sheva", null);
            BranchManager bm = hrm.addBranchManager(222222222, "Tomer Cohen", "2222222222", 50,"04-06-2024", null, "Half", 18, branch, "2222");
            List<Role> roleList = Network.getNetwork().getRoles();
            roleList.remove(Network.getNetwork().getRole("driver"));
            List<Role> oneRole = new ArrayList<>();
            oneRole.add(roleList.get(1));
            EmployeeDao GeneralDao = new GeneralEmployeeDao();
            EmployeeDao DriverDao = new DriverDao();
            EmployeeDao BranchManagerDao = new BranchManagerDao();
            EmployeeDao HRMDao = new HRManagerDao();
            hrm.addGeneralEmployee(444444444, "Shelly atanelov", "9999995555", 35,"05-06-2024",null,"Full",18 , oneRole, false, branch,"4444" );
            hrm.addGeneralEmployee(333333333, "Shahar Bar", "9999999999", 50,"04-06-2024",null,"Half",18 , roleList, true, branch,"3333");
            List<String> licenseTypes = new ArrayList<>();
            licenseTypes.add("A");
            licenseTypes.add("B");
            hrm.addDriver(555555555, "Illy Hason", "9999999999", 35,"05-06-2024",null,"Full",18 , branch,"5555",1234567, licenseTypes);


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


        }

        return network;
    }
}
