package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.RoleOfShiftsDao;
import HR.Domain.Network;
import HR.Domain.Role;
import HR.Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RoleOfShiftsDaoImp implements RoleOfShiftsDao {
    @Override
    public void create(String branchName, HashMap<Role,Integer[][]> roleOfShifts){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO RoleOfShifts(branchName, roleName, day, shift, amount) VALUES (?,?,?,?,?)";
        try{

            for(Role role : roleOfShifts.keySet()){
                for (int i = 0; i < Network.shifts; i++) {
                    for(int j = 0; j < Network.days; j++){
                        PreparedStatement prepare = connection.prepareStatement(query);
                        prepare.setString(1, branchName);
                        prepare.setString(2, role.getRoleName());
                        prepare.setInt(3, i);
                        prepare.setInt(4, j);
                        prepare.setInt(5, roleOfShifts.get(role)[i][j]);
                        prepare.execute();
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Override
    public HashMap<Role,Integer[][]> read(String branchName){
        //set up the rolesOfShifts HashMap
        HashMap<Role,Integer[][]> rolesOfShifts =  new HashMap<>();
        for(Role r: Network.getNetwork().getRoles()) {
            rolesOfShifts.put(r, new Integer[Network.shifts][Network.days]);
            for (int i = 0; i < Network.shifts; i++) {
                rolesOfShifts.get(r)[i] = new Integer[Network.days];
                for (int j = 0; j < Network.days; j++) {
                    rolesOfShifts.get(r)[i][j] = 0;
                }
            }
        }
        //insert data from database
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM RoleOfShifts WHERE branchName = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("roleName");
                    int day = resultSet.getInt("day");
                    int shift = resultSet.getInt("shift");
                    int amount = resultSet.getInt("amount");
                    rolesOfShifts.get(Network.getNetwork().getRole(roleName))[shift][day] = amount;
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return rolesOfShifts;
    }
    @Override
    public void update(String branchName, HashMap<Role,Integer[][]> newRoleOfShifts){
        delete(branchName);
        create(branchName, newRoleOfShifts);
    }

    @Override
    public void update(String branchName, String roleName, int day, int shift, int numOfEmployees) {
        Connection connection = Utility.toConnect();
            String query = "DELETE FROM RoleOfShifts where BranchName = ?  AND roleName = ?  AND day = ? AND shift = ? AND amount = ?";
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, branchName);
                prepare.setString(2, roleName);
                prepare.setInt(3, day);
                prepare.setInt(4, shift);
                prepare.setInt(5, numOfEmployees);
                prepare.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        query = "INSERT INTO RoleOfShifts(branchName, roleName, day, shift, amount) VALUES (?,?,?,?,?)";
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, branchName);
                prepare.setString(2, roleName);
                prepare.setInt(3, day);
                prepare.setInt(4, shift);
                prepare.setInt(5, numOfEmployees);
                prepare.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        Utility.Close(connection);
    }

    @Override
    public void delete(String branchName){
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM RoleOfShifts WHERE branchName = ?";
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
