/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import entity.Room;
import utils.JpaUtils;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RoomDAO {

    // 1. Tìm 1 phòng cụ thể theo RoomID (Ví dụ: "101")
    public Room findById(String roomId) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            return em.find(Room.class, roomId);
        }
    }

    // 2. Lấy toàn bộ danh sách phòng (kèm loại phòng) để UI vẽ sơ đồ
    public List<Room> findAllRooms() {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            String jpql = "SELECT r FROM Room r JOIN FETCH r.roomType ORDER BY r.floor, r.roomId";
            TypedQuery<Room> query = em.createQuery(jpql, Room.class);
            return query.getResultList();
        }
    }

    // 3. Lấy danh sách phòng theo trạng thái (Dùng cho UI lọc: Trống, Đang ở...)
    public List<Room> findRoomsByStatus(String status) {
        try (EntityManager em = JpaUtils.getEntityManager()) {
            String jpql = "SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.status = :status";
            TypedQuery<Room> query = em.createQuery(jpql, Room.class);
            query.setParameter("status", status);
            return query.getResultList();
        }
    }

    // 4. Cập nhật thông tin phòng (Bao gồm cả việc cập nhật Status)
    public boolean update(Room room) {
        EntityManager em = JpaUtils.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(room);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally {
            em.close();
        }
    }
    
public List<Room> findAvailableRooms() {
    try (EntityManager em = JpaUtils.getEntityManager()) {
        String jpql = "SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.status='Available'";
        return em.createQuery(jpql, Room.class).getResultList();
    }
}
}
