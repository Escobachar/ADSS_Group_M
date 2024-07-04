package Server;

import Domain.Branch;
import Domain.GeneralEmployee;
import Domain.Network;
import Domain.Role;
import java.io.File;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

public class Utility {
    public static boolean onlyNumbers(String toCheck) {
        for (int i = 0; i < toCheck.length(); i++)
            if (!(toCheck.charAt(i) > 47 && toCheck.charAt(i) < 58))
                return false;
        return !toCheck.isEmpty();
    }
    public static void shiftPrinter(String[][] shifts){
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i + "  ");
        }
        for (int j = 0; j < Network.shifts; j++) {
            System.out.print("\n"+(j+1)+"  ");
            for (int i = 0; i < Network.days; i++) {
                System.out.print("|" + shifts[j][i]);
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
        }
        System.out.println();
    }
    public static void shiftPrinter(Integer[][] shifts){
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i + "   ");
        }
        for (int j = 0; j < Network.shifts; j++) {
            System.out.print("\n"+(j+1)+"   ");
            for (int i = 0; i < Network.days; i++) {
                System.out.print("|" + shifts[j][i]+"   ");
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
        }
        System.out.println();
    }
    public static Role RolesSelect(Network network){
        Scanner scanner = new Scanner(System.in);
        String SelectedRole = "s";
        System.out.println("Select role:");
        Role[] roles = new Role[network.getRoles().size()+1];
        int i=1;
        for (Role r: network.getRoles()){
            if(!r.getRoleName().equals("Shift Manager")) {
                System.out.println(i + ". " + r.getRoleName());
                roles[i] = r;
                i++;
            }
        }
        System.out.println(i+". return back");

        int SelectedRoleIndex;
        do{
            while(!onlyNumbers(SelectedRole)) {
                SelectedRole = scanner.nextLine();
                if (!onlyNumbers(SelectedRole))
                    System.out.println("Not valid number");
            }
            SelectedRoleIndex=Integer.parseInt(SelectedRole);
        }while(SelectedRoleIndex>roles.length+1 || SelectedRoleIndex<1);

        if (SelectedRoleIndex!=i)
            return roles[SelectedRoleIndex];
        else
            return null;

    }
    public static void printRoleOfShifts(HashMap<Role, Integer[][]> rolesOfShifts){
        for (Role r : rolesOfShifts.keySet()) {
            System.out.println(r.getRoleName());
            shiftPrinter(rolesOfShifts.get(r));
        }
    }
    public static int maxNameLength(Set<GeneralEmployee> gel) {
        int maxNameLength=0;
        for(GeneralEmployee ge : gel)
            maxNameLength=Math.max(maxNameLength,ge.getName().length());
        return maxNameLength;
    }
    public static void ShiftAviPrinter(Set<GeneralEmployee>[][] gel){
        int maxSet=0,maxNameLength=0;
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                maxSet = Math.max(gel[i][j].size(), maxSet);
                maxNameLength=Math.max(maxNameLength,maxNameLength(gel[i][j]));
            }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i);
            for(int space=0;space<maxNameLength;space++)
                System.out.print(" ");

        }
        Iterator<GeneralEmployee>[][] empList = new Iterator[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++) {
                empList[i][j]= gel[i][j].iterator();
            }

        for (int i = 0; i < Network.shifts; i++) {
            System.out.print("\n"+(i+1)+"  ");
            for(int m=0;m<maxSet;m++) {
                for (int j = 0; j < Network.days; j++) {
                    String thisName="";
                    if(empList[i][j].hasNext()) {
                        thisName = empList[i][j].next().getName();
                        System.out.print(thisName);
                    }
                    for(int space=0;space<maxNameLength-thisName.length();space++)
                        System.out.print(" ");
                    System.out.print("  ");
                }
                System.out.println("  ");
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("---");
            for(int space=0;space<maxNameLength;space++)
                System.out.print("-");
        }
        System.out.println();
    }
    public static int maxStringLength(HashMap<String,Role> hm) {
        int maxStringLength = 0;
        for (String name : hm.keySet())
            maxStringLength = Math.max(maxStringLength, (name+" - "+hm.get(name).getRoleName()).length());
        return maxStringLength;
    }
    public static int maxStringLength(List<String> list) {
        int maxStringLength = 0;
        for (String name : list)
            maxStringLength = Math.max(maxStringLength,name.length());
        return maxStringLength;
    }
    public static void ShiftsOfWeekPrinter(Branch branch) {
        HashMap<Integer,Role>[][] shiftsWithID = branch.getEmployeesShifts();
        HashMap<String,Role>[][] shifts = getShiftsWithNames(shiftsWithID,branch);
        int maxHash=0,maxStringLength=0;
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                if(shifts[i][j]!=null) {
                    maxHash = Math.max(shifts[i][j].size(), maxHash);
                    maxStringLength = Math.max(maxStringLength, maxStringLength(shifts[i][j]));
                }
            }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i);
            for(int space=0;space<maxStringLength;space++)
                System.out.print(" ");
        }

        Iterator<String>[][] NamesList = new Iterator[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++) {
                NamesList[i][j]= shifts[i][j].keySet().iterator();
            }

        for (int i = 0; i < Network.shifts; i++) {
            System.out.print("\n"+(i+1)+"    ");
            for(int m=0;m<maxHash;m++) {
                for (int j = 0; j < Network.days; j++) {
                    String thisName="";
                    if(NamesList[i][j].hasNext()) {
                        thisName = NamesList[i][j].next();
                        System.out.print(thisName+" - "+shifts[i][j].get(thisName).getRoleName());
                        for(int space=0;space<maxStringLength-(thisName.length()+shifts[i][j].get(thisName).getRoleName().length()+2);space++)
                            System.out.print(" ");
                    }
                    else
                        for(int space=0;space<maxStringLength+2;space++)
                            System.out.print(" ");

                }
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("--");
            for(int space=0;space<maxStringLength;space++)
                System.out.print("-");
        }
        System.out.println();
    }
    public static void storekeepersPrinter(List<String>[][] shifts) {
        int maxHash=0,maxStringLength=0;
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++) {
                if(shifts[i][j]!=null) {
                    maxHash = Math.max(shifts[i][j].size(), maxHash);
                    maxStringLength = Math.max(maxStringLength, maxStringLength(shifts[i][j]));
                }
            }
        System.out.print("----");
        for (int i = 1; i <= Network.days; i++) {
            System.out.print("|" + i);
            for(int space=0;space<maxStringLength;space++)
                System.out.print(" ");
        }

        Iterator<String>[][] NamesList = new Iterator[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++) {
                NamesList[i][j]=null;
                if( shifts[i][j]!=null)
                    NamesList[i][j]= shifts[i][j].iterator();
            }

        for (int i = 0; i < Network.shifts; i++) {
            System.out.print("\n"+(i+1)+"  ");
            for(int m=0;m<maxHash;m++) {
                for (int j = 0; j < Network.days; j++) {
                    String thisName="";
                    if(NamesList[i][j]!=null && NamesList[i][j].hasNext()) {
                        thisName = NamesList[i][j].next();
                        System.out.print(thisName);
                    }
                    for(int space=0;space<maxStringLength-thisName.length();space++)
                        System.out.print(" ");
                }
            }
        }
        System.out.print("\n----");
        for (int i = 0; i <= Network.days; i++) {
            System.out.print("--");
            for(int space=0;space<maxStringLength;space++)
                System.out.print("-");
        }
        System.out.println();
    }
    public static HashMap<String, Role>[][] getShiftsWithNames(HashMap<Integer, Role>[][] shiftsWithID,Branch branch) {
        HashMap<String, Role>[][] shifts = new HashMap[Network.shifts][Network.days];
        for (int i = 0; i < Network.shifts; i++)
            for (int j = 0; j < Network.days; j++){
                shifts[i][j] = new LinkedHashMap<>();
                for(Integer ID:shiftsWithID[i][j].keySet())
                    shifts[i][j].put(branch.getEmployeeName(ID), shiftsWithID[i][j].get(ID));
                }
        return shifts;
    }
    private static String getDBUrl(){
        //return "jdbc:sqlite:C:\\uni\\D\\nitoz\\testing\\dev\\DataLayer\\DataBase.db";
        String relativePath = "DataLayer/DataBase.db";
        File databaseFile = Paths.get(relativePath).toFile();
        if (databaseFile.exists()) {
            return "jdbc:sqlite:"+databaseFile.getAbsolutePath().replace("\\", "\\\\");
        } else {
            throw new IllegalStateException("Error: Database file not found in the url: " + databaseFile.getAbsolutePath());
        }
    }
    public static Connection toConnect(){
        Connection connection=null;
        try {
            Class.forName("org.sqlite.JDBC");
            connection = DriverManager.getConnection(getDBUrl());
        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
        return connection;
    }
    public static void Close(Connection connection){
            try {
                if (connection != null)
                    connection.close();

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

    }
}

