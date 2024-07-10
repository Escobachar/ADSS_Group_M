package suppliers.DataAccessLayer.DAO;
import java.sql.*;
import suppliers.DataAccessLayer.DataBase;
import suppliers.DomainLayer.*;

public class ProductsDiscountDAO {
    
    public ProductsDiscountDAO() throws SQLException {
    }

    public DiscountQuantity getDiscountQuantity(int sid, int catalogNumber) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("SELECT * FROM ProductsDiscount WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, catalogNumber);
        ResultSet rs = stmt.executeQuery();
        DataBase.closeConnection();
        return DiscountQuantity.createDiscountQuantity(rs.getInt("catalogNum"), rs.getInt("amount"), rs.getDouble("precentage"));
    }

    public void addDiscountQuantity(int sid, int catalogNumber, DiscountQuantity discount) throws SQLException {
        try {
            Connection conn = DataBase.getConnection();
            System.out.println("start addDiscountQuantity ");
            PreparedStatement stmt = conn.prepareStatement("INSERT INTO ProductsDiscount (SupplierId, catalogNum, amount, precentage) VALUES (?, ?, ?, ?)");
            stmt.setInt(1, sid);
            stmt.setInt(2, catalogNumber);
            stmt.setInt(3, discount.getAmount());
            stmt.setDouble(4, discount.getDiscountPercentage());
            stmt.executeUpdate();
            DataBase.closeConnection();
        }
        catch (Exception e)
        {
            System.out.println("addDiscountQuantity "+ e.getMessage());
        }
    }

    public void updateDiscountQuantity(int sid, int catalogNumber, DiscountQuantity discount) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("UPDATE ProductsDiscount SET amount = ?, precentage = ? WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, discount.getAmount());
        stmt.setDouble(2, discount.getDiscountPercentage());
        stmt.setInt(3, sid);
        stmt.setInt(4, catalogNumber);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }

    public void deleteDiscountQuantity(int sid, int catalogNumber) throws SQLException {
        Connection conn = DataBase.getConnection();
        PreparedStatement stmt = conn.prepareStatement("DELETE FROM ProductsDiscount WHERE SupplierId = ? AND catalogNum = ?");
        stmt.setInt(1, sid);
        stmt.setInt(2, catalogNumber);
        stmt.executeUpdate();
        DataBase.closeConnection();
    }
}