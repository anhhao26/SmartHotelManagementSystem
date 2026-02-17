/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package servlet;

import dao.RoomDAO;
import entity.*;
import service.BookingService;

import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;

import java.io.IOException;
import java.sql.Date;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    BookingService service = new BookingService();
    RoomDAO roomDAO = new RoomDAO();

    protected void doPost(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException{

        try{

            Room room = roomDAO.findById(req.getParameter("roomId"));

            Booking b = new Booking();
            b.setRoom(room);
            b.setCheckIn(Date.valueOf(req.getParameter("checkIn")));
            b.setCheckOut(Date.valueOf(req.getParameter("checkOut")));
            b.setCustomerName(req.getParameter("name"));
            b.setEmail(req.getParameter("email"));
            b.setStatus("PENDING");
            b.setTotalPrice(room.getPrice());

            String rs = service.book(b);

            if(rs.equals("OK")){
                req.setAttribute("booking", b);
                req.getRequestDispatcher("payment.jsp").forward(req,resp);
            }else{
                req.setAttribute("error", rs);
                req.getRequestDispatcher("search.jsp").forward(req,resp);
            }

        }catch(Exception e){
            resp.getWriter().println("Error:"+e.getMessage());
        }
    }
}



