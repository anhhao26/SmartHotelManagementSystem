package com.smarthotel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "AdminServlet", urlPatterns = {"/admin"})
public class AdminServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Mọi request vào đây đã được AuthorizationFilter kiểm tra role.
        // Bạn có thể lấy dữ liệu thống kê từ database để truyền ra Dashboard tại đây.
        
        req.getRequestDispatcher("/admin/dashboard.jsp").forward(req, resp);
    }
}