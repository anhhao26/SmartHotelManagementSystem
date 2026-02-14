package dao;
import context.DBConnection;
import model.Account;
import java.sql.*;

public class AccountDAO {

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));
            StringBuilder sb = new StringBuilder();
            for (byte b : hash) sb.append(String.format("%02x", b));
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    public Account login(String user, String pass) {
        String sql = "SELECT * FROM Accounts WHERE Username = ? AND Password = ?";
        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, user);
            ps.setString(2, hashPassword(pass));
            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                return new Account(
                    rs.getString("Username"),
                    rs.getString("Password"),
                    rs.getString("Role"),
                    rs.getInt("CustomerID")
                );
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}