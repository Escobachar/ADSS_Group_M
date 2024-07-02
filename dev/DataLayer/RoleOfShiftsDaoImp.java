package DataLayer;

import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

public class RoleOfShiftsDaoImp implements RoleOfShiftsDao{
    @Override
    public void create(String branchName, HashMap<Role,Integer[][]> hashMap){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO RoleOfShifts(branchNane, roleName, day, shift, amount) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement prepare = connection.prepareStatement(query);

            for(Role role : hashMap.keySet()){
                int day = hashMap.get(role)[0].length; //maby its shift
                int shift =  hashMap.get(role)[1].length;
                for (int i = 0; i < day; i++) {
                    for(int j = 0; j < shift; j++){
                        prepare.setString(1, branchName);
                        prepare.setString(2, role.getRoleName());
                        prepare.setInt(3, i);
                        prepare.setInt(4, j);
                        prepare.setInt(5, hashMap.get(role)[i][j]);
                    }
                }
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }
    @Override
    public HashMap<Role,Integer[][]> read(String branchName){
        HashMap<Role,Integer[][]> ret =  new HashMap<>();
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
//create Integer [][] and insert amount to the right plase, create role and return the hashmap
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return ret;
    }




    @Override
    public void update(){}
    @Override
    public void delete(){}
}
