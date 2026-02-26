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
  <title>Check-in / Check-out</title>
  <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
  <style>
    .box { border:1px solid #e9ecef; padding:16px; border-radius:8px; background:white; }
  </style>
</head>
<body class="bg-light">
<%
  // Chỉ giữ lại logic load dữ liệu, loại bỏ logic Auth
  BookingDAO bdao = new BookingDAO();
  List<BookingShort> pending = bdao.findPendingBookings();
  List<BookingShort> checked = bdao.findCheckedInBookings();

  InventoryDAO invDao = new InventoryDAO();
  List<Inventory> invList = invDao.findAll();
%>

<div class="container py-4">
  <div class="d-flex justify-content-between align-items-center mb-3">
    <h3>Check-in / Check-out</h3>
    <a class="btn btn-outline-secondary" href="<%=request.getContextPath()%>/reception/home.jsp">Back</a>
  </div>

  <div class="row g-3">
    <div class="col-md-6">
      <div class="box">
        <h5>Check-in (Booking Pending)</h5>
        <select id="pendingSel" class="form-select mb-2" onchange="fillCheckin()">
          <option value="">-- Chọn booking --</option>
          <% for (BookingShort b : pending) {
              String v = b.bookingID + "|" + b.roomID + "|" + b.customerID;
          %>
            <option value="<%=v%>">Booking#<%=b.bookingID%> - Phòng <%=b.roomID%> - <%=b.customerName%></option>
          <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
          <input type="hidden" name="action" value="checkin">
          <div class="mb-2">
            <label>Booking ID</label>
            <input id="bid_checkin" name="bid" class="form-control" readonly required>
          </div>
          <div class="mb-2">
            <label>Room ID</label>
            <input id="rid_checkin" name="rid" class="form-control" readonly required>
          </div>
          <div class="d-grid">
            <button class="btn btn-success">Xác nhận Check-in</button>
          </div>
        </form>
      </div>
    </div>

    <div class="col-md-6">
      <div class="box">
        <h5>Check-out & Thanh toán (Booking Checked-in)</h5>
        <select id="checkedSel" class="form-select mb-2" onchange="fillCheckout()">
          <option value="">-- Chọn booking --</option>
          <% for (BookingShort b : checked) {
              String v = b.bookingID + "|" + b.roomID + "|" + b.customerID;
          %>
            <option value="<%=v%>">Booking#<%=b.bookingID%> - Phòng <%=b.roomID%> - <%=b.customerName%></option>
          <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post">
          <input type="hidden" name="action" value="checkout">
          <div class="mb-2">
            <label>Booking ID</label>
            <input id="bid_checkout" name="bid" class="form-control" readonly required>
          </div>
          <div class="mb-2">
            <label>Room ID</label>
            <input id="rid_checkout" name="rid" class="form-control" readonly required>
          </div>
          <div class="mb-2">
            <label>Customer ID</label>
            <input id="cid_checkout" name="cid" class="form-control" readonly required>
          </div>

          <div class="mb-2">
            <label>Giá phòng (VNĐ)</label>
            <input type="number" class="form-control" name="price" value="1000000" required>
            <div class="form-text">Hệ thống sẽ tự check VIP và giảm 20% nếu là VIP</div>
          </div>

          <hr>
          <h6>Mini-bar / Dịch vụ (chọn các món khách đã dùng)</h6>
          <div style="max-height:250px; overflow:auto;">
            <table class="table table-sm">
              <thead class="table-light"><tr><th>Chọn</th><th>Tên</th><th>Giá</th><th>Tồn</th><th>Số lượng</th></tr></thead>
              <tbody>
                <% for (Inventory it : invList) { %>
                <tr>
                  <td><input type="checkbox" name="itemId" value="<%= it.getItemID() %>"></td>
                  <td><%= it.getItemName() %></td>
                  <td><%= String.format("%,.0f", it.getSellingPrice()) %></td>
                  <td><%= it.getQuantity() %></td>
                  <td><input class="form-control form-control-sm" type="number" name="qty_<%=it.getItemID()%>" value="1" min="1" style="width:90px"></td>
                </tr>
                <% } %>
              </tbody>
            </table>
          </div>

          <div class="d-grid">
            <button class="btn btn-warning">THANH TOÁN (Checkout)</button>
          </div>
        </form>

      </div>
    </div>
  </div>

</div>

<script>
function fillCheckin(){
  var v = document.getElementById('pendingSel').value;
  if(!v) return;
  var p = v.split('|');
  document.getElementById('bid_checkin').value = p[0];
  document.getElementById('rid_checkin').value = p[1];
}
function fillCheckout(){
  var v = document.getElementById('checkedSel').value;
  if(!v) return;
  var p = v.split('|');
  document.getElementById('bid_checkout').value = p[0];
  document.getElementById('rid_checkout').value = p[1];
  document.getElementById('cid_checkout').value = p[2];
}
</script>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>