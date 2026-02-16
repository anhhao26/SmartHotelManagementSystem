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
    public String saveVoucher(Voucher voucher) {
        // 1. Validate đầu vào (Tránh lỗi do người dùng nhập sai trên UI)
        if (voucher.getVoucherCode() == null || voucher.getVoucherCode().trim().isEmpty()) {
            return "Mã Voucher không được để trống!";
        }
        if (voucher.getDiscountValue() == null || voucher.getDiscountValue().compareTo(BigDecimal.ZERO) <= 0) {
            return "Giá trị giảm giá phải lớn hơn 0!";
        }
        if (voucher.getStartDate().after(voucher.getEndDate())) {
            return "Ngày bắt đầu không thể sau ngày kết thúc!";
        }

        // 2. Logic kiểm tra trùng lặp để quyết định Thêm hay Sửa
        Voucher existingVoucher = voucherDAO.findById(voucher.getVoucherCode());
        
        if (existingVoucher == null) {
            // Chưa tồn tại -> Bắt Thủ kho (DAO) Thêm mới
            boolean success = voucherDAO.insert(voucher);
            return success ? "OK" : "Lỗi hệ thống khi thêm mới Voucher.";
        } else {
            // Đã tồn tại -> Bắt Thủ kho (DAO) Cập nhật
            boolean success = voucherDAO.update(voucher);
            return success ? "OK" : "Lỗi hệ thống khi cập nhật Voucher.";
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
