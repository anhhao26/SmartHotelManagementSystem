<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.dao.CustomerDAO" %>
<%@ page import="com.smarthotel.model.BookingHistory" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Lịch sử Giao dịch | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; color: #333; padding-bottom: 50px;}
      .history-card { border: none; border-radius: 12px; box-shadow: 0 4px 15px rgba(0,0,0,0.04); background: #fff; overflow: hidden; margin-top: 20px;}
      .table th { background-color: #f8f9fa; color: #495057; font-weight: 700; text-transform: uppercase; font-size: 13px; border-bottom: 2px solid #dee2e6; padding: 15px;}
      .table td { vertical-align: middle; font-weight: 500; border-bottom: 1px solid #f1f3f5; padding: 15px;}
  </style>
</head>
<body>
<%
    Integer cidObj = (Integer) session.getAttribute("CUST_ID");
    if (cidObj == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }
    int cid = cidObj;
    CustomerDAO dao = new CustomerDAO();
    List<BookingHistory> hist = dao.getBookingHistory(cid); 
%>

<nav class="navbar navbar-dark bg-primary px-4 py-3 shadow-sm">
    <div class="container">
        <a class="navbar-brand fw-bold" href="#">🏨 SMARTHOTEL</a>
        <a href="<%=request.getContextPath()%>/guest/profile.jsp" class="btn btn-outline-light btn-sm fw-bold">⬅ Về Hồ Sơ</a>
    </div>
</nav>

<div class="container py-4">
  <h3 class="fw-bold" style="color: #2c3e50;">🕒 Lịch sử lưu trú & Giao dịch</h3>
  
  <div class="card history-card">
      <div class="table-responsive">
          <table class="table table-hover mb-0 text-center">
            <thead>
              <tr>
                  <th>Mã Booking</th>
                  <th>Phòng</th>
                  <th>Check In</th>
                  <th>Check Out</th>
                  <th>Trạng thái</th>
                  <th>Mã Hóa đơn</th>
                  <th>Tổng tiền</th>
                  <th>Đã Giảm</th>
              </tr>
            </thead>
            <tbody>
              <% if(hist != null && !hist.isEmpty()) { 
                  for (BookingHistory h : hist) { %>
              <tr>
                <td class="fw-bold text-muted">#<%= h.getBookingID() %></td>
                <td class="text-primary fw-bold"><%= h.getRoomNumber() == null ? "-" : h.getRoomNumber() %></td>
                <td><%= h.getCheckInDate()!=null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(h.getCheckInDate()) : "-" %></td>
                <td><%= h.getCheckOutDate()!=null ? new java.text.SimpleDateFormat("dd/MM/yyyy").format(h.getCheckOutDate()) : "-" %></td>
                <td>
                    <span class="badge <%= h.getStatus().equals("Completed") ? "bg-success" : "bg-warning text-dark" %>">
                        <%= h.getStatus() %>
                    </span>
                </td>
                <td class="fw-bold text-secondary"><%= h.getInvoiceID() != null ? "#INV-" + h.getInvoiceID() : "-" %></td>
                <td class="text-danger fw-bold"><%= h.getTotalAmount() != null ? String.format("%,.0f", h.getTotalAmount()) + " đ" : "-" %></td>
                <td class="text-success"><%= h.getDiscount() != null ? String.format("%,.0f", h.getDiscount()) + " đ" : "-" %></td>
              </tr>
              <%  } 
               } else { %>
              <tr><td colspan="8" class="py-5 text-muted fw-bold bg-light">Bạn chưa có giao dịch nào trong hệ thống.</td></tr>
              <% } %>
            </tbody>
          </table>
      </div>
  </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>