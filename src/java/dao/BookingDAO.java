package com.smarthotel.dao;

import com.smarthotel.model.Booking;
import com.smarthotel.util.JPAUtil;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class BookingDAO {
    public static class BookingShort {
        public int bookingID;
        public int roomID;
        public int customerID;
        public String customerName;
        public java.util.Date checkInDate;
        public java.util.Date checkOutDate;
        public String status;
    }

    public Booking find(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try { 
            return em.find(Booking.class, id); 
        } finally { 
            em.close(); 
        }
    }

    public void merge(Booking b, EntityManager em) { 
        em.merge(b); 
    }

    public List<BookingShort> findPendingBookings() {
        return findBookingsByStatus("Pending");
    }

    public List<BookingShort> findCheckedInBookings() {
        return findBookingsByStatus("Checked-in");
    }

    // ĐÃ SỬA: Chuyển từ Native SQL sang JPQL để không bị phụ thuộc vào tên bảng SQL
    private List<BookingShort> findBookingsByStatus(String status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Dùng JPQL truy vấn qua Entity Booking thay vì truy vấn thẳng bảng SQL
            String jpql = "SELECT b FROM Booking b WHERE b.status = :status ORDER BY b.bookingID";
            TypedQuery<Booking> q = em.createQuery(jpql, Booking.class);
            q.setParameter("status", status);
            
            List<Booking> list = q.getResultList();
            List<BookingShort> out = new ArrayList<>();
            
            for (Booking b : list) {
                BookingShort bs = new BookingShort();
                bs.bookingID = b.getBookingID();
                
                // Lấy Room ID an toàn
                if (b.getRoom() != null) {
                    bs.roomID = b.getRoom().getRoomID();
                } else {
                    bs.roomID = 0;
                }
                
                // Lấy thông tin Khách hàng an toàn
                if (b.getCustomer() != null) {
                    bs.customerID = b.getCustomer().getCustomerID();
                    bs.customerName = b.getCustomer().getFullName();
                } else {
                    bs.customerID = 0;
                    bs.customerName = "Khách vãng lai";
                }
                
                bs.checkInDate = b.getCheckInDate();
                bs.checkOutDate = b.getCheckOutDate();
                bs.status = b.getStatus();
                out.add(bs);
            }
            return out;
        } finally {
            em.close();
        }
    }
}