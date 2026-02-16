/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entity.Room;
import service.RoomService;
import java.io.IOException;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet(name = "RoomServlet", urlPatterns = {"/RoomServlet"})
public class RoomServlet extends HttpServlet {

    private RoomService roomService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo Service khi Servlet được chạy
        roomService = new RoomService();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Cấu hình UTF-8 để hiển thị tiếng Việt không bị lỗi font
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String action = request.getParameter("action");

        // 1. XỬ LÝ ĐỔI TRẠNG THÁI PHÒNG (Ví dụ Lễ tân click dọn phòng xong)
        if ("changeStatus".equals(action)) {
            String roomId = request.getParameter("roomId");
            String newStatus = request.getParameter("newStatus"); // Trạng thái muốn chuyển sang

            if (roomId != null && newStatus != null) {
                // Gọi Service xử lý logic vận hành
                String result = roomService.changeRoomStatus(roomId, newStatus);
                
                if ("OK".equals(result)) {
                    request.setAttribute("successMessage", "Đã cập nhật trạng thái phòng " + roomId + " thành " + newStatus);
                } else {
                    request.setAttribute("errorMessage", result); // Báo lỗi nếu sai quy trình
                }
            }
        }

        // 2. LẤY DANH SÁCH PHÒNG LÊN SƠ ĐỒ
        String statusFilter = request.getParameter("status"); // Nếu có chức năng lọc
        List<Room> list;
        
        if (statusFilter != null && !statusFilter.isEmpty() && !"All".equals(statusFilter)) {
            list = roomService.getRoomsByStatus(statusFilter);
        } else {
            list = roomService.getRoomBoard(); // Mặc định lấy tất cả
        }

        // Gắn danh sách vào request để đẩy sang JSP
        request.setAttribute("roomList", list);

        // 3. ĐẨY SANG GIAO DIỆN (Vì JSP nằm trong WEB-INF)
        // Lưu ý: Nếu bạn để trong thư mục con như WEB-INF/views/room.jsp thì sửa lại chuỗi bên dưới cho khớp nhé
        request.getRequestDispatcher("/WEB-INF/Room.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Với sơ đồ phòng chủ yếu dùng thao tác Click (GET), ta gọi thẳng sang doGet
        doGet(request, response);
    }
}
