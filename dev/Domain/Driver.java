package Domain;

import java.util.*;

public class Driver extends GeneralEmployee {
    private String driverLicense;
    private List<String> driverLicenseTypes;

    public Driver(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, Date endOfEmployment, String partOfJob, int vacationsDays, Branch branch, String password,Network network,
                  String driverLicense,List<String> driverLicenseTypes) {
        List<Role> role = new LinkedList<>();
        role.add(network.getRole("driver"));
        createConstuctor(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,role, false, branch, password);
        this.driverLicense=driverLicense;
        this.driverLicenseTypes=driverLicenseTypes;
    }
    public Driver(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment, String partOfJob, int vacationsDays, Branch branch, String password,Network network,
                  String driverLicense,List<String> driverLicenseTypes) {
        List<Role> role = new LinkedList<>();
        role.add(network.getRole("driver"));
        createConstuctor(ID, name, bankAccountDetails, salary, startOfEmployment,null, partOfJob, vacationsDays, role, false, branch, password);
        this.driverLicense=driverLicense;
        this.driverLicenseTypes=driverLicenseTypes;
    }

    public String getDriverLicense() {
        return driverLicense;
    }
    public void setDriverLicense(String driverLicense) {
        this.driverLicense = driverLicense;
    }
    public List<String> getDriverLicenseTypes() {
        return driverLicenseTypes;
    }
    public void setDriverLicenseTypes(List<String> driverLicenseTypes) {
        this.driverLicenseTypes = driverLicenseTypes;
    }
}
