/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package service;

import dao.VoucherDAO;
import entity.Voucher;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VoucherService {
    
    private VoucherDAO voucherDAO;

    public VoucherService() {
        this.voucherDAO = new VoucherDAO();
    }

    // Lấy danh sách để đẩy lên bảng JTable trên Giao diện
    public List<Voucher> getAllActiveVouchers() {
        return voucherDAO.findAllActive();
    }

    // Logic Thêm/Sửa: Kiểm tra dữ liệu cực kỳ chặt chẽ trước khi lưu
    public String saveVoucher(Voucher newVoucher) {
    // 1. Vào Database tìm xem mã này đã từng tồn tại chưa
    Voucher existingVoucher = voucherDAO.findById(newVoucher.getVoucherCode());
    
    if (existingVoucher != null) {
        // --- TÌNH HUỐNG 1: MÃ NÀY ĐÃ TỪNG TỒN TẠI TRONG QUÁ KHỨ ---
        
        // Kiểm tra xem mã cũ còn đang hoạt động VÀ còn lượt dùng không?
        boolean isStillValid = existingVoucher.isIsActive() 
                            && existingVoucher.getUsedCount() < existingVoucher.getUsageLimit();
                            
        if (isStillValid) {
            // BỨC TƯỜNG LỬA: Mã vẫn đang xài ngon -> CHẶN LẠI, không cho tạo đè
            return "Lỗi: Mã Voucher này đang tồn tại và vẫn còn lượt sử dụng!";
        } else {
            // HỢP LỆ: Mã đã bị xóa mềm (isActive = false) HOẶC đã hết sạch lượt dùng
            // -> Tiến hành "Hồi sinh" (Ghi đè thông số mới lên dòng cũ)
            existingVoucher.setDiscountValue(newVoucher.getDiscountValue());
            existingVoucher.setMinOrderValue(newVoucher.getMinOrderValue());
            existingVoucher.setUsageLimit(newVoucher.getUsageLimit());
            existingVoucher.setStartDate(newVoucher.getStartDate());
            existingVoucher.setEndDate(newVoucher.getEndDate());
            
            // QUAN TRỌNG: Reset số lượt đã dùng về 0 và Mở khóa hoạt động lại
            existingVoucher.setUsedCount(0); 
            existingVoucher.setIsActive(true);
            
            // Gọi lệnh Update thay vì Insert
            voucherDAO.update(existingVoucher);
            return "success";
        }
    } else {
        // --- TÌNH HUỐNG 2: MÃ HOÀN TOÀN MỚI TINH ---
        newVoucher.setUsedCount(0);
        newVoucher.setIsActive(true); // Mặc định mở khóa
        
        // Gọi lệnh Insert thêm mới
        voucherDAO.insert(newVoucher);
        return "success";
    }
}

    // Xóa mềm Voucher: Chuyển IsActive = false rồi cập nhật
    public boolean deleteVoucher(String voucherCode) {
        Voucher v = voucherDAO.findById(voucherCode);
        if (v != null) {
            v.setIsActive(false); 
            return voucherDAO.update(v); // Cập nhật trạng thái xuống DB
        }
        return false;
    }

    // LOGIC CHO MODULE 6 (Đặt phòng): Khách nhập mã thì gọi hàm này
    public String checkVoucherValid(String code, BigDecimal orderValue) {
        // Lấy thông tin từ kho lên để xử lý
        Voucher v = voucherDAO.findById(code);

        // Bắt đầu chuỗi kiểm tra logic (Rules)
        if (v == null || !v.isIsActive()) {
            return "Mã giảm giá không tồn tại hoặc đã bị khóa.";
        }
        
        Date now = new Date();
        if (now.before(v.getStartDate())) {
            return "Mã giảm giá chưa đến thời gian áp dụng.";
        }
        if (now.after(v.getEndDate())) {
            return "Mã giảm giá đã hết hạn.";
        }
        
        if (v.getUsedCount() >= v.getUsageLimit()) {
            return "Mã giảm giá đã hết lượt sử dụng.";
        }
        
        if (orderValue.compareTo(v.getMinOrderValue()) < 0) {
            return "Đơn hàng chưa đạt giá trị tối thiểu (" + v.getMinOrderValue() + " VNĐ).";
        }

        return "OK"; // Pass mọi bài kiểm tra
    }
}