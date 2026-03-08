package controller;

import dao.BookingDAO;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet("/confirmPayment")
public class ConfirmPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        
        // Cập nhật trạng thái thành Confirmed
        new BookingDAO().confirm(id);

        // Gửi mail giả lập
        String email = req.getParameter("email");
        if (email != null && !email.isEmpty()) {
            System.out.println("====== SYSTEM MAIL ======");
            System.out.println("Send mail to: " + email);
            System.out.println("Subject: Booking Confirm");
            System.out.println("Content: Your booking ID: " + id + " has been successfully confirmed.");
            System.out.println("=========================");
        }

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h2 style='color:green; text-align:center; margin-top:50px;'>Thanh toán và Xác nhận thành công!</h2>");
        resp.getWriter().println("<div style='text-align:center;'><a href='"+req.getContextPath()+"/guest/profile.jsp'>Quay lại Hồ sơ cá nhân</a></div>");
    }
}