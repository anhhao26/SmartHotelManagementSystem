/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import dao.RoomDAO;
import entity.Room;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/rooms")
public class AvailableRoomServlet extends HttpServlet {

    RoomDAO dao = new RoomDAO();

    protected void doGet(HttpServletRequest req,HttpServletResponse resp)
            throws ServletException,IOException{

        List<Room> rooms = dao.findAvailableRooms();

        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/WEB-INF/rooms.jsp")
           .forward(req, resp);
    }
}
