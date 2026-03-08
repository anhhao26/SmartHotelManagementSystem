<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.dao.BookingDAO" %>
<%@ page import="com.smarthotel.dao.InventoryDAO" %>
<%@ page import="com.smarthotel.dao.BookingDAO.BookingShort" %>
<%@ page import="com.smarthotel.model.Inventory" %>
<%@ page import="java.util.List" %>
<!doctype html>
<html lang="vi">
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width,initial-scale=1">
  <title>Nghiệp vụ Check-in / Check-out</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
      body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; color: #333; padding-bottom: 40px;}
      .header-area { background: #198754; color: white; padding: 20px 0; margin-bottom: 30px; box-shadow: 0 4px 10px rgba(0,0,0,0.05); }
      
      .box { border: none; padding: 25px; border-radius: 12px; background: white; box-shadow: 0 5px 15px rgba(0,0,0,0.04); height: 100%; border-top: 4px solid; }
      .box-checkin { border-top-color: #0d6efd; }
      .box-checkout { border-top-color: #dc3545; }
      
      .form-label { font-weight: 600; color: #495057; font-size: 14px; margin-bottom: 5px;}
      .form-control, .form-select { border-radius: 8px; border: 1px solid #ced4da; background-color: #f8f9fa;}
      .form-control[readonly] { background-color: #e9ecef; color: #6c757d; font-weight: bold;}
      .form-control:focus, .form-select:focus { background-color: #fff; box-shadow: none; border-color: #198754; }
      
      .table-minibar th { background-color: #f8f9fa; color: #495057; font-size: 13px; text-transform: uppercase; border-bottom: 2px solid #dee2e6;}
      .table-minibar td { vertical-align: middle; border-bottom: 1px solid #f1f3f5;}
      
      .btn-action { font-weight: bold; padding: 12px; border-radius: 8px; transition: 0.3s; }
      .btn-action:hover { transform: translateY(-2px); }
  </style>
</head>
<body>
<%
  BookingDAO bdao = new BookingDAO();
  List<BookingShort> pending = bdao.findPendingBookings();
  List<BookingShort> checked = bdao.findCheckedInBookings();
  InventoryDAO invDao = new InventoryDAO();
  List<Inventory> invList = invDao.findAll();
%>

<div class="header-area">
    <div class="container d-flex justify-content-between align-items-center">
        <h3 class="m-0 fw-bold">🛎 Nghiệp vụ Nhận & Trả Phòng</h3>
        <a class="btn btn-light fw-bold text-success px-4" href="<%=request.getContextPath()%>/reception/home.jsp">⬅ Lễ tân</a>
    </div>
</div>

<div class="container">
  <div class="row g-4">
    <div class="col-lg-5">
      <div class="box box-checkin">
        <h4 class="text-primary fw-bold mb-4">Nhận Phòng (Check-in)</h4>
        
        <label class="form-label text-muted">Chọn phòng khách đã đặt trước (Pending)</label>
        <select id="pendingSel" class="form-select mb-4 border-primary" onchange="fillCheckin()">
          <option value="">-- Lựa chọn Booking --</option>
          <% for (BookingShort b : pending) { String v = b.bookingID + "|" + b.roomID + "|" + b.customerID; %>
            <option value="<%=v%>">Mã #<%=b.bookingID%> | P.<%=b.roomID%> | <%=b.customerName%></option>
          <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
          <input type="hidden" name="action" value="checkin">
          <div class="row g-3 mb-4">
              <div class="col-6">
                <label class="form-label">Mã Đơn (Booking ID)</label>
                <input id="bid_checkin" name="bid" class="form-control text-center text-primary" readonly required>
              </div>
              <div class="col-6">
                <label class="form-label">Phòng (Room ID)</label>
                <input id="rid_checkin" name="rid" class="form-control text-center text-primary" readonly required>
              </div>
          </div>
          <button class="btn btn-primary btn-action w-100 mt-2 shadow-sm">XÁC NHẬN CHO KHÁCH NHẬN PHÒNG</button>
        </form>
      </div>
    </div>

    <div class="col-lg-7">
      <div class="box box-checkout">
        <h4 class="text-danger fw-bold mb-4">Trả Phòng & Thanh Toán (Check-out)</h4>
        
        <label class="form-label text-muted">Chọn phòng khách đang ở</label>
        <select id="checkedSel" class="form-select mb-4 border-danger" onchange="fillCheckout()">
          <option value="">-- Lựa chọn Booking --</option>
          <% for (BookingShort b : checked) { String v = b.bookingID + "|" + b.roomID + "|" + b.customerID; %>
            <option value="<%=v%>">Mã #<%=b.bookingID%> | P.<%=b.roomID%> | <%=b.customerName%></option>
          <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
          <input type="hidden" name="action" value="checkout">
          
          <div class="row g-3 mb-3">
              <div class="col-4">
                <label class="form-label">Booking ID</label>
                <input id="bid_checkout" name="bid" class="form-control text-center text-danger" readonly required>
              </div>
              <div class="col-4">
                <label class="form-label">Room ID</label>
                <input id="rid_checkout" name="rid" class="form-control text-center text-danger" readonly required>
              </div>
              <div class="col-4">
                <label class="form-label">Customer ID</label>
                <input id="cid_checkout" name="cid" class="form-control text-center" readonly required>
              </div>
          </div>

          <div class="mb-4">
            <label class="form-label fw-bold text-dark">Giá phòng thanh toán (VNĐ)</label>
            <input type="number" class="form-control form-control-lg text-danger fw-bold" name="price" value="1000000" required>
            <div class="form-text text-success fw-bold">Hệ thống sẽ tự check VIP và giảm 20% nếu là VIP.</div>
          </div>

          <hr class="text-muted">
          <h6 class="fw-bold mb-3 text-secondary">🛒 Dịch vụ Minibar đã dùng</h6>
          <div style="max-height:220px; overflow:auto; border: 1px solid #dee2e6; border-radius: 8px;">
            <table class="table table-minibar table-hover mb-0">
              <thead class="sticky-top">
                  <tr><th class="text-center">Chọn</th><th>Tên SP</th><th>Giá bán</th><th>Kho</th><th width="100">SL dùng</th></tr>
              </thead>
              <tbody>
                <% for (Inventory it : invList) { %>
                <tr>
                  <td class="text-center"><input class="form-check-input" type="checkbox" name="itemId" value="<%= it.getItemID() %>"></td>
                  <td class="fw-bold"><%= it.getItemName() %></td>
                  <td class="text-danger"><%= String.format("%,.0f", it.getSellingPrice()) %> đ</td>
                  <td><span class="badge bg-secondary"><%= it.getQuantity() %></span></td>
                  <td><input class="form-control form-control-sm text-center" type="number" name="qty_<%=it.getItemID()%>" value="1" min="1"></td>
                </tr>
                <% } %>
              </tbody>
            </table>
          </div>

          <button class="btn btn-danger btn-action w-100 mt-4 shadow-sm" style="font-size: 1.1rem;">
              XUẤT HÓA ĐƠN & TRẢ PHÒNG
          </button>
        </form>

      </div>
    </div>
  </div>
</div>

<script>
function fillCheckin(){
  var v = document.getElementById('pendingSel').value;
  if(!v) { document.getElementById('bid_checkin').value = ""; document.getElementById('rid_checkin').value = ""; return; }
  var p = v.split('|');
  document.getElementById('bid_checkin').value = p[0];
  document.getElementById('rid_checkin').value = p[1];
}
function fillCheckout(){
  var v = document.getElementById('checkedSel').value;
  if(!v) { document.getElementById('bid_checkout').value = ""; document.getElementById('rid_checkout').value = ""; document.getElementById('cid_checkout').value = ""; return; }
  var p = v.split('|');
  document.getElementById('bid_checkout').value = p[0];
  document.getElementById('rid_checkout').value = p[1];
  document.getElementById('cid_checkout').value = p[2];
}
</script>
</body>
</html>