package DataLayer;

import Domain.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.stream.*;
import java.util.List;

public class BranchRepositoryImp implements BranchRepository {
    private BranchDao branchDao=new BranchDaoImp();
    private EmployeeDao branchManagerDao = new BranchManagerDao();
    private EmployeeDao HRManagerDao = new HRManagerDao();
    private EmployeeDao driverDao = new DriverDao();
    private EmployeeDao generalEmployeeDao = new GeneralEmployeeDao();
    private RoleOfShiftsDao roleOfShiftsDao = new RoleOfShiftsDaoImp();
    private ShiftAvailabilityDao shiftAvailabilityDao = new ShiftAvailabilityDaoImp();
    private EmployeeShiftsDao employeeShiftsDao = new EmployeeShiftsImp();
    private HistoryOfEmployeesShiftsDao historyOfEmployeesShiftsDao = new HistoryOfEmployeesShiftsDaoImp();
    @Override
    public void create(Branch branch) {
        branchDao.create(branch);
        createList(branch);
        String branchName = branch.getBranchName();
        roleOfShiftsDao.create(branchName,branch.getRolesOfShifts());
        shiftAvailabilityDao.create(branchName,branch.getShiftsAvailability());
        historyOfEmployeesShiftsDao.create(branchNameranch.getHistoryEmployeesShifts());
        employeeShiftsDao.create(branchName,branch.getEmployeesShifts());
    }
    private void createList(Branch branch) {
        for(Employee e : branch.getEmployeesList()) {
            if (e instanceof Driver)
                driverDao.create(e);
            else if (e instanceof GeneralEmployee)
                generalEmployeeDao.create(e);
        }
    }
    private void deleteList(Branch branch) {
        for(Employee e : branch.getEmployeesList()) {
            if (e instanceof Driver)
                driverDao.delete(e.getID());
            else if (e instanceof GeneralEmployee)
                generalEmployeeDao.delete(e.getID());
        }
    }


    @Override
    public Branch get(String branchName) {
        Branch branch = branchDao.read(branchName);
        List<Employee> employeeList = Stream.concat(driverDao.readAll(branchName).stream(), generalEmployeeDao.readAll(branchName).stream()).toList();
        employeeList.add(branch.getBranchManager());
        branch.setRolesOfShifts(roleOfShiftsDao.read(branchName));
        branch.setShiftsAvailability(shiftAvailabilityDao.read(branchName));
        branch.setHistoryEmployeesShifts(historyOfEmployeesShiftsDao.read(branchName));
        branch.setEmployeesShifts(employeeShiftsDao.read(branchName));
        return branch;
    }

    @Override
    public void update(Branch branch, String whatToUpdate) {
        String branchName = branch.getBranchName();
        if(whatToUpdate.contains("List")) {
            delete(branch,"List");
            createList(branch);
        }
        if(whatToUpdate.contains("info")) {
            delete(branch,"info");
            branchDao.create(branch);
        }
        if(whatToUpdate.contains("roleOfShifts")) {
            delete(branch,"roleOfShifts");
            roleOfShiftsDao.create(branchName,branch.getRolesOfShifts());
        }
        if(whatToUpdate.contains("shiftAvailability")) {
            delete(branch,"shiftAvailability");
            shiftAvailabilityDao.create(branchName,branch.getShiftsAvailability());
        }
        if(whatToUpdate.contains("historyOfEmployeesShifts")) {
            delete(branch,"historyOfEmployeesShifts");
            historyOfEmployeesShiftsDao.create(branchNameranch.getHistoryEmployeesShifts());
        }
        if(whatToUpdate.contains("employeeShifts")) {
            delete(branch,"employeeShifts");
            employeeShiftsDao.create(branchName,branch.getEmployeesShifts());
        }
    }

    @Override
    public void delete(Branch branch, String whatToDelete) {
        String branchName = branch.getBranchName();
        if(whatToDelete.contains("List")) {
            deleteList(branch);
        }
        if(whatToDelete.contains("info")) {
            branchDao.delete(branchName);
        }
        if(whatToDelete.contains("roleOfShifts")) {
            roleOfShiftsDao.delete(branchName);
        }
        if(whatToDelete.contains("shiftAvailability")) {
            shiftAvailabilityDao.delete(branchName);
        }
        if(whatToDelete.contains("historyOfEmployeesShifts")) {
            historyOfEmployeesShiftsDao.delete(branchName);
        }
        if(whatToDelete.contains("employeeShifts")) {
            employeeShiftsDao.delete(branchName);
        }
    }
}
