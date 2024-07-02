package DataLayer;

import Domain.*;

public interface RoleDao {
    void create(Role role);
    Role read(String roleName);
    void update(String access);

    void update(Role role);

    void delete(Role role);
}
