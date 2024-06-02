package Domain;

import java.util.*;

abstract public class Manager extends Employee{
        private List<String> access;
        private Network network;
        public Manager(int ID, String name, String bankAccountDetails, int salary, Date startOfEmployment , Date endOfEmployment, String partOfJob, int vacationsDays  ) {
            super(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays);
        }

        public void addEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch){
            addEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,null,partOfJob,vacationsDays,roles,isManager,branch);
        }

        public void addEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch  ){
            List<Employee> el=branch.getEmployeesList();
            if(Network.CheckAddEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch))
            {
                for (Employee e : el) {
                    if (e.getID() == ID)
                        return;
                }
                GeneralEmployee ge= new GeneralEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch);

                el.add(ge);
            }
        }

        public void UpdateEmployee(int ID,String name, String bankAccountDetails, int salary,Date startOfEmployment ,Date endOfEmployment,String partOfJob,int vacationsDays,List<Role> roles,boolean isManager,Branch branch  ){
            if(Network.CheckAddEmployee(ID,name,bankAccountDetails,salary,startOfEmployment,endOfEmployment,partOfJob,vacationsDays,roles,isManager,branch)) {
                List<Employee> gel = branch.getEmployeesList();
                branch.getGeneralEmployee(ID).copyGeneralEmployee(new GeneralEmployee(ID, name, bankAccountDetails, salary, startOfEmployment, endOfEmployment, partOfJob, vacationsDays, roles, isManager, branch));
            }
        }

        public void updateShiftsOfBranch(Branch branch){
            Set<Integer>[][] employeesShifts = new Set[Network.shifts][Network.days];
            for (int i = 0; i < Network.shifts; i++)
                for (int j = 0; j < Network.days; j++)
                    employeesShifts[i][j]=new HashSet<Integer>();

            for(Employee e:branch.getEmployeesList()){
                if(e instanceof GeneralEmployee) {
                    GeneralEmployee ge=(GeneralEmployee)e;
                    for (int i = 0; i < ge.getShiftsRequest().length; i++) {
                        for (int j = 0; j < ge.getShiftsRequest()[i].length; j++) {
                            if (ge.getShiftsRequest()[i][j])
                                employeesShifts[i][j].add(ge.getID());
                        }
                    }
                }
            }
            branch.setEmployeesShifts(employeesShifts);
        }

        public void updateShiftsOfBranch(Branch branch, HashMap<Role,Integer[][]> rolesOfShifts) {
            branch.setRolesOfShifts(rolesOfShifts);
        }

        public void updateEmployeeRole(GeneralEmployee employee, List<Role> roles){
            employee.setRoles(roles);
        }
    }
