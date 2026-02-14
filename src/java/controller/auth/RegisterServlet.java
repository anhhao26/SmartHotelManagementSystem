package controller.auth;

import context.DBConnection;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.*;
import java.text.SimpleDateFormat;

@WebServlet(name="RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {

    // demo hash password (cơ bản)
    private String hashPassword(String password) {
        try {
            java.security.MessageDigest md = java.security.MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes("UTF-8"));

            StringBuilder sb = new StringBuilder();
            for(byte b : hash){
                sb.append(String.format("%02x", b));
            }
            return sb.toString();
        } catch (Exception e) {
            return password; // fallback
        }
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username");
        String password = req.getParameter("password");

        String fullName = req.getParameter("fullName");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");

        String cccd = req.getParameter("cccd");
        String nationality = req.getParameter("nationality");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String preferences = req.getParameter("preferences");

        // ==== VALIDATE BASIC ====
        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {

            req.setAttribute("err", "Vui lòng nhập đầy đủ Username / Password / FullName");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        if (password.length() < 4) {
            req.setAttribute("err", "Mật khẩu phải từ 4 ký tự trở lên!");
            req.getRequestDispatcher("register.jsp").forward(req, resp);
            return;
        }

        java.sql.Date sqlDob = null;
        if (dob != null && !dob.trim().isEmpty()) {
            try {
                java.util.Date parsed = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
                sqlDob = new java.sql.Date(parsed.getTime());
            } catch (Exception ex) {
                req.setAttribute("err", "Ngày sinh không hợp lệ!");
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }
        }

        String hashedPassword = hashPassword(password);

        // ==== SQL ====
        String checkUsernameSQL = "SELECT * FROM Accounts WHERE Username = ?";
        String checkCustomerSQL = "SELECT * FROM Customers WHERE CCCD_Passport = ? OR Phone = ? OR Email = ?";

        String insertCustSql =
                "INSERT INTO Customers (FullName, TotalSpending, Points, MemberType, CCCD_Passport, Phone, Email, Address, Nationality, DateOfBirth, Gender, Preferences) " +
                "VALUES (?, 0, 0, 'Guest', ?, ?, ?, ?, ?, ?, ?, ?)";

        String insertAcc =
                "INSERT INTO Accounts (Username, Password, Role, CustomerID) VALUES (?, ?, 'GUEST', ?)";

        try (Connection conn = DBConnection.getConnection()) {

            conn.setAutoCommit(false);

            try {

                // ==== 1) CHECK USERNAME EXISTS ====
                try (PreparedStatement ps = conn.prepareStatement(checkUsernameSQL)) {
                    ps.setString(1, username);
                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        req.setAttribute("err", "Username đã tồn tại!");
                        req.getRequestDispatcher("register.jsp").forward(req, resp);
                        return;
                    }
                }

                // ==== 2) CHECK CUSTOMER EXISTS BY CCCD/PHONE/EMAIL ====
                try (PreparedStatement ps = conn.prepareStatement(checkCustomerSQL)) {
                    ps.setString(1, cccd);
                    ps.setString(2, phone);
                    ps.setString(3, email);

                    ResultSet rs = ps.executeQuery();
                    if (rs.next()) {
                        req.setAttribute("err", "Khách hàng đã tồn tại (CCCD/Phone/Email bị trùng)!");
                        req.getRequestDispatcher("register.jsp").forward(req, resp);
                        return;
                    }
                }

                // ==== 3) INSERT CUSTOMER ====
                int customerId = -1;

                try (PreparedStatement ps = conn.prepareStatement(insertCustSql, Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, fullName);
                    ps.setString(2, cccd);
                    ps.setString(3, phone);
                    ps.setString(4, email);
                    ps.setString(5, address);
                    ps.setString(6, nationality);

                    if (sqlDob != null) ps.setDate(7, sqlDob);
                    else ps.setNull(7, Types.DATE);

                    ps.setString(8, gender);
                    ps.setString(9, preferences);

                    int n = ps.executeUpdate();
                    if (n == 0) throw new Exception("Insert customer failed!");

                    ResultSet keys = ps.getGeneratedKeys();
                    if (keys.next()) {
                        customerId = keys.getInt(1);
                    } else {
                        throw new Exception("Không lấy được CustomerID!");
                    }
                }

                // ==== 4) INSERT ACCOUNT ====
                try (PreparedStatement ps2 = conn.prepareStatement(insertAcc)) {
                    ps2.setString(1, username);
                    ps2.setString(2, hashedPassword);
                    ps2.setInt(3, customerId);

                    int n2 = ps2.executeUpdate();
                    if (n2 == 0) throw new Exception("Insert account failed!");
                }

                // ==== 5) COMMIT ====
                conn.commit();

                // ==== 6) AUTO LOGIN ====
                model.Account acc = new model.Account();
                acc.setUsername(username);
                acc.setPassword(hashedPassword);
                acc.setRole("GUEST");
                acc.setCustomerID(customerId);

                HttpSession session = req.getSession();
                session.setAttribute("acc", acc);
                session.setAttribute("ROLE", acc.getRole());
                session.setAttribute("CUST_ID", acc.getCustomerID());

                resp.sendRedirect("guest/profile.jsp?msg=register_success");
                return;

            } catch (Exception e) {
                conn.rollback();
                e.printStackTrace();

                req.setAttribute("err", "Register failed: " + e.getMessage());
                req.getRequestDispatcher("register.jsp").forward(req, resp);
                return;
            }

        } catch (Exception ex) {
            ex.printStackTrace();
            req.setAttribute("err", "Server error: " + ex.getMessage());
            req.getRequestDispatcher("register.jsp").forward(req, resp);
        }
    }
}