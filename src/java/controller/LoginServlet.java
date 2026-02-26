package com.smarthotel.controller;

import com.smarthotel.model.Account;
import com.smarthotel.service.AuthService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {

    private final AuthService authService = new AuthService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        req.getRequestDispatcher("/login.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.setCharacterEncoding("UTF-8");

        String username = req.getParameter("username") != null
                ? req.getParameter("username").trim() : "";

        String password = req.getParameter("password") != null
                ? req.getParameter("password").trim() : "";

        if (username.isEmpty() || password.isEmpty()) {
            req.setAttribute("err", "Vui lòng nhập đầy đủ tài khoản và mật khẩu!");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        Account acc = authService.login(username, password);

        if (acc == null) {
            req.setAttribute("err", "Sai tài khoản hoặc mật khẩu!");
            req.setAttribute("debug_msg", "User not found or password mismatch for '" + username + "'.");
            req.getRequestDispatcher("/login.jsp").forward(req, resp);
            return;
        }

        HttpSession session = req.getSession(true);
        session.setAttribute("acc", acc);
        session.setAttribute("ROLE", acc.getRole());
        session.setAttribute("CUST_ID", acc.getCustomerID());

        String role = acc.getRole() != null ? acc.getRole().trim().toUpperCase() : "";
        String base = req.getContextPath();

        // Đã sửa: Redirect về đúng Servlet mapping thay vì chọc thẳng vào file .jsp
        if ("MANAGER".equals(role) || "ADMIN".equals(role) || "SUPERADMIN".equals(role)) {
            resp.sendRedirect(base + "/admin");
            return;
        }

        if ("RECEPTIONIST".equals(role) || "STAFF".equals(role)) {
            resp.sendRedirect(base + "/reception"); 
            return;
        }

        resp.sendRedirect(base + "/guest/profile.jsp");
    }
}