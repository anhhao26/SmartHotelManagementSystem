package dao;

import context.DBConnection;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {

    public static class BookingShort {
        public int bookingID;
        public int roomID;
        public int customerID;
        public String customerName;
        public Date checkInDate;
        public Date checkOutDate;
        public String status;
    }

    // Lấy các booking đang ở trạng thái Pending (chưa checkin)
    public List<BookingShort> findPendingBookings() {
        List<BookingShort> list = new ArrayList<>();
        String sql = "SELECT b.BookingID, b.RoomID, b.CustomerID, c.FullName, b.CheckInDate, b.CheckOutDate, b.Status " +
                     "FROM Bookings b JOIN Customers c ON b.CustomerID = c.CustomerID " +
                     "WHERE b.Status = 'Pending' ORDER BY b.BookingID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookingShort bs = new BookingShort();
                bs.bookingID = rs.getInt("BookingID");
                bs.roomID = rs.getInt("RoomID");
                bs.customerID = rs.getInt("CustomerID");
                bs.customerName = rs.getString("FullName");
                bs.checkInDate = rs.getDate("CheckInDate");
                bs.checkOutDate = rs.getDate("CheckOutDate");
                bs.status = rs.getString("Status");
                list.add(bs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }

    // Lấy các booking đang trạng thái Checked-in (cần checkout)
    public List<BookingShort> findCheckedInBookings() {
        List<BookingShort> list = new ArrayList<>();
        String sql = "SELECT b.BookingID, b.RoomID, b.CustomerID, c.FullName, b.CheckInDate, b.CheckOutDate, b.Status " +
                     "FROM Bookings b JOIN Customers c ON b.CustomerID = c.CustomerID " +
                     "WHERE b.Status = 'Checked-in' ORDER BY b.BookingID";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql);
             ResultSet rs = ps.executeQuery()) {
            while (rs.next()) {
                BookingShort bs = new BookingShort();
                bs.bookingID = rs.getInt("BookingID");
                bs.roomID = rs.getInt("RoomID");
                bs.customerID = rs.getInt("CustomerID");
                bs.customerName = rs.getString("FullName");
                bs.checkInDate = rs.getDate("CheckInDate");
                bs.checkOutDate = rs.getDate("CheckOutDate");
                bs.status = rs.getString("Status");
                list.add(bs);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}