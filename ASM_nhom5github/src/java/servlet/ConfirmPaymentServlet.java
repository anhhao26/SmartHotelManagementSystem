/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import dao.BookingDAO;
import utils.MailUtil;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet("/confirmPayment")
public class ConfirmPaymentServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        int id = Integer.parseInt(req.getParameter("id"));

        new BookingDAO().confirm(id);

        MailUtil.send(
                req.getParameter("email"),
                "Booking Confirm",
                "Your booking ID: " + id
        );

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().println("<h2 style='color:green'>Thanh toán thành công!</h2>");
    }
}



