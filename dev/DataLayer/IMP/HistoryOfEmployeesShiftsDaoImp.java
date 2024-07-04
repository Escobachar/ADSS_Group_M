package DataLayer.IMP;

import DataLayer.interfaces.HistoryOfEmployeesShiftsDao;
import Domain.Network;
import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class HistoryOfEmployeesShiftsDaoImp implements HistoryOfEmployeesShiftsDao {
    public void create(String branchName, List<HashMap<Integer, Role>[][]> historyEmployeesShifts){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO HistoryOfEmployeesShifts(branchNane, empID, roleName, day, shift, dateOfWeek) VALUES (?,?,?,?,?)";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            for(String dateOfWeek : historyEmployeesShifts.keySet()){
                HashMap<Integer,Role>[][] employeeShifts = historyEmployeesShifts.get(dateOfWeek);
                for (int i = 0; i < employeeShifts.length; i++) {
                    for (int j = 0; j < employeeShifts[i].length; j++) {
                        HashMap<Integer, Role> map = employeeShifts[i][j];
                        for (Integer id : map.keySet()) {
                            prepare.setString(1, branchName);
                            prepare.setInt(2, id);
                            prepare.setString(3, map.get(id).getRoleName());
                            prepare.setInt(4, j);
                            prepare.setInt(5, i);
                            prepare.setString(6, dateOfWeek);
                        }
                    }
                }
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    public List<HashMap<Integer,Role>[][]> read(String branchName){
        return null;
    }

    public void update(String branchName, HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts){
        delete(branchName);
        create(branchName, historyEmployeesShifts);
    }

    public void delete(String branchName){
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM HistoryOfEmployeesShifts WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();
            if (deleteRows > 0)
                System.out.println("HistoryOfEmployeesShifts has been deleted");
            else
                System.out.println("No HistoryOfEmployeesShifts found with branchName: " + branchName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
}
