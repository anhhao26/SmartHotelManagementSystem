package dao;

import context.DBConnection;
import model.CartItem;
import java.sql.*;
import java.util.List;

public class BillingDAO {

    // CHECK-IN (giữ nguyên, nhưng đảm bảo rollback/commit bằng same connection)
    public boolean processCheckIn(int bookingID, int roomID) {
        String sqlBooking = "UPDATE Bookings SET Status = 'Checked-in' WHERE BookingID = ?";
        String sqlRoom = "UPDATE Rooms SET Status = 'Occupied' WHERE RoomID = ?";

        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) throw new SQLException("Cannot get DB connection");
            conn.setAutoCommit(false);

            try (PreparedStatement ps1 = conn.prepareStatement(sqlBooking);
                 PreparedStatement ps2 = conn.prepareStatement(sqlRoom)) {

                ps1.setInt(1, bookingID);
                ps1.executeUpdate();

                ps2.setInt(1, roomID);
                ps2.executeUpdate();

                conn.commit();
                return true;
            } catch (Exception e) {
                if (conn != null) conn.rollback();
                e.printStackTrace();
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        } finally {
            try { if (conn != null) { conn.setAutoCommit(true); conn.close(); } } catch (SQLException ex) {}
        }
    }

    // CHECK-OUT & BILLING (transactional, atomic)
    public boolean processCheckOut(int bookingID, int customerID, int roomID, List<CartItem> items, double roomPrice) {
        Connection conn = null;
        try {
            conn = DBConnection.getConnection();
            if (conn == null) throw new SQLException("Không thể kết nối DB");
            conn.setAutoCommit(false);

            // 1. Kiểm tra VIP
            boolean isVIP = false;
            try (PreparedStatement psVIP = conn.prepareStatement("SELECT MemberType FROM Customers WHERE CustomerID = ?")) {
                psVIP.setInt(1, customerID);
                try (ResultSet rs = psVIP.executeQuery()) {
                    if (rs.next()) isVIP = "VIP".equalsIgnoreCase(rs.getString("MemberType"));
                }
            }

            double discountAmount = isVIP ? (roomPrice * 0.20) : 0;
            double finalRoomPrice = roomPrice - discountAmount;

            // 2. Tính tiền minibar & kiểm tra tồn kho (read before write)
            double minibarTotal = 0;
            if (items != null && !items.isEmpty()) {
                try (PreparedStatement psCheck = conn.prepareStatement("SELECT SellingPrice, Quantity FROM Inventory WHERE ItemID = ?")) {
                    for (CartItem it : items) {
                        psCheck.setInt(1, it.getItemID());
                        try (ResultSet rs = psCheck.executeQuery()) {
                            if (!rs.next()) throw new SQLException("ItemID " + it.getItemID() + " không tồn tại");
                            double unit = rs.getDouble("SellingPrice");
                            int stock = rs.getInt("Quantity");
                            if (stock < it.getQuantity()) throw new SQLException("Not enough stock for ItemID " + it.getItemID());
                            minibarTotal += unit * it.getQuantity();
                        }
                        psCheck.clearParameters();
                    }
                }
            }

            double grandTotal = finalRoomPrice + minibarTotal;

            // 3. Insert Invoice và lấy InvoiceID
            int invoiceID;
            String sqlInsertInv = "INSERT INTO Invoices (BookingID, TotalAmount, Discount, CreatedDate) VALUES (?, ?, ?, GETDATE())";
            try (PreparedStatement psInv = conn.prepareStatement(sqlInsertInv, Statement.RETURN_GENERATED_KEYS)) {
                psInv.setInt(1, bookingID);
                psInv.setDouble(2, grandTotal);
                psInv.setDouble(3, discountAmount);
                int n = psInv.executeUpdate();
                if (n == 0) throw new SQLException("Insert invoice failed");
                try (ResultSet keys = psInv.getGeneratedKeys()) {
                    if (keys.next()) invoiceID = keys.getInt(1);
                    else throw new SQLException("Không lấy được InvoiceID");
                }
            }

            // 4. Insert InvoiceItems + Update Inventory (write)
            String sqlInsertItem = "INSERT INTO InvoiceItems (InvoiceID, ItemID, Quantity, UnitPrice) VALUES (?, ?, ?, ?)";
            String sqlUpdateStock = "UPDATE Inventory SET Quantity = Quantity - ? WHERE ItemID = ?";
            try (PreparedStatement psItem = conn.prepareStatement(sqlInsertItem);
                 PreparedStatement psUpdate = conn.prepareStatement(sqlUpdateStock);
                 PreparedStatement psGetPrice = conn.prepareStatement("SELECT SellingPrice FROM Inventory WHERE ItemID = ?")) {

                for (CartItem it : items) {
                    // lấy giá (để lưu vào invoice item)
                    psGetPrice.setInt(1, it.getItemID());
                    double unitPrice;
                    try (ResultSet rs = psGetPrice.executeQuery()) {
                        if (rs.next()) unitPrice = rs.getDouble("SellingPrice");
                        else throw new SQLException("Giá item không tìm thấy: " + it.getItemID());
                    }
                    psItem.setInt(1, invoiceID);
                    psItem.setInt(2, it.getItemID());
                    psItem.setInt(3, it.getQuantity());
                    psItem.setDouble(4, unitPrice);
                    psItem.executeUpdate();

                    psUpdate.setInt(1, it.getQuantity());
                    psUpdate.setInt(2, it.getItemID());
                    int affected = psUpdate.executeUpdate();
                    if (affected == 0) throw new SQLException("Trừ kho thất bại cho ItemID=" + it.getItemID());
                    psItem.clearParameters();
                    psUpdate.clearParameters();
                    psGetPrice.clearParameters();
                }
            }

            // 5. Update Booking & Room
            try (PreparedStatement psB = conn.prepareStatement("UPDATE Bookings SET Status = ? WHERE BookingID = ?");
                 PreparedStatement psR = conn.prepareStatement("UPDATE Rooms SET Status = ? WHERE RoomID = ?")) {
                psB.setString(1, "Checked-out");
                psB.setInt(2, bookingID);
                psB.executeUpdate();

                psR.setString(1, "Cleaning");
                psR.setInt(2, roomID);
                psR.executeUpdate();
            }

            // 6. Update Customer spending & points
            int pointsEarned = (int) (grandTotal / 100000);
            try (PreparedStatement psC = conn.prepareStatement("UPDATE Customers SET TotalSpending = TotalSpending + ?, Points = Points + ? WHERE CustomerID = ?")) {
                psC.setDouble(1, grandTotal);
                psC.setInt(2, pointsEarned);
                psC.setInt(3, customerID);
                psC.executeUpdate();
            }

            // 7. Promote VIP nếu đủ (DB trigger cũng có thể xử lý)
            try (PreparedStatement psProm = conn.prepareStatement("UPDATE Customers SET MemberType = 'VIP' WHERE CustomerID = ? AND TotalSpending >= 10000000")) {
                psProm.setInt(1, customerID);
                psProm.executeUpdate();
            }

            conn.commit();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            if (conn != null) {
                try { conn.rollback(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
            return false;
        } finally {
            if (conn != null) {
                try { conn.setAutoCommit(true); conn.close(); } catch (SQLException ex) { ex.printStackTrace(); }
            }
        }
    }
}