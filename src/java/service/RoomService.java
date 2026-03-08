package service;

import dao.RoomDAO;
import model.Room;
import model.RoomType;
import java.util.List;

public class RoomService {
    private final RoomDAO roomDAO = new RoomDAO();

    public List<Room> getRoomBoard() { return roomDAO.findAllRooms(); }
    public List<Room> getRoomsByStatus(String status) { return roomDAO.findRoomsByStatus(status); }

    public String changeRoomStatus(String roomNumber, String newStatus) {
        Room room = roomDAO.findByRoomNumber(roomNumber);
        if (room == null) return "Không tìm thấy phòng số " + roomNumber;

        String currentStatus = room.getStatus();
        boolean isValidFlow = false;
        
        if ("Available".equals(currentStatus) && ("Occupied".equals(newStatus) || "Maintenance".equals(newStatus))) {
            isValidFlow = true; 
        } else if ("Occupied".equals(currentStatus) && "Cleaning".equals(newStatus)) {
            isValidFlow = true; 
        } else if ("Cleaning".equals(currentStatus) && "Available".equals(newStatus)) {
            isValidFlow = true; 
        } else if ("Maintenance".equals(currentStatus) && "Available".equals(newStatus)) {
            isValidFlow = true; 
        } else if (currentStatus.equals(newStatus)) {
            return "Phòng đã ở trạng thái " + currentStatus + " rồi.";
        }

        if (isValidFlow) {
            room.setStatus(newStatus);
            boolean success = roomDAO.update(room);
            return success ? "OK" : "Lỗi khi lưu trạng thái vào CSDL!";
        } else {
            return "Lỗi quy trình: Không thể chuyển từ '" + currentStatus + "' trực tiếp sang '" + newStatus + "'.";
        }
    }

    public List<RoomType> getAllRoomTypes() { return roomDAO.getAllRoomTypes(); }

    public boolean addNewRoom(String roomNumber, int typeId, int floor, double price) {
        if (roomNumber == null || roomNumber.trim().isEmpty() || floor < 0 || price <= 0) return false;
        if (roomDAO.findByRoomNumber(roomNumber) != null) return false; // Chặn trùng mã phòng
        return roomDAO.addRoom(roomNumber, typeId, floor, price);
    }

    public boolean updateRoomPrice(String roomNumber, double newPrice) {
        if (roomNumber == null || newPrice <= 0) return false;
        return roomDAO.updateRoomPrice(roomNumber, newPrice);
    }
}