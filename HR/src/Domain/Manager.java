package Domain;

import java.util.*;

abstract public class Manager extends Employee{
    private Network network;
    public Network getNetwork(){return network;}

    public Manager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment , Date endOfEmployment, String partOfJob, int vacationsDays,String password,Network network  ) {
        super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays,password);
        this.network=network;
    }

    public GeneralEmployee addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment,String partOfJob,int vacationsDays,
                                              List<Role> roles,boolean isManager,Branch branch,String password){
        return addGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,roles,isManager,branch,password);
    }

    public GeneralEmployee addGeneralEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,
                                              int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password){
        List<Employee> el=branch.getEmployeesList();
        if(Network.checkGeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch.getBranchName(),network))
        {
            for (Employee e : el) {
                if (e.getID() == ID)
                    return null;
            }
            GeneralEmployee ge= new GeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch,password);
            el.add(ge);
            return ge;
        }
        return null;
    }


    public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch,String password  ) {
        if (Network.checkGeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch.getBranchName(),network)) {
            List<Employee> gel = branch.getEmployeesList();
            branch.SearchGeneralEmployee(ID).copyGeneralEmployee(new GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch, password));
        }
    }

//    public void updateShiftsOfBranch(Branch branch){
//        Set<String>[][] employeesShifts = new Set[Network.shifts][Network.days];
//        for (int i = 0; i < Network.shifts; i++)
//            for (int j = 0; j < Network.days; j++)
//                employeesShifts[i][j]=new HashSet<String>();
//
//        for(Employee e:branch.getEmployeesList()){
//            if(e instanceof GeneralEmployee) {
//                GeneralEmployee ge=(GeneralEmployee)e;
//                for (int i = 0; i < ge.getShiftsRequest().length; i++) {
//                    for (int j = 0; j < ge.getShiftsRequest()[i].length; j++) {
//                        if (ge.getShiftsRequest()[i][j])
//                            employeesShifts[i][j].add(ge.getName());
//                    }
//                }
//            }
//        }
//        branch.setEmployeesShifts(employeesShifts);
//    }

    public void updateRolesOfShiftsOfBranch(Branch branch, HashMap<Role,Integer[][]> rolesOfShifts) {
        branch.setRolesOfShifts(rolesOfShifts);
    }

    public void updateEmployeeRole(GeneralEmployee employee, List<Role> roles){
        employee.setRoles(roles);
    }
    public void setNetwork(Network network){this.network=network;}
}
