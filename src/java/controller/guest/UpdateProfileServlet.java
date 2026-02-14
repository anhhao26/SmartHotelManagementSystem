package controller.guest;

import dao.CustomerDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import model.Customer;

@WebServlet(name="UpdateProfileServlet", urlPatterns = {"/updateProfile"})
public class UpdateProfileServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        HttpSession session = req.getSession(false);
        if (session == null || session.getAttribute("CUST_ID") == null) {
            resp.sendRedirect("../login.jsp");
            return;
        }
        int cid = (Integer) session.getAttribute("CUST_ID");

        String fullName = req.getParameter("fullName");
        String cccd = req.getParameter("cccd");
        String phone = req.getParameter("phone");
        String email = req.getParameter("email");
        String address = req.getParameter("address");
        String nationality = req.getParameter("nationality");
        String dob = req.getParameter("dob");
        String gender = req.getParameter("gender");
        String preferences = req.getParameter("preferences");

        Customer c = new Customer();
        c.setCustomerID(cid);
        c.setFullName(fullName);
        c.setCccdPassport(cccd);
        c.setPhone(phone);
        c.setEmail(email);
        c.setAddress(address);
        c.setNationality(nationality);
        c.setGender(gender);
        c.setPreferences(preferences);
        if (dob != null && !dob.isEmpty()) {
            try { c.setDateOfBirth(new java.text.SimpleDateFormat("yyyy-MM-dd").parse(dob)); } catch (Exception ex) {}
        }

        CustomerDAO dao = new CustomerDAO();
        boolean ok = dao.updateCustomerProfile(c);
        if (ok) resp.sendRedirect("guest/profile.jsp?msg=Profile Updated");
        else resp.sendRedirect("guest/profile.jsp?err=Update Failed");
    }
}