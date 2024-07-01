package DataLayer;

import Domain.*;

public interface RoleDao {
    void create(Role role);
    Role read(String roleName);
    void update(String access);
    void delete(Role role);
}
