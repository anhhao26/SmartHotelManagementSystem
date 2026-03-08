package dao;

import model.Room;
import model.RoomType;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.List;

public class RoomDAO {

    public Room findByRoomNumber(String roomNumber) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :rn", Room.class);
            query.setParameter("rn", roomNumber);
            List<Room> list = query.getResultList();
            return list.isEmpty() ? null : list.get(0);
        } finally { em.close(); }
    }

    public List<Room> findAllRooms() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT r FROM Room r JOIN FETCH r.roomType ORDER BY r.floor, r.roomNumber";
            return em.createQuery(jpql, Room.class).getResultList();
        } finally { em.close(); }
    }

    public List<Room> findRoomsByStatus(String status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT r FROM Room r JOIN FETCH r.roomType WHERE r.status = :status ORDER BY r.roomNumber";
            TypedQuery<Room> query = em.createQuery(jpql, Room.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally { em.close(); }
    }

    public boolean update(Room room) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            em.merge(room);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        } finally { em.close(); }
    }

    // --- BỔ SUNG TỪ BẠN KIA ---
    public List<RoomType> getAllRoomTypes() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            return em.createQuery("SELECT rt FROM RoomType rt", RoomType.class).getResultList();
        } finally { em.close(); }
    }

    public boolean addRoom(String roomNumber, int typeId, int floor, double price) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            RoomType rt = em.find(RoomType.class, typeId);
            
            Room newRoom = new Room();
            newRoom.setRoomNumber(roomNumber);
            newRoom.setFloor(floor);
            newRoom.setPrice(price);
            newRoom.setStatus("Available");
            newRoom.setRoomType(rt);

            em.persist(newRoom);
            em.getTransaction().commit();
            return true;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally { em.close(); }
    }

    public boolean updateRoomPrice(String roomNumber, double newPrice) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            // Cập nhật theo RoomNumber
            TypedQuery<Room> query = em.createQuery("SELECT r FROM Room r WHERE r.roomNumber = :rn", Room.class);
            query.setParameter("rn", roomNumber);
            List<Room> list = query.getResultList();
            
            if (!list.isEmpty()) {
                Room r = list.get(0);
                r.setPrice(newPrice);
                em.merge(r);
                em.getTransaction().commit();
                return true;
            }
            return false;
        } catch (Exception e) {
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            return false;
        } finally { em.close(); }
    }
}