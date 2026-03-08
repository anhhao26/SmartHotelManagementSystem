package controller;

import util.VNPayConfig;
import util.VNPayUtils;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@WebServlet("/create_payment")
public class VNPayServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
        // 1. Lấy dữ liệu từ request
        String amountStr = request.getParameter("amount");
        String bookingId = request.getParameter("bookingId");
        
        // In log kiểm tra giá trị nhận được từ JSP
        System.out.println("LOG VNPAY: BookingID=" + bookingId + " | RawAmount=" + amountStr);

        if (amountStr == null || bookingId == null) {
            response.getWriter().println("Thieu thong tin thanh toan!");
            return;
        }

        // --- ĐÃ FIX LỖI NHÂN 10 LẦN TIỀN ---
        // Xóa dấu phẩy (nếu có do format 1,000,000) nhưng giữ lại dấu chấm thập phân
        amountStr = amountStr.replace(",", "");
        
        // Đọc giá trị dưới dạng số thực (Double) để hệ thống hiểu đuôi ".0" (ví dụ: 1000000.0)
        double parsedAmount = Double.parseDouble(amountStr);
        
        // Nhân 100 theo chuẩn VNPay và ép về số nguyên (long)
        long amount = (long) (parsedAmount * 100); 
        // -----------------------------------
        
        // Mã tham chiếu duy nhất (Thêm timestamp để tránh lỗi trùng mã khi test lại)
        String vnp_TxnRef = bookingId + "_" + System.currentTimeMillis();
        String vnp_OrderInfo = "Thanh toan don dat phong " + bookingId;
        String vnp_IpAddr = request.getRemoteAddr();
        
        Calendar cld = Calendar.getInstance(TimeZone.getTimeZone("Etc/GMT+7"));
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = formatter.format(cld.getTime());
        
        cld.add(Calendar.MINUTE, 15);
        String vnp_ExpireDate = formatter.format(cld.getTime());

        // 2. Thiết lập tham số
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", "2.1.0");
        vnp_Params.put("vnp_Command", "pay");
        vnp_Params.put("vnp_TmnCode", VNPayConfig.vnp_TmnCode);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", "VND");
        vnp_Params.put("vnp_TxnRef", vnp_TxnRef);
        vnp_Params.put("vnp_OrderInfo", vnp_OrderInfo);
        vnp_Params.put("vnp_OrderType", "other");
        vnp_Params.put("vnp_Locale", "vn");
        vnp_Params.put("vnp_ReturnUrl", VNPayConfig.vnp_ReturnUrl);
        vnp_Params.put("vnp_IpAddr", vnp_IpAddr);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        vnp_Params.put("vnp_ExpireDate", vnp_ExpireDate);

        // 3. Sắp xếp và tạo chuỗi Query + HashData
        List<String> fieldNames = new ArrayList<>(vnp_Params.keySet());
        Collections.sort(fieldNames);

        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        
        Iterator<String> itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = itr.next();
            String fieldValue = vnp_Params.get(fieldName);
            if (fieldValue != null && fieldValue.length() > 0) {
                // Build hash data
                hashData.append(fieldName).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                // Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString())).append('=').append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }

        // 4. Tạo Secure Hash và redirect
        String queryUrl = query.toString();
        String vnp_SecureHash = VNPayUtils.hmacSHA512(VNPayConfig.vnp_HashSecret, hashData.toString());
        queryUrl += "&vnp_SecureHash=" + vnp_SecureHash;
        
        response.sendRedirect(VNPayConfig.vnp_PayUrl + "?" + queryUrl);
    }
}