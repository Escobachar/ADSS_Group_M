package DataLayer.IMP;

import DataLayer.interfaces.ShiftAvailabilityDao;
import Domain.GeneralEmployee;
import Domain.Network;
import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ShiftAvailabilityDaoImp implements ShiftAvailabilityDao {
    @Override
    public void create(String branchName, HashMap<Role,Set<GeneralEmployee>[][]> shiftAvailability){
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO ShiftAvailability(branchNane, role, empID, day, shift) VALUES (?,?,?,?,?)";
        try{
            PreparedStatement prepare = connection.prepareStatement(query);
            for(Role role : shiftAvailability.keySet()){
                for (int i = 0; i < Network.shifts; i++) {
                    for(int j = 0; j < Network.days; j++){
                        Set<GeneralEmployee> availableEmployees = shiftAvailability.get(role.getRoleName())[i][j];
                        for(GeneralEmployee employee: availableEmployees){
                            prepare.setString(1, branchName);
                            prepare.setString(2, role.getRoleName());
                            prepare.setInt(3,employee.getID());
                            prepare.setInt(4, j);
                            prepare.setInt(5, i);
                        }
                    }
                }
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Override
    public HashMap<Role,Set<GeneralEmployee>[][]> read(String branchName){
        //set up the shiftsAvailability HashMap
        HashMap<Role,Set<GeneralEmployee>[][]> shiftsAvailability = new HashMap<Role,Set<GeneralEmployee>[][]>();
        for(Role r: Network.getNetwork().getRoles()) {
            shiftsAvailability.put(r, new HashSet[Network.shifts][Network.days]);
            Set<GeneralEmployee>[][] setList = shiftsAvailability.get(r);
            for (int i = 0; i < Network.shifts; i++) {
                for (int j = 0; j < Network.days; j++) {
                    setList[i][j] = new HashSet<>();
                }
            }
        }
        //insert data from database
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM ShiftAvailability WHERE branchName = ?";
        try{
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, branchName);
            try (ResultSet resultSet = statement.executeQuery()) {
                if (resultSet.next()) {
                    String roleName = resultSet.getString("role");
                    int day = resultSet.getInt("day");
                    int shift = resultSet.getInt("shift");
                    int empID = resultSet.getInt("empID");
                    shiftsAvailability.get(Network.getNetwork().getRole(roleName))[shift][day].add((GeneralEmployee) Network.getNetwork().getEmployee(branchName,empID));
                }
            }catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
        return shiftsAvailability;
    }
    @Override
    public void update(String branchName, HashMap<Role,Set<GeneralEmployee>[][]> newShiftAvailability){
        delete(branchName);
        create(branchName, newShiftAvailability);
    }
    @Override
    public void delete(String branchName){
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM ShiftAvailability WHERE branchName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, branchName);
            int deleteRows = prepare.executeUpdate();
            if (deleteRows > 0)
                System.out.println("ShiftAvailability has been deleted");
            else
                System.out.println("No ShiftAvailability found with branchName: " + branchName);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Override
    public void update(boolean add, String BranchName, Role r, int ID, int day, int shift) {
        Connection connection = Utility.toConnect();
        if(!add) {
            String query = "DELETE FROM ShiftAvailability where BranchName = ?  AND role = ? AND empID = ? AND day = ? AND shift = ?";
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, BranchName);
                prepare.setString(2, r.getRoleName());
                prepare.setInt(3, ID);
                prepare.setInt(4, day);
                prepare.setInt(5, shift);
                prepare.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        else{
            String query = "INSERT into ShiftAvailability where BranchName = ?  AND role = ? AND empID = ? AND day = ? AND shift = ?";
            try {
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, BranchName);
                prepare.setString(2, r.getRoleName());
                prepare.setInt(3, ID);
                prepare.setInt(4, day);
                prepare.setInt(5, shift);
                prepare.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }

        }
        Utility.Close(connection);
    }
}
