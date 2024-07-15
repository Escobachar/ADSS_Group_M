package HR.DataLayer.IMP;

import HR.DataLayer.interfaces.RoleDao;
import HR.Domain.Role;
import HR.Server.Utility;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

public class RoleDaoImp implements RoleDao {

    @Override
    public void create(Role role) {
        Connection connection = Utility.toConnect();
        String query = "INSERT INTO Role(roleName, access) VALUES(?, ?)";
        try {
            if (role.getAccess().isEmpty()){
                PreparedStatement prepare = connection.prepareStatement(query);
            prepare.setString(1, role.getRoleName());
            prepare.setString(2, "");
            prepare.executeUpdate();
            }
            else {
                for (String access : role.getAccess()) {
                    PreparedStatement prepare = connection.prepareStatement(query);
                    prepare.setString(1, role.getRoleName());
                    prepare.setString(2, access);
                    prepare.executeUpdate();
                }
            }
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
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        Utility.Close(connection);
    }
    @Override
    public List<Role> readAll(){
        List<Role> roles = new ArrayList<>();
        Connection connection = Utility.toConnect();
        String query = "SELECT DISTINCT roleName FROM Role";
        try {
            PreparedStatement statement = connection.prepareStatement(query);
            try (ResultSet resultSet = statement.executeQuery()) {
                while (resultSet.next()) {
                    String roleName = resultSet.getString("roleName");
                    roles.add(read(roleName));
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        Utility.Close(connection);
        return roles;
    }

}
