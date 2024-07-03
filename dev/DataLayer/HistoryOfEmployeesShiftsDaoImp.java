package DataLayer;

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
            // Iterate over the list
            for (HashMap<Integer, Role>[][] shiftHistory : historyEmployeesShifts) {
                // Iterate over the 2D array
                for (int i = 0; i < shiftHistory.length; i++) {
                    for (int j = 0; j < shiftHistory[i].length; j++) {
                        HashMap<Integer, Role> idToRole = shiftHistory[i][j];
                        // Iterate over the HashMap
                        for (Integer ID : idToRole.keySet()) {
                            Role role = idToRole.get(ID);


                        }
                    }
                }
            }

        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public List<HashMap<Integer,Role>[][]> read(String branchName){
        return null;
    }

    public void update(String branchName, List<HashMap<Integer,Role>[][]> historyEmployeesShifts){

    }

    public void delete(String branchName){

    }
}
