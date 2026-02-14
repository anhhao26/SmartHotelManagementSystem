package dao;

import context.DBConnection;
import model.Customer;
import model.BookingHistory;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class CustomerDAO {

    // Tạo customer mới, trả về customerID
    public int createCustomer(Customer c) {
        String sql = "INSERT INTO Customers (FullName, TotalSpending, Points, MemberType, CCCD_Passport, Phone, Email, Address, Nationality, DateOfBirth, Gender, Preferences) "
                   + "VALUES (?, 0, 0, 'Guest', ?, ?, ?, ?, ?, ?, ?, ?)";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getCccdPassport());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getNationality());
            if (c.getDateOfBirth() != null) ps.setDate(7, new java.sql.Date(c.getDateOfBirth().getTime())); else ps.setNull(7, Types.DATE);
            ps.setString(8, c.getGender());
            ps.setString(9, c.getPreferences());
            int n = ps.executeUpdate();
            if (n == 0) return -1;
            try (ResultSet keys = ps.getGeneratedKeys()) {
                if (keys.next()) return keys.getInt(1);
            }
        } catch (Exception e) { e.printStackTrace(); }
        return -1;
    }

    // Lấy theo ID
    public Customer getCustomerByID(int id) {
        String sql = "SELECT * FROM Customers WHERE CustomerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, id);
            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    Customer c = new Customer();
                    c.setCustomerID(rs.getInt("CustomerID"));
                    c.setFullName(rs.getString("FullName"));
                    c.setTotalSpending(rs.getDouble("TotalSpending"));
                    c.setPoints(rs.getInt("Points"));
                    c.setMemberType(rs.getString("MemberType"));
                    c.setCccdPassport(rs.getString("CCCD_Passport"));
                    c.setPhone(rs.getString("Phone"));
                    c.setEmail(rs.getString("Email"));
                    c.setAddress(rs.getString("Address"));
                    c.setNationality(rs.getString("Nationality"));
                    Date dob = rs.getDate("DateOfBirth");
                    if (dob != null) c.setDateOfBirth(new java.util.Date(dob.getTime()));
                    c.setGender(rs.getString("Gender"));
                    c.setPreferences(rs.getString("Preferences"));
                    return c;
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return null;
    }

    // Cập nhật profile
    public boolean updateCustomerProfile(Customer c) {
        String sql = "UPDATE Customers SET FullName=?, CCCD_Passport=?, Phone=?, Email=?, Address=?, Nationality=?, DateOfBirth=?, Gender=?, Preferences=? WHERE CustomerID = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setString(1, c.getFullName());
            ps.setString(2, c.getCccdPassport());
            ps.setString(3, c.getPhone());
            ps.setString(4, c.getEmail());
            ps.setString(5, c.getAddress());
            ps.setString(6, c.getNationality());
            if (c.getDateOfBirth() != null) ps.setDate(7, new java.sql.Date(c.getDateOfBirth().getTime())); else ps.setNull(7, Types.DATE);
            ps.setString(8, c.getGender());
            ps.setString(9, c.getPreferences());
            ps.setInt(10, c.getCustomerID());
            int n = ps.executeUpdate();
            return n > 0;
        } catch (Exception e) { e.printStackTrace(); }
    return false;
    }

    // Lấy lịch sử booking + invoice cho khách
    public List<BookingHistory> getBookingHistory(int customerID) {
        List<BookingHistory> list = new ArrayList<>();
        String sql =
            "SELECT b.BookingID, r.RoomNumber, b.CheckInDate, b.CheckOutDate, b.Status, i.InvoiceID, i.TotalAmount, i.Discount, i.CreatedDate " +
            "FROM Bookings b " +
            "LEFT JOIN Rooms r ON r.RoomID = b.RoomID " +
            "LEFT JOIN Invoices i ON i.BookingID = b.BookingID " +
            "WHERE b.CustomerID = ? " +
            "ORDER BY b.BookingID DESC";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {
            ps.setInt(1, customerID);
            try (ResultSet rs = ps.executeQuery()) {
                while (rs.next()) {
                    BookingHistory h = new BookingHistory();
                    h.setBookingID(rs.getInt("BookingID"));
                    h.setRoomNumber(rs.getString("RoomNumber"));
                    java.sql.Date ci = rs.getDate("CheckInDate");
                    java.sql.Date co = rs.getDate("CheckOutDate");
                    if (ci != null) h.setCheckInDate(new java.util.Date(ci.getTime()));
                    if (co != null) h.setCheckOutDate(new java.util.Date(co.getTime()));
                    h.setStatus(rs.getString("Status"));
                    int iid = rs.getInt("InvoiceID");
                    if (!rs.wasNull()) h.setInvoiceID(iid);
                    double ta = rs.getDouble("TotalAmount");
                    if (!rs.wasNull()) h.setTotalAmount(ta);
                    double disc = rs.getDouble("Discount");
                    if (!rs.wasNull()) h.setDiscount(disc);
                    Timestamp invt = rs.getTimestamp("CreatedDate");
                    if (invt != null) h.setInvoiceDate(new java.util.Date(invt.getTime()));
                    list.add(h);
                }
            }
        } catch (Exception e) { e.printStackTrace(); }
        return list;
    }
}