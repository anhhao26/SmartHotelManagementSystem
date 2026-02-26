package com.smarthotel.controller;

import com.smarthotel.model.Customer;
import com.smarthotel.service.CustomerService;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import jakarta.servlet.ServletException;
import java.io.IOException;

@WebServlet(name="UpdateProfileServlet", urlPatterns = {"/updateProfile"})
public class UpdateProfileServlet extends HttpServlet {
    private final CustomerService svc = new CustomerService();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            resp.sendRedirect(req.getContextPath() + "/login.jsp");
            return;
        }
        int cid = (Integer) session.getAttribute("CUST_ID");

        Customer c = svc.getById(cid);
        c.setFullName(req.getParameter("fullName"));
        c.setCccdPassport(req.getParameter("cccd"));
        c.setPhone(req.getParameter("phone"));
        c.setEmail(req.getParameter("email"));
        c.setAddress(req.getParameter("address"));
        c.setNationality(req.getParameter("nationality"));
        c.setGender(req.getParameter("gender"));
        c.setPreferences(req.getParameter("preferences"));
        String dob = req.getParameter("dob");
        try { if (dob != null && !dob.isEmpty()) c.setDateOfBirth(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dob)); } catch (Exception ex) {}

        boolean ok = svc.update(c);
        if (ok) resp.sendRedirect(req.getContextPath() + "/guest/profile.jsp?msg=ProfileUpdated");
        else resp.sendRedirect(req.getContextPath() + "/guest/profile.jsp?err=UpdateFailed");
    }
}