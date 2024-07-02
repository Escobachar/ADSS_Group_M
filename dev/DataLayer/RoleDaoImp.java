package DataLayer;

import Domain.Employee;
import Domain.Role;

public class RoleDaoImp implements RoleDao{

    @Override
    public void create(Role role) {

    }

    @Override
    public Role read(String roleName) {
        return null;
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
