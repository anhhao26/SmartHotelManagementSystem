package controller;

import model.Room;
import model.RoomType;
import service.RoomService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "RoomServlet", urlPatterns = {"/RoomServlet"})
public class RoomServlet extends HttpServlet {

    private final RoomService roomService = new RoomService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        List<RoomType> typeList = roomService.getAllRoomTypes(); 
        request.setAttribute("typeList", typeList);

        String statusFilter = request.getParameter("status");
        List<Room> list;
        if (statusFilter != null && !statusFilter.isEmpty() && !"All".equals(statusFilter)) {
            list = roomService.getRoomsByStatus(statusFilter);
        } else {
            list = roomService.getRoomBoard(); 
        }

        request.setAttribute("roomList", list);
        request.getRequestDispatcher("/WEB-INF/Room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        String action = request.getParameter("action");

        if ("changeStatus".equals(action)) {
            String roomId = request.getParameter("roomId");
            String newStatus = request.getParameter("newStatus"); 
            if (roomId != null && newStatus != null) {
                String result = roomService.changeRoomStatus(roomId, newStatus);
                if ("OK".equals(result)) request.setAttribute("successMessage", "Đã cập nhật trạng thái phòng " + roomId + " thành " + newStatus);
                else request.setAttribute("errorMessage", result); 
            }
        } 
        else if ("addRoom".equals(action)) {
            String roomId = request.getParameter("roomId");
            int typeId = Integer.parseInt(request.getParameter("typeId"));
            int floor = Integer.parseInt(request.getParameter("floor"));
            double price = Double.parseDouble(request.getParameter("price"));
            boolean success = roomService.addNewRoom(roomId, typeId, floor, price);
            if (success) request.setAttribute("successMessage", "Đã tạo phòng " + roomId + " thành công!");
            else request.setAttribute("errorMessage", "Lỗi: Không thể tạo phòng (Kiểm tra lại mã phòng).");
        }
        else if ("updatePrice".equals(action)) {
            String roomId = request.getParameter("roomId"); 
            double newPrice = Double.parseDouble(request.getParameter("newPrice"));
            boolean success = roomService.updateRoomPrice(roomId, newPrice);
            if (success) request.setAttribute("successMessage", "Đã cập nhật giá mới cho phòng " + roomId);
            else request.setAttribute("errorMessage", "Lỗi: Không tìm thấy phòng hoặc giá không hợp lệ.");
        }
        doGet(request, response);
    }
}