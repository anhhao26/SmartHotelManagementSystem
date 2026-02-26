<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.dao.CustomerDAO" %>
<%@ page import="com.smarthotel.model.BookingHistory" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Lịch sử | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
</head>
<body>
<%
    Integer cidObj = (Integer) session.getAttribute("CUST_ID");
    if (cidObj == null) { response.sendRedirect(request.getContextPath()+"/login.jsp"); return; }
    int cid = cidObj;
    CustomerDAO dao = new CustomerDAO();
    List<BookingHistory> hist = dao.getBookingHistory(cid); // if your DAO has this method
%>

<div class="container py-4">
  <h3>Lịch sử lưu trú</h3>
  <table class="table table-striped table-bordered mt-3">
    <thead class="table-light">
      <tr><th>BookingID</th><th>Phòng</th><th>CheckIn</th><th>CheckOut</th><th>Status</th><th>Invoice</th><th>Total</th><th>Discount</th></tr>
    </thead>
    <tbody>
      <% for (BookingHistory h : hist) { %>
      <tr>
        <td><%= h.getBookingID() %></td>
        <td><%= h.getRoomNumber() == null ? "-" : h.getRoomNumber() %></td>
        <td><%= h.getCheckInDate()!=null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(h.getCheckInDate()) : "-" %></td>
        <td><%= h.getCheckOutDate()!=null ? new java.text.SimpleDateFormat("yyyy-MM-dd").format(h.getCheckOutDate()) : "-" %></td>
        <td><%= h.getStatus() %></td>
        <td><%= h.getInvoiceID() != null ? h.getInvoiceID() : "-" %></td>
        <td><%= h.getTotalAmount() != null ? String.format("%,.0f", h.getTotalAmount()) + " VNĐ" : "-" %></td>
        <td><%= h.getDiscount() != null ? String.format("%,.0f", h.getDiscount()) + " VNĐ" : "-" %></td>
      </tr>
      <% } %>
    </tbody>
  </table>

  <a class="btn btn-secondary" href="<%=request.getContextPath()%>/guest/profile.jsp">Quay về hồ sơ</a>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>