package dao;
import context.DBConnection;
import model.Inventory;
import java.sql.*;
import java.util.*;
public class InventoryDAO {
    public List<Inventory> findAll(){
        List<Inventory> list = new ArrayList<>();
        String sql = "SELECT ItemID, ItemName, SellingPrice, Quantity FROM Inventory";
        try (Connection c = DBConnection.getConnection();
             PreparedStatement p = c.prepareStatement(sql);
             ResultSet r = p.executeQuery()){
            while(r.next()){
                list.add(new Inventory(r.getInt("ItemID"), r.getString("ItemName"),
                                       r.getDouble("SellingPrice"), r.getInt("Quantity")));
            }
        } catch(Exception e){ e.printStackTrace(); }
        return list;
    }
}