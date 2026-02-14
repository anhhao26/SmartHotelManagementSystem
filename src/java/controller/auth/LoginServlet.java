package controller.auth;

import context.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.sql.*;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for (byte b : hash) {
                sb.append(String.format("%02X", b)); // IMPORTANT: UPPERCASE
            }
            return sb.toString();
        } catch (Exception e) {
            return password;
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        if (username == null || password == null || username.isEmpty() || password.isEmpty()) {
            req.setAttribute("err", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);
            return;
        }

        String hashedPassword = hashPassword(password);

        String sql = "SELECT * FROM Accounts WHERE Username = ? AND Password = ?";

        try (Connection conn = DBConnection.getConnection();
             PreparedStatement ps = conn.prepareStatement(sql)) {

            ps.setString(1, username);
            ps.setString(2, hashedPassword);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                String role = rs.getString("Role");
                int customerID = rs.getInt("CustomerID");

                model.Account acc = new model.Account();
                acc.setUsername(username);
                acc.setRole(role);
                acc.setCustomerID(customerID);

                HttpSession session = req.getSession();
                session.setAttribute("acc", acc);
                session.setAttribute("ROLE", role);
                session.setAttribute("CUST_ID", customerID);

                // FIX redirect (dùng contextPath để khỏi lỗi không chuyển trang)
                String base = req.getContextPath();

                if ("MANAGER".equalsIgnoreCase(role)) {
                    resp.sendRedirect(base + "/admin/dashboard.jsp");
                } else if ("RECEPTIONIST".equalsIgnoreCase(role)) {
                    resp.sendRedirect(base + "/reception/home.jsp");
                } else {
                    resp.sendRedirect(base + "/guest/profile.jsp");
                }
                return;
            }

            req.setAttribute("err", "Sai tài khoản hoặc mật khẩu!");
            req.getRequestDispatcher("login.jsp").forward(req, resp);

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("err", "Lỗi server: " + e.getMessage());
            req.getRequestDispatcher("login.jsp").forward(req, resp);
        }
    }
}