package DataLayer.IMP;

import DataLayer.interfaces.EmployeeShiftsDao;
import Domain.GeneralEmployee;
import Domain.Network;
import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class EmployeeShiftsDaoImp implements EmployeeShiftsDao {
    public void create(String branchName, HashMap<Integer, Role>[][] employeeShifts){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO EmployeeShifts(branchNane, empID, role, day, shift) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement prepare = connection.prepareStatement(query);
            for (int i = 0; i < employeeShifts.length; i++) {
                for (int j = 0; j < employeeShifts[i].length; j++) {
                    HashMap<Integer, Role> map = employeeShifts[i][j];
                    for (Integer id : map.keySet()) {
                        prepare.setString(1, branchName);
                        prepare.setInt(2, id);
                        prepare.setString(3, map.get(id).getRoleName());
                        prepare.setInt(4, j);
                        prepare.setInt(5, i);
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    public HashMap<Integer,Role>[][] read(String branchName){
        HashMap<Integer,Role>[][] employeesShifts=new HashMap[Network.shifts][Network.days];
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                employeesShifts[i][j]=new HashMap<>();

        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM EmployeeShifts WHERE branchName = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("role");
                    int day = resultSet.getInt("day");
                    int shift = resultSet.getInt("shift");
                    int empID = resultSet.getInt("empID");
                    employeesShifts[shift][day].put(empID,Network.getNetwork().getRole(roleName));
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return employeesShifts;
    }
    public void update(String branchName, HashMap<Integer,Role>[][] employeeShifts){
        delete(branchName);
        create(branchName, employeeShifts);
    }
    public void delete(String branchName){
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM EmployeeShifts WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();
            if (deleteRows > 0)
                System.out.println("EmployeeShifts has been deleted");
            else
                System.out.println("No EmployeeShifts found with branchName: " + branchName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
}
