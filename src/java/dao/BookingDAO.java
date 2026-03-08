package dao;

import model.Booking;
import util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.Arrays;
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

    // Mở rộng phễu lấy cả đơn Pending và Confirmed
    public List<BookingShort> findPendingBookings() {
        return findBookingsByStatuses(Arrays.asList("Pending", "PENDING", "Confirmed", "CONFIRMED"));
    }

    public List<BookingShort> findCheckedInBookings() {
        return findBookingsByStatuses(Arrays.asList("Checked-in", "CHECKED-IN"));
    }

    // Tối ưu hóa hàm lọc để truyền được nhiều trạng thái cùng lúc
    private List<BookingShort> findBookingsByStatuses(List<String> statuses) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            String jpql = "SELECT b FROM Booking b WHERE b.status IN :statuses ORDER BY b.bookingID DESC";
            TypedQuery<Booking> q = em.createQuery(jpql, Booking.class);
            q.setParameter("statuses", statuses);
            
            List<Booking> list = q.getResultList();
            List<BookingShort> out = new ArrayList<>();
            
            for (Booking b : list) {
                BookingShort bs = new BookingShort();
                bs.bookingID = b.getBookingID();
                
                if (b.getRoom() != null) {
                    bs.roomID = b.getRoom().getRoomID();
                } else {
                    bs.roomID = 0;
                }
                
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

    // --- CÁC HÀM XỬ LÝ ĐẶT PHÒNG ---
    public boolean saveBooking(Booking b){
        EntityManager em = JPAUtil.getEntityManager();
        try{
            em.getTransaction().begin();
            
            // Logic chống trùng phòng
            // Bỏ chặn trạng thái PENDING để phòng vẫn Available khi chưa trả tiền
            Long conflict = em.createQuery(
            "SELECT COUNT(b) FROM Booking b WHERE b.room.roomID = :roomId " +
            "AND UPPER(b.status) IN ('CONFIRMED', 'CHECKED-IN') " +
            "AND :checkIn < b.checkOutDate AND :checkOut > b.checkInDate", Long.class)
            .setParameter("roomId", b.getRoom().getRoomID())
            .setParameter("checkIn", b.getCheckInDate())
            .setParameter("checkOut", b.getCheckOutDate())
            .getSingleResult();

            if(conflict > 0){
                em.getTransaction().rollback();
                return false;
            }

            em.persist(b);
            em.getTransaction().commit();
            return true;
        }catch(Exception e){
            if (em.getTransaction().isActive()) em.getTransaction().rollback();
            e.printStackTrace();
            return false;
        }finally{
            em.close();
        }
    }

    // ĐÃ FIX VÀ NÂNG CẤP: Hàm này được gọi khi VNPay trả về thành công
    // Nó sẽ đổi Booking -> Confirmed, Room -> Occupied VÀ cộng dồn tiền cho Customer
    public void confirm(int id) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            em.getTransaction().begin();
            Booking b = em.find(Booking.class, id);
            
            if (b != null) {
                // 1. Cập nhật hóa đơn thành Đã Thanh Toán
                b.setStatus("Confirmed"); 
                
                // 2. CHÍNH THỨC KHÓA PHÒNG
                if (b.getRoom() != null) {
                    b.getRoom().setStatus("Occupied");
                }
                
                // 3. TÍNH TỔNG TIỀN CHI TIÊU VÀ CỘNG ĐIỂM CHO KHÁCH
                if (b.getCustomer() != null) {
                    model.Customer customer = b.getCustomer();
                    
                    // Lấy chi tiêu hiện tại (kiểm tra null để tránh lỗi)
                    double currentSpending = customer.getTotalSpending() != null ? customer.getTotalSpending() : 0.0;
                    
                    // Cộng dồn tiền hóa đơn này vào tổng chi tiêu
                    customer.setTotalSpending(currentSpending + b.getTotalAmount());
                    
                    // Logic cộng điểm: Ví dụ 100,000 VND = 1 điểm thưởng
                    int currentPoints = customer.getPoints() != null ? customer.getPoints() : 0;
                    int addedPoints = (int) (b.getTotalAmount() / 100000);
                    customer.setPoints(currentPoints + addedPoints);
                }
                
                em.merge(b);
            }
            em.getTransaction().commit();
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            e.printStackTrace();
        } finally {
            em.close();
        }
    }
    
    // Thêm hàm này để lấy lịch sử đặt phòng của một khách hàng cụ thể
    public List<Booking> findByCustomerId(int customerId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            // Lấy tất cả Booking có customerID trùng khớp, sắp xếp mới nhất lên đầu
            String jpql = "SELECT b FROM Booking b WHERE b.customer.customerID = :cid ORDER BY b.bookingID DESC";
            TypedQuery<Booking> query = em.createQuery(jpql, Booking.class);
            query.setParameter("cid", customerId);
            return query.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
            return new ArrayList<>(); // Trả về list rỗng nếu có lỗi, tránh bị NullPointerException
        } finally {
            em.close();
        }
    }
}