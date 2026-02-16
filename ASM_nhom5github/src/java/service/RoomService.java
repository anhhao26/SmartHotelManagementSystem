/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.RoomDAO;
import entity.Room;
import java.util.List;

public class RoomService {
    
    private RoomDAO roomDAO;

    public RoomService() {
        this.roomDAO = new RoomDAO();
    }

    // Lấy toàn bộ phòng để vẽ Giao diện Sơ đồ phòng (Room Board)
    public List<Room> getRoomBoard() {
        return roomDAO.findAllRooms();
    }

    // Lọc phòng theo trạng thái
    public List<Room> getRoomsByStatus(String status) {
        return roomDAO.findRoomsByStatus(status);
    }

    // LOGIC VẬN HÀNH CHÍNH: Chuyển đổi trạng thái có kiểm soát
    public String changeRoomStatus(String roomId, String newStatus) {
        // 1. Hỏi kho xem phòng hiện tại đang ra sao
        Room room = roomDAO.findById(roomId);
        if (room == null) {
            return "Không tìm thấy phòng số " + roomId;
        }

        String currentStatus = room.getStatus();

        // 2. Kiểm tra luồng nghiệp vụ hợp lệ (Business Rules)
        boolean isValidFlow = false;
        
        if ("Available".equals(currentStatus) && ("Occupied".equals(newStatus) || "Maintenance".equals(newStatus))) {
            isValidFlow = true; // Trống -> Có khách ở, hoặc Trống -> Đem đi bảo trì
        } 
        else if ("Occupied".equals(currentStatus) && "Cleaning".equals(newStatus)) {
            isValidFlow = true; // Khách trả phòng -> Đang dọn
        } 
        else if ("Cleaning".equals(currentStatus) && "Available".equals(newStatus)) {
            isValidFlow = true; // Dọn xong -> Sẵn sàng
        } 
        else if ("Maintenance".equals(currentStatus) && "Available".equals(newStatus)) {
            isValidFlow = true; // Bảo trì xong -> Sẵn sàng
        } 
        else if (currentStatus.equals(newStatus)) {
            return "Phòng đã ở trạng thái " + currentStatus + " rồi.";
        }

        // 3. Nếu luồng đúng thì mới cho phép DAO cập nhật
        if (isValidFlow) {
            room.setStatus(newStatus);
            boolean success = roomDAO.update(room);
            return success ? "OK" : "Lỗi khi lưu trạng thái vào CSDL!";
        } else {
            // Chặn đứng các hành động sai lệch (VD: Đang ở Occupied -> Chuyển thẳng Available)
            return "Lỗi quy trình: Không thể chuyển từ '" + currentStatus + "' trực tiếp sang '" + newStatus + "'.";
        }
    }
}
