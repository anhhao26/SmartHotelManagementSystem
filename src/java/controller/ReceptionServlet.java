package com.smarthotel.controller;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "ReceptionServlet", urlPatterns = {"/reception"})
public class ReceptionServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Mọi request vào đây đã được AuthorizationFilter kiểm tra role.
        req.getRequestDispatcher("/reception/home.jsp").forward(req, resp);
    }
}