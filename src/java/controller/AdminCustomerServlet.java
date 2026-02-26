package com.smarthotel.controller;

import com.smarthotel.model.Customer;
import com.smarthotel.service.CustomerService;
import com.smarthotel.model.Account;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "AdminCustomerServlet", urlPatterns = {"/admin/customers"})
public class AdminCustomerServlet extends HttpServlet {
    private final CustomerService customerService = new CustomerService();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Customer> list = customerService.findAll(); 
        req.setAttribute("customers", list);
        req.getRequestDispatcher("/admin/manageCustomers.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("action");
        if ("addPoints".equalsIgnoreCase(action)) {
            try {
                int cid = Integer.parseInt(req.getParameter("customerId"));
                int add = Integer.parseInt(req.getParameter("points"));
                Customer c = customerService.getById(cid);
                
                if (c != null) {
                    // Cập nhật điểm
                    int newPoints = (c.getPoints() != null ? c.getPoints() : 0) + add;
                    c.setPoints(newPoints);
                    
                    // Giả lập tăng tổng chi tiêu tương ứng với số điểm được cộng (1 điểm = 100k)
                    // (Tùy chọn: có thể bỏ qua nếu Admin chỉ muốn cho điểm suông, không tăng TotalSpending)
                    double currentSpend = c.getTotalSpending() != null ? c.getTotalSpending() : 0;
                    double simulatedSpend = currentSpend + (add * 100000.0);
                    c.setTotalSpending(simulatedSpend);

                    // Upgrade to VIP nếu đạt ngưỡng 10 củ
                    if (simulatedSpend >= 10000000.0) {
                        c.setMemberType("VIP");
                    } else if (simulatedSpend > 0 && !"VIP".equalsIgnoreCase(c.getMemberType())) {
                        c.setMemberType("Member");
                    }
                    
                    customerService.update(c);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        resp.sendRedirect(req.getContextPath() + "/admin/customers");
    }
}