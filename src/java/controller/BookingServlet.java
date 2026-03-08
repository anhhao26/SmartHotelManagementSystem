package controller;

import dao.CustomerDAO;
import dao.RoomDAO;
import dao.VoucherDAO;
import model.Booking;
import model.Customer;
import model.Room;
import model.Voucher;
import service.BookingService;
import service.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;

@WebServlet("/booking")
public class BookingServlet extends HttpServlet {

    private final BookingService bookingService = new BookingService();
    private final RoomDAO roomDAO = new RoomDAO();
    private final CustomerDAO customerDAO = new CustomerDAO();
    
    // TÍCH HỢP VOUCHER
    private final VoucherService voucherService = new VoucherService();
    private final VoucherDAO voucherDAO = new VoucherDAO();

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            HttpSession session = req.getSession(false);
            if (session == null || session.getAttribute("CUST_ID") == null) {
                resp.sendRedirect(req.getContextPath() + "/login.jsp?err=Vui long dang nhap de dat phong");
                return;
            }
            int customerId = (Integer) session.getAttribute("CUST_ID");
            Customer customer = customerDAO.findById(customerId);

            String roomNumberInput = req.getParameter("roomId");
            Room room = roomDAO.findByRoomNumber(roomNumberInput);

            if (room == null) {
                req.setAttribute("error", "Phòng không tồn tại!");
                req.getRequestDispatcher("webapp/search.jsp").forward(req, resp);
                return;
            }

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            Booking b = new Booking();
            b.setRoom(room);
            b.setCustomer(customer);
            b.setCheckInDate(sdf.parse(req.getParameter("checkIn")));
            b.setCheckOutDate(sdf.parse(req.getParameter("checkOut")));
            b.setStatus("Pending");

            // TÍNH TIỀN GỐC
            double pricePerNight = room.getPrice(); // Giá phòng tự định nghĩa
            long diff = b.getCheckOutDate().getTime() - b.getCheckInDate().getTime();
            long days = diff / (1000 * 60 * 60 * 24);
            if (days <= 0) days = 1; 
            
            double totalAmount = pricePerNight * days;

            // XỬ LÝ VOUCHER (NẾU KHÁCH NHẬP)
            String voucherCode = req.getParameter("voucherCode");
            if (voucherCode != null && !voucherCode.trim().isEmpty()) {
                String vCheck = voucherService.checkVoucherValid(voucherCode, BigDecimal.valueOf(totalAmount));
                
                if (!"OK".equals(vCheck)) {
                    req.setAttribute("error", "LỖI VOUCHER: " + vCheck);
                    req.getRequestDispatcher("webapp/search.jsp").forward(req, resp);
                    return; // Chặn lại không cho đặt nếu voucher sai
                }
                
                // Nếu OK -> Trừ tiền
                Voucher v = voucherDAO.findById(voucherCode);
                totalAmount = totalAmount - v.getDiscountValue().doubleValue();
                if (totalAmount < 0) totalAmount = 0; // Không để âm tiền
                
                // Tăng lượt dùng voucher lên 1
                v.setUsedCount(v.getUsedCount() + 1);
                voucherDAO.update(v);
            }

            b.setTotalAmount(totalAmount);
            String rs = bookingService.book(b);

            if (rs.equals("OK")) {
                
                // ĐÃ SỬA: KHÔNG khóa phòng ở đây nữa. 
                // Phòng vẫn là Available cho đến khi thanh toán VNPay thành công.
                
                req.setAttribute("booking", b);
                req.getRequestDispatcher("webapp/payment.jsp").forward(req, resp);
            } else {
                req.setAttribute("error", rs);
                req.getRequestDispatcher("webapp/search.jsp").forward(req, resp);
            }

        } catch (Exception e) {
            e.printStackTrace();
            req.setAttribute("error", "Lỗi dữ liệu: " + e.getMessage());
            req.getRequestDispatcher("webapp/search.jsp").forward(req, resp);
        }
    }
}