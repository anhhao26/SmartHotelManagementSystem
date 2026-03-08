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
      body { background-color: #e9ecef; padding: 40px 0; font-family: 'Courier New', Courier, monospace;}
      
      .invoice-box { 
          background: #fff; max-width: 800px; margin: 0 auto; padding: 40px; 
          border-radius: 12px; box-shadow: 0 10px 30px rgba(0,0,0,0.08); border-top: 6px solid #212529;
      }
      
      .hotel-brand { font-size: 28px; font-weight: 900; letter-spacing: 2px; text-transform: uppercase; color: #212529; margin-bottom: 0;}
      .invoice-title { font-size: 20px; font-weight: bold; color: #6c757d; letter-spacing: 5px; margin-top: 10px; margin-bottom: 30px; text-transform: uppercase;}
      
      .info-label { font-weight: bold; color: #495057; font-size: 14px;}
      .info-val { font-size: 15px; color: #212529; margin-bottom: 10px;}
      
      .table-invoice { margin-top: 30px; }
      .table-invoice th { background-color: #f8f9fa; color: #495057; font-weight: bold; border-bottom: 2px solid #dee2e6; text-transform: uppercase; font-size: 13px;}
      .table-invoice td { vertical-align: middle; border-bottom: 1px dashed #dee2e6;}
      
      .summary-row th { font-size: 15px; text-transform: uppercase; color: #6c757d; border-bottom: none;}
      .total-row th { font-size: 20px; color: #dc3545; font-weight: 900; border-top: 2px solid #212529;}
      
      @media print { 
          body { background-color: #fff; padding: 0; }
          .invoice-box { box-shadow: none; border: none; padding: 0; max-width: 100%;}
          .no-print { display:none !important; } 
      }
  </style>
</head>
<body>
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

<div class="container">
  <div class="d-flex justify-content-between align-items-center mb-4 no-print" style="max-width: 800px; margin: 0 auto;">
    <a class="btn btn-secondary fw-bold px-4" href="<%=request.getContextPath()%>/reception/home.jsp">⬅ Quay Lại</a>
    <button onclick="window.print()" class="btn btn-primary fw-bold px-4">🖨 In Hóa Đơn</button>
  </div>

  <div class="invoice-box">
  <% if (inv == null) { %>
    <div class="alert alert-danger text-center fw-bold py-4">Không tìm thấy Hóa đơn hợp lệ trong hệ thống!</div>
  <% } else { %>
  
    <div class="row border-bottom pb-4 mb-4">
        <div class="col-sm-6">
            <h1 class="hotel-brand">SMARTHOTEL</h1>
            <div class="text-muted small">01 Nguyễn Văn Linh, Đà Nẵng, VN<br>Tel: 0236 123 4567</div>
        </div>
        <div class="col-sm-6 text-sm-end text-start mt-3 mt-sm-0">
            <div class="invoice-title">INVOICE / BIÊN LAI</div>
            <div><span class="info-label">Số Hóa Đơn:</span> <span class="fw-bold text-danger">#INV-<%= inv.getInvoiceID() %></span></div>
            <div><span class="info-label">Ngày xuất:</span> <%= inv.getCreatedDate()!=null? new java.text.SimpleDateFormat("dd/MM/yyyy HH:mm").format(inv.getCreatedDate()) : "-" %></div>
        </div>
    </div>

    <div class="row px-3 py-3 rounded" style="background-color: #f8f9fa;">
      <div class="col-sm-6">
        <div class="info-label text-uppercase text-muted" style="font-size: 11px;">Thông tin khách hàng</div>
        <div class="info-val fw-bold fs-5 text-primary"><%= inv.getCustomer()!=null?inv.getCustomer().getFullName():"Khách lẻ" %></div>
      </div>
      <div class="col-sm-6 text-sm-end text-start mt-3 mt-sm-0">
        <div class="info-label text-uppercase text-muted" style="font-size: 11px;">Thông tin phòng</div>
        <div class="info-val fw-bold">Mã Booking: #BK-<%= inv.getBooking()!=null?inv.getBooking().getBookingID():"-" %></div>
      </div>
    </div>

    <table class="table table-invoice">
      <thead>
          <tr>
              <th width="5%">#</th>
              <th>Mô tả dịch vụ / Sản phẩm</th>
              <th class="text-center" width="10%">SL</th>
              <th class="text-end" width="20%">Đơn giá (VNĐ)</th>
              <th class="text-end" width="25%">Thành tiền (VNĐ)</th>
          </tr>
      </thead>
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
            <td class="text-muted"><%= idx++ %></td>
            <td class="fw-bold"><%= it.getInventory()!=null?it.getInventory().getItemName():"Dịch vụ phòng" %></td>
            <td class="text-center"><%= it.getQuantity()!=null?it.getQuantity():0 %></td>
            <td class="text-end"><%= String.format("%,.0f", it.getUnitPrice()!=null?it.getUnitPrice():0.0) %></td>
            <td class="text-end fw-bold"><%= String.format("%,.0f", line) %></td>
          </tr>
        <% } %>
      </tbody>
      <tfoot>
        <tr class="summary-row">
            <th colspan="4" class="text-end py-3">Tổng cộng:</th>
            <td class="text-end fw-bold py-3"><%= String.format("%,.0f", subtotal) %></td>
        </tr>
        <tr class="summary-row">
            <th colspan="4" class="text-end py-2 text-success">Chiết khấu / Giảm giá VIP:</th>
            <td class="text-end fw-bold py-2 text-success">- <%= String.format("%,.0f", inv.getDiscount()!=null?inv.getDiscount():0.0) %></td>
        </tr>
        <tr class="total-row">
            <th colspan="4" class="text-end py-4">TỔNG THANH TOÁN:</th>
            <td class="text-end py-4"><%= String.format("%,.0f", inv.getTotalAmount()!=null?inv.getTotalAmount():subtotal) %> đ</td>
        </tr>
      </tfoot>
    </table>
    
    <div class="text-center mt-5 text-muted" style="font-size: 13px; font-family: Arial, sans-serif;">
        <i>Cảm ơn quý khách đã sử dụng dịch vụ của SmartHotel. Hẹn gặp lại!</i>
    </div>
  <% } %>
  </div>
</div>
</body>
</html>