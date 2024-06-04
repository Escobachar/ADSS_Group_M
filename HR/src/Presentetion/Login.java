package Presentetion;

import Server.*;
import Domain.*;

import java.util.Scanner;
import java.util.Set;

public class Login {
    public static void main(String[] args){
        Scanner scanner = new Scanner(System.in);
        System.out.print("with items or no? \r 1.with\r2.without\r");
        Network network= Server.init.init(scanner.nextLine().equals("1"));
        System.out.print("Enter ID: ");
        String ID = scanner.nextLine();
        Employee emp=null;
        boolean passwordmatch = false;
        boolean IDFound= false;
        while(!passwordmatch) {
            while(!IDFound) {
                while (!(onlyNumbers(ID))) {
                    System.out.print("ID not valid or not found.\rEnter  ID: ");
                    ID = scanner.nextLine();
                }
                emp = network.SearchByID(Integer.parseInt(ID));
                IDFound=emp!=null;
            }
            System.out.print("Enter password: ");
            String password = scanner.nextLine();
            if(emp.getPassword()==password)
            {
                passwordmatch=true;
                Menu(emp);
            }
            else
            {
                System.out.println("password does not match\rEnter  ID: \"");
                IDFound= false;
                ID = scanner.nextLine();
            }
        }

    }

    private static boolean onlyNumbers(String toCheck) {
        for (int i = 0; i < toCheck.length(); i++)
            if (!(toCheck.charAt(i) > 47 && toCheck.charAt(i) < 58))
                return false;
        return true;
    }

    private static void Menu(Employee emp)
    {
        Set<String> access=emp.getAccess();
        if(access.contains("updateShifts"))
        {


        }

        if(access.contains("getShifts")) {
            GeneralEmployee ge = (GeneralEmployee) emp;
            String[][] shifts = new String[Network.shifts][Network.days];
            for (int i = 0; i < Network.shifts; i++)
                for (int j = 0; j < Network.days; j++)
                    if (ge.getShiftsRequest()[i][j])
                        shifts[i][j] = "yes";
                    else
                        shifts[i][j] = "no ";
            System.out.print("----");
            for (int i = 1; i <= Network.days; i++) {
                System.out.print("|" + i + "  ");
            }


            for (int j = 0; j < Network.shifts; j++) {
                System.out.print("\r"+j+"  ");
                for (int i = 0; i < Network.days; i++) {
                    System.out.print("|" + GS(i, j, shifts));
                }
            }
            System.out.print("----");
            for (int i = 1; i <= Network.days; i++) {
                System.out.print("---");
            }
        }

}

private static String GS(Integer i,Integer j,String[][] shifts){
        i++;
        return shifts[i][j];
}

}
