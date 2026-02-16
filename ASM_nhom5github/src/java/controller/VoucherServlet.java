/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controller;

import entity.Voucher;
import service.VoucherService;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

// Đường dẫn URL mapping để JSP gọi tới
@WebServlet(name = "VoucherServlet", urlPatterns = {"/VoucherServlet"})
public class VoucherServlet extends HttpServlet {

    private VoucherService voucherService;

    @Override
    public void init() throws ServletException {
        // Khởi tạo Service khi Servlet chạy
        voucherService = new VoucherService(); 
    }

    // XỬ LÝ GET: Load danh sách hiển thị lên bảng HOẶC Xóa mã
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        String action = request.getParameter("action");

        // Nếu người dùng bấm nút "Xóa" trên bảng JSP
        if ("delete".equals(action)) {
            String code = request.getParameter("code");
            voucherService.deleteVoucher(code);
            // Xóa xong thì load lại trang
            response.sendRedirect("VoucherServlet");
            return;
        }

        // Mặc định: Lấy danh sách Voucher từ Service
        List<Voucher> list = voucherService.getAllActiveVouchers();
        
        // Đóng gói danh sách gửi sang file voucher.jsp
        request.setAttribute("voucherList", list);
        request.getRequestDispatcher("WEB-INF/Voucher.jsp").forward(request, response);
    }

    // XỬ LÝ POST: Nhận dữ liệu từ Form Thêm/Sửa gửi lên
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        
        // Lấy chữ từ các ô input của form HTML/JSP
        String code = request.getParameter("voucherCode");
        String discountStr = request.getParameter("discountValue");
        String minOrderStr = request.getParameter("minOrderValue");
        String usageLimitStr = request.getParameter("usageLimit");
        
        // Thẻ <input type="datetime-local"> trả về định dạng "yyyy-MM-dd'T'HH:mm"
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            // 1. Ép kiểu dữ liệu
            BigDecimal discount = new BigDecimal(discountStr);
            BigDecimal minOrder = new BigDecimal(minOrderStr == null || minOrderStr.isEmpty() ? "0" : minOrderStr);
            int usageLimit = Integer.parseInt(usageLimitStr);

            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");
            Date startDate = sdf.parse(startDateStr);
            Date endDate = sdf.parse(endDateStr);

            // 2. Đóng gói vào Entity
            Voucher v = new Voucher();
            v.setVoucherCode(code);
            v.setDiscountValue(discount);
            v.setMinOrderValue(minOrder);
            v.setStartDate(startDate);
            v.setEndDate(endDate);
            v.setUsageLimit(usageLimit);
            v.setUsedCount(0);
            v.setIsActive(true);

            // 3. Nhờ Service lưu vào CSDL
            String result = voucherService.saveVoucher(v);

            if (!"OK".equals(result)) {
                // Nếu lỗi logic (VD: Ngày kết thúc nhỏ hơn ngày bắt đầu), báo lỗi lại UI
                request.setAttribute("errorMessage", result);
                doGet(request, response); 
            } else {
                // Thành công thì load lại trang web
                response.sendRedirect("VoucherServlet");
            }

        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi dữ liệu đầu vào. Vui lòng kiểm tra lại số liệu hoặc ngày tháng!");
            doGet(request, response);
        }
    }
}
