package DataLayer.interfaces;

import Domain.*;

import java.util.List;

public interface RoleDao {
    void create(Role role);
    Role read(String roleName);
    void update(Role role);
    void delete(Role role);
    List<Role> readAll();
}
