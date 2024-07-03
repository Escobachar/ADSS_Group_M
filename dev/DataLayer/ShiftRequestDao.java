package DataLayer;

import Domain.Role;

import java.util.HashMap;

public interface ShiftRequestDao {
    void create(boolean[][] shifts,int ID);
    boolean[][] read(int ID);
    void update(boolean[][] shifts,int ID);
    void delete(int ID);
}
