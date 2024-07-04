package DataLayer.IMP;

import DataLayer.interfaces.ShiftRequestDao;
import Domain.Network;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class ShiftRequestDaoImp implements ShiftRequestDao {

    @Override
    public void create(boolean[][] shifts, int ID) {
        Connection connection = Utility.toConnect();
        for(int i=0;i<Network.shifts;i++)
            for(int j=0;j<Network.days;j++)
                if (shifts[i][j]) {
                    String query = "INSERT INTO ShiftRequests(ID,shift,day) VALUES(?, ?, ?)";
                    try {
                        PreparedStatement prepare = connection.prepareStatement(query);
                        prepare.setInt(1, ID);
                        prepare.setInt(2, j);
                        prepare.setInt(3, i);
                        prepare.executeUpdate();

                    } catch (SQLException e) {
                        System.out.println(e.getMessage());
                    }
                    Utility.Close(connection);
                }
        System.out.println("ShiftRequest has been added.");
    }

    @Override
    public boolean[][] read(int ID) {
        boolean[][] shifts = new boolean[Network.days][Network.shifts];
        for (int i = 1; i <= Network.days; i++)
            for (int j = 0; j < Network.shifts; j++)
                shifts[i][j]=false;
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM ShiftRequests WHERE ID = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setInt(1, ID);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    int j = resultSet.getInt("shift");
                    int i = resultSet.getInt("day");
                    shifts[i][j]=true;
                }
            }catch (SQLException e) {
                    throw new RuntimeException(e);
                }
        }catch (SQLException e) {
                throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return shifts;
    }

    @Override
    public void update(boolean[][] shifts, int ID) {
        delete(ID);
        create(shifts, ID);
    }

    @Override
    public void update(boolean shifts, int ID, int shift, int day) {
        Connection connection = Utility.toConnect();
        String query = "UPDATE FROM ShiftRequests WHERE ID = ? AND shift = ? AND day = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            prepare.setInt(2, shift);
            prepare.setInt(3, day);
            int deleteRows = prepare.executeUpdate();
            if (deleteRows > 0)
                System.out.println("ShiftRequest has been deleted from ShiftRequests table.");
            else
                System.out.println("No ShiftRequest found with ID: " + ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }


    @Override
    public void delete(int ID) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM ShiftRequests WHERE ID = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setInt(1, ID);
            int deleteRows = prepare.executeUpdate();
            if (deleteRows > 0)
                System.out.println("ShiftRequest has been deleted from ShiftRequests table.");
            else
                System.out.println("No ShiftRequest found with ID: " + ID);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);

    }
}
