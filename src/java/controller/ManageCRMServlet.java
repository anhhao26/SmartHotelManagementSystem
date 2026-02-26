package com.smarthotel.controller;

import com.smarthotel.model.Account;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ManageCRMServlet", urlPatterns = {"/admin/crm"})
public class ManageCRMServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession session = req.getSession(false);
        if (session == null) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        Object o = session.getAttribute("acc");
        if (!(o instanceof Account)) { resp.sendRedirect(req.getContextPath()+"/login"); return; }
        Account acc = (Account) o;
        String role = acc.getRole() == null ? "" : acc.getRole().trim().toUpperCase();
        if (!("ADMIN".equals(role) || "MANAGER".equals(role) || "SUPERADMIN".equals(role))) {
            resp.sendRedirect(req.getContextPath()+"/login"); return;
        }

        // TODO: load customer list via CustomerService if you want
        req.getRequestDispatcher("/admin/manageCRM.jsp").forward(req, resp);
    }
}