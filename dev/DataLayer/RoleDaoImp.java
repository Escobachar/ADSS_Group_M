package DataLayer;

import Domain.Role;
import Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoleDaoImp implements RoleDao{

    @Override
    public void create(Role role) {
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Role(roleName, access) VALUES(?, ?)";
        try {
            for (String access : role.getAccess()) {
                PreparedStatement prepare = connection.prepareStatement(query);
                prepare.setString(1, role.getRoleName());
                prepare.setString(2, access);
                prepare.executeUpdate();
            }
            System.out.println("Role" +role.getRoleName() + "has been added.");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }

    @Override
    public Role read(String roleName) {
        Role role = null;
        List<String> accesses = new ArrayList<String>();
        Connection connection = Utility.toConnect();
        String query = "SELECT * FROM Role WHERE roleName = ?";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            statement.setString(1, roleName);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String access = resultSet.getString("access");
                    accesses.add(access);
                }
                role = new Role(roleName, accesses);
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return role;
    }

    @Override
    public void update(Role role) {
        delete(role);
        create(role);
    }

    @Override
    public void delete(Role role) {
        Connection connection = Utility.toConnect();
        String query = "DELETE FROM Role WHERE roleName = ?";
        try {
            PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, role.getRoleName());
            int deleteRows = prepare.executeUpdate();

            if (deleteRows > 0)
                System.out.println("role has been deleted from Role table.");
            else
                System.out.println("No role found with roleName: " + role.getRoleName());
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    }
}
