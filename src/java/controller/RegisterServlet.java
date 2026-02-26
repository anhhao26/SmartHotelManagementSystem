package com.smarthotel.controller;

import com.smarthotel.dao.AccountDAO;
import com.smarthotel.model.Account;
import com.smarthotel.model.Customer;
import com.smarthotel.service.AuthService;
import com.smarthotel.service.CustomerService;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;
import java.text.SimpleDateFormat;

@WebServlet(name="RegisterServlet", urlPatterns = {"/register"})
public class RegisterServlet extends HttpServlet {
    private final CustomerService custSvc = new CustomerService();
    private final AuthService authSvc = new AuthService();
    private final AccountDAO accountDAO = new AccountDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
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

        if (username == null || username.trim().isEmpty() ||
            password == null || password.trim().isEmpty() ||
            fullName == null || fullName.trim().isEmpty()) {
            req.setAttribute("err", "Vui lòng nhập đầy đủ Username / Password / FullName");
req.getRequestDispatcher("/guest/register.jsp").forward(req, resp);            return;
        }

        if (password.length() < 4) {
            req.setAttribute("err", "Mật khẩu phải từ 4 ký tự trở lên!");
req.getRequestDispatcher("/guest/register.jsp").forward(req, resp);            return;
        }

        // CHECK username exists (prevent PK/unique violation)
        if (accountDAO.existsByUsername(username)) {
            req.setAttribute("err", "Username đã tồn tại!");
req.getRequestDispatcher("/guest/register.jsp").forward(req, resp);            return;
        }

        // CHECK customer unique fields
        if (custSvc.existsUnique(cccd, phone, email)) {
            req.setAttribute("err", "Khách hàng đã tồn tại (CCCD/Phone/Email bị trùng)!");
req.getRequestDispatcher("/guest/register.jsp").forward(req, resp);            return;
        }

        Customer c = new Customer();
        c.setFullName(fullName);
        c.setCccdPassport(cccd);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setNationality(nationality);
        c.setPreferences(preferences);
        c.setMemberType("Guest");
        c.setPoints(0);
        c.setTotalSpending(0.0);
        try {
            if (dob != null && !dob.isEmpty()) c.setDateOfBirth(new SimpleDateFormat("yyyy-MM-dd").parse(dob));
        } catch (Exception ex) { /* ignore parse error */ }

        // create customer then account
        Customer created = custSvc.create(c);

        Account a = new Account();
        a.setUsername(username);
        a.setRole("GUEST");
        a.setCustomerID(created.getCustomerID());

        // register account (AccountDAO will hash password)
        authSvc.registerAccount(a, password);

        // auto login
        HttpSession session = req.getSession();
        session.setAttribute("acc", a);
        session.setAttribute("ROLE", "GUEST");
        session.setAttribute("CUST_ID", created.getCustomerID());
        resp.sendRedirect(req.getContextPath() + "/guest/profile.jsp?msg=register_success");
    }
}