package controller;

import model.Voucher;
import service.VoucherService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.List;

@WebServlet(name = "VoucherServlet", urlPatterns = {"/VoucherServlet"})
public class VoucherServlet extends HttpServlet {

    private final VoucherService voucherService = new VoucherService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String action = request.getParameter("action");
        if ("delete".equals(action)) {
            String code = request.getParameter("code");
            voucherService.deleteVoucher(code);
            response.sendRedirect("VoucherServlet");
            return;
        }

        List<Voucher> list = voucherService.getAllActiveVouchers();
        request.setAttribute("voucherList", list);
        
        // ĐÃ SỬA: Thêm dấu gạch chéo (/) vào trước WEB-INF
        request.getRequestDispatcher("/WEB-INF/Voucher.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String code = request.getParameter("voucherCode");
        String discountStr = request.getParameter("discountValue");
        String minOrderStr = request.getParameter("minOrderValue");
        String usageLimitStr = request.getParameter("usageLimit");
        String startDateStr = request.getParameter("startDate");
        String endDateStr = request.getParameter("endDate");

        try {
            BigDecimal discount = new BigDecimal(discountStr);
            BigDecimal minOrder = new BigDecimal(minOrderStr == null || minOrderStr.isEmpty() ? "0" : minOrderStr);
            int usageLimit = Integer.parseInt(usageLimitStr);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm");

            Voucher v = new Voucher();
            v.setVoucherCode(code);
            v.setDiscountValue(discount);
            v.setMinOrderValue(minOrder);
            v.setStartDate(sdf.parse(startDateStr));
            v.setEndDate(sdf.parse(endDateStr));
            v.setUsageLimit(usageLimit);

            String result = voucherService.saveVoucher(v);

            if (!"OK".equals(result) && !"success".equals(result)) {
                request.setAttribute("errorMessage", result);
                doGet(request, response); 
            } else {
                response.sendRedirect("VoucherServlet");
            }
        } catch (Exception e) {
            e.printStackTrace();
            request.setAttribute("errorMessage", "Lỗi dữ liệu đầu vào. Vui lòng kiểm tra lại số liệu hoặc ngày tháng!");
            doGet(request, response);
        }
    }
}