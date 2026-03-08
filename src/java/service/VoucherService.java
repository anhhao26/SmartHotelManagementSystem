package service;

import dao.VoucherDAO;
import model.Voucher;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class VoucherService {
    private final VoucherDAO voucherDAO = new VoucherDAO();

    public List<Voucher> getAllActiveVouchers() { return voucherDAO.findAllActive(); }

    public String saveVoucher(Voucher newVoucher) {
        Voucher existingVoucher = voucherDAO.findById(newVoucher.getVoucherCode());
        if (existingVoucher != null) {
            boolean isStillValid = existingVoucher.isIsActive() && existingVoucher.getUsedCount() < existingVoucher.getUsageLimit();
            if (isStillValid) {
                return "Lỗi: Mã Voucher này đang tồn tại và vẫn còn lượt sử dụng!";
            } else {
                existingVoucher.setDiscountValue(newVoucher.getDiscountValue());
                existingVoucher.setMinOrderValue(newVoucher.getMinOrderValue());
                existingVoucher.setUsageLimit(newVoucher.getUsageLimit());
                existingVoucher.setStartDate(newVoucher.getStartDate());
                existingVoucher.setEndDate(newVoucher.getEndDate());
                existingVoucher.setUsedCount(0); 
                existingVoucher.setIsActive(true);
                voucherDAO.update(existingVoucher);
                return "success";
            }
        } else {
            newVoucher.setUsedCount(0);
            newVoucher.setIsActive(true); 
            voucherDAO.insert(newVoucher);
            return "success";
        }
    }

    public boolean deleteVoucher(String voucherCode) {
        Voucher v = voucherDAO.findById(voucherCode);
        if (v != null) {
            v.setIsActive(false); 
            return voucherDAO.update(v);
        }
        return false;
    }

    public String checkVoucherValid(String code, BigDecimal orderValue) {
        Voucher v = voucherDAO.findById(code);
        if (v == null || !v.isIsActive()) return "Mã giảm giá không tồn tại hoặc đã bị khóa.";
        Date now = new Date();
        if (now.before(v.getStartDate())) return "Mã giảm giá chưa đến thời gian áp dụng.";
        if (now.after(v.getEndDate())) return "Mã giảm giá đã hết hạn.";
        if (v.getUsedCount() >= v.getUsageLimit()) return "Mã giảm giá đã hết lượt sử dụng.";
        if (orderValue.compareTo(v.getMinOrderValue()) < 0) return "Đơn hàng chưa đạt giá trị tối thiểu.";
        return "OK";
    }
}