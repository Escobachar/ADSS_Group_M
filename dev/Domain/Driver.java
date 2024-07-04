package Domain;

import DataLayer.IMP.DriverDao;
import DataLayer.interfaces.EmployeeDao;

import java.util.*;

public class Driver extends GeneralEmployee {
    private Integer driverLicense;
    private List<String> driverLicenseTypes;
    private EmployeeDao DriverDao = new DriverDao();

    public Driver(int ID, String name, String bankAccountDetails, int salary,String  startOfEmployment, String endOfEmployment, String partOfJob, int vacationsDays, Branch branch, String password,
                  Integer driverLicense,List<String> driverLicenseTypes) {
        List<Role> role = new LinkedList<>();
        role.add(Network.getNetwork().getRole("driver"));
        createConstuctor(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,role, false, branch, password);
        this.getAccess().add("ShowAviStoreKeeper");
        this.driverLicense=driverLicense;
        this.driverLicenseTypes=driverLicenseTypes;
    }
    public Driver(int ID, String name, String bankAccountDetails, int salary, String startOfEmployment, String partOfJob, int vacationsDays, Branch branch, String password,
                  Integer driverLicense,List<String> driverLicenseTypes) {
        List<Role> role = new LinkedList<>();
        createConstuctor(ID, name, bankAccountDetails, salary, startOfEmployment,null, partOfJob, vacationsDays, role, false, branch, password);
        this.driverLicense=driverLicense;
        this.driverLicenseTypes=driverLicenseTypes;
    }

    public Integer getDriverLicense() {
        return driverLicense;
    }
    public void setDriverLicense(Integer driverLicense) {
        this.driverLicense = driverLicense;
    }
    public List<String> getDriverLicenseTypes() {
        return driverLicenseTypes;
    }
    public void setDriverLicenseTypes(List<String> driverLicenseTypes) {
        this.driverLicenseTypes = driverLicenseTypes;
    }
    public void DBUpdateDetails(){
        DriverDao.update(this);

    }
}
