package DataLayer.interfaces;

public interface ShiftRequestDao {
    void create(boolean[][] shifts,int ID);
    boolean[][] read(int ID);
    void update(boolean[][] shifts,int ID);

    void update(boolean shifts, int ID, int shift, int day);

    void delete(int ID);
}
