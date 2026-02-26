<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.util.JPAUtil" %>
<%@ page import="jakarta.persistence.EntityManager" %>
<%@ page import="com.smarthotel.model.Invoice" %>
<%@ page import="com.smarthotel.model.InvoiceItem" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Invoice | SmartHotel</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    @media print { .no-print { display:none } }
  </style>
</head>
<body class="bg-white">
<%
  String sid = request.getParameter("invoiceId");
  Invoice inv = null;
  if (sid != null) {
    try {
      int iid = Integer.parseInt(sid);
      EntityManager em = JPAUtil.getEntityManager();
      try { inv = em.find(Invoice.class, iid); } finally { em.close(); }
    } catch (Exception e) { inv = null; }
  }
%>

<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h4>HÓA ĐƠN DỊCH VỤ</h4>
    <div>
      <button onclick="window.print()" class="btn btn-sm btn-primary no-print">In hóa đơn</button>
      <a class="btn btn-sm btn-outline-secondary no-print" href="<%=request.getContextPath()%>/reception/home.jsp">Quay lại</a>
    </div>
  </div>

  <% if (inv == null) { %>
    <div class="alert alert-warning">Không tìm thấy hóa đơn. Vui lòng truyền <code>?invoiceId=...</code> trong URL.</div>
  <% } else { %>
    <div class="row">
      <div class="col-md-6">
        <p><b>Invoice ID:</b> <%= inv.getInvoiceID() %></p>
        <p><b>Booking ID:</b> <%= inv.getBooking()!=null?inv.getBooking().getBookingID():"-" %></p>
        <p><b>Khách:</b> <%= inv.getCustomer()!=null?inv.getCustomer().getFullName():"-" %></p>
      </div>
      <div class="col-md-6 text-end">
        <p><b>Ngày:</b> <%= inv.getCreatedDate()!=null? new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm").format(inv.getCreatedDate()) : "-" %></p>
      </div>
    </div>

    <table class="table table-bordered mt-3">
      <thead class="table-light"><tr><th>#</th><th>Sản phẩm</th><th>SL</th><th>Đơn giá</th><th>Thành tiền</th></tr></thead>
      <tbody>
        <%
          List<InvoiceItem> items = inv.getItems();
          int idx = 1;
          double subtotal = 0;
          for (InvoiceItem it : items) {
            double line = (it.getUnitPrice()!=null?it.getUnitPrice():0.0) * (it.getQuantity()!=null?it.getQuantity():0);
            subtotal += line;
        %>
          <tr>
            <td><%= idx++ %></td>
            <td><%= it.getInventory()!=null?it.getInventory().getItemName():"-" %></td>
            <td><%= it.getQuantity()!=null?it.getQuantity():0 %></td>
            <td><%= String.format("%,.0f", it.getUnitPrice()!=null?it.getUnitPrice():0.0) %></td>
            <td><%= String.format("%,.0f", line) %></td>
          </tr>
        <% } %>
      </tbody>
      <tfoot>
        <tr><th colspan="4" class="text-end">Tổng</th><th><%= String.format("%,.0f", subtotal) %></th></tr>
        <tr><th colspan="4" class="text-end">Giảm giá</th><th><%= String.format("%,.0f", inv.getDiscount()!=null?inv.getDiscount():0.0) %></th></tr>
        <tr><th colspan="4" class="text-end">Thanh toán</th><th><%= String.format("%,.0f", inv.getTotalAmount()!=null?inv.getTotalAmount():subtotal) %></th></tr>
      </tfoot>
    </table>
  <% } %>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>