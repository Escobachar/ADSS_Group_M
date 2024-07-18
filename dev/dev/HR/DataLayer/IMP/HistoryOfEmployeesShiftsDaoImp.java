package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.HistoryOfEmployeesShiftsDao;
import HR.Domain.Network;
import HR.Domain.Role;
import HR.Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

public class HistoryOfEmployeesShiftsDaoImp implements HistoryOfEmployeesShiftsDao {
    public void create(String branchName, HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO HistoryOfEmpShifts(branchNane, empID, roleName, day, shift, dateOfWeek) VALUES (?,?,?,?,?,?)";
        try {

            for(String dateOfWeek : historyEmployeesShifts.keySet()){
                HashMap<Integer,Role>[][] employeeShifts = historyEmployeesShifts.get(dateOfWeek);
                for (int i = 0; i < employeeShifts.length; i++) {
                    for (int j = 0; j < employeeShifts[i].length; j++) {
                        HashMap<Integer, Role> map = employeeShifts[i][j];
                        for (Integer id : map.keySet()) {
                            PreparedStatement prepare = connection.prepareStatement(query);
                            prepare.setString(1, branchName);
                            prepare.setInt(2, id);
                            prepare.setString(3, map.get(id).getRoleName());
                            prepare.setInt(4, j);
                            prepare.setInt(5, i);
                            prepare.setString(6, dateOfWeek);
                            prepare.execute();
                        }
                    }
                }
            }
        }catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    public HashMap<String, HashMap<Integer,Role>[][]> read(String branchName){
        HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts = new HashMap<>();
        HashMap<Integer,Role>[][] employeesShifts = new HashMap[Network.shifts][Network.days];
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                employeesShifts[i][j]=new HashMap<>();

        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM HistoryOfEmpShifts WHERE branchName = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("role");
                    int day = resultSet.getInt("day");
                    int shift = resultSet.getInt("shift");
                    int empID = resultSet.getInt("empID");
                    String dateOfWeek = resultSet.getString("dateOfWeek");
                    employeesShifts[shift][day].put(empID,Network.getNetwork().getRole(roleName));
                    historyEmployeesShifts.put(dateOfWeek,employeesShifts);
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return historyEmployeesShifts;
    }

    public void update(String branchName, HashMap<String, HashMap<Integer,Role>[][]> historyEmployeesShifts){
        delete(branchName);
        create(branchName, historyEmployeesShifts);
    }

    public void delete(String branchName){
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM HistoryOfEmpShifts WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
}
