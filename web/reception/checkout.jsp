<%@page import="dao.BookingDAO"%>
<%@page import="dao.BookingDAO.BookingShort"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    // đảm bảo là receptionist
    model.Account acc = (model.Account) session.getAttribute("acc");
    if (acc == null || !"RECEPTIONIST".equalsIgnoreCase(acc.getRole())) {
        response.sendRedirect("../login.jsp");
        return;
    }

    BookingDAO bdao = new BookingDAO();
    List<BookingShort> pending = bdao.findPendingBookings();
    List<BookingShort> checked = bdao.findCheckedInBookings();
%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lễ tân - Check-in/out</title>
    <style>
        .box { border: 1px solid #ddd; padding: 20px; margin: 20px; width: 45%; float: left; }
        .btn-in { background: green; color: white; padding: 10px; border: none; cursor: pointer;}
        .btn-out { background: orange; color: white; padding: 10px; border: none; cursor: pointer;}
    </style>
    <script>
        // Khi chọn booking, điền tự động roomId + customerId
        function fillCheckin() {
            var sel = document.getElementById('pendingSel');
            var v = sel.value; // format bookingId|roomId|customerId
            if (!v) return;
            var parts = v.split('|');
            document.getElementById('bid_checkin').value = parts[0];
            document.getElementById('rid_checkin').value = parts[1];
            document.getElementById('cid_checkin').value = parts[2];
        }
        function fillCheckout() {
            var sel = document.getElementById('checkedSel');
            var v = sel.value;
            if (!v) return;
            var parts = v.split('|');
            document.getElementById('bid_checkout').value = parts[0];
            document.getElementById('rid_checkout').value = parts[1];
            document.getElementById('cid_checkout').value = parts[2];
        }
    </script>
</head>
<body>
    <h1>Giao diện Lễ tân (Module 7)</h1>

    <div class="box">
        <h3>1. Check-in Khách (Chọn booking Pending)</h3>
        <label>Chọn booking Pending: </label>
        <select id="pendingSel" onchange="fillCheckin()">
            <option value="">-- Chọn booking --</option>
            <%
                for (BookingShort b : pending) {
                    String v = b.bookingID + "|" + b.roomID + "|" + b.customerID;
            %>
            <option value="<%=v%>">Booking#<%=b.bookingID%> - Phòng <%=b.roomID%> - <%=b.customerName%></option>
            <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post" style="margin-top:10px;">
            <input type="hidden" name="action" value="checkin">
            Booking ID: <input type="number" id="bid_checkin" name="bid" required readonly><br><br>
            Room ID: <input type="number" id="rid_checkin" name="rid" required readonly><br><br>
            Customer ID: <input type="number" id="cid_checkin" name="cid" readonly><br><br>
            <button class="btn-in" type="submit">Xác nhận Check-in (-> Occupied)</button>
        </form>
    </div>

    <div class="box">
        <h3>2. Check-out & Thanh toán (Chọn booking Checked-in)</h3>
        <label>Chọn booking Checked-in: </label>
        <select id="checkedSel" onchange="fillCheckout()">
            <option value="">-- Chọn booking --</option>
            <%
                for (BookingShort b : checked) {
                    String v = b.bookingID + "|" + b.roomID + "|" + b.customerID;
            %>
            <option value="<%=v%>">Booking#<%=b.bookingID%> - Phòng <%=b.roomID%> - <%=b.customerName%></option>
            <% } %>
        </select>

        <form action="${pageContext.request.contextPath}/checkout" method="post" style="margin-top:10px;">
            <input type="hidden" name="action" value="checkout">
            Booking ID: <input type="number" id="bid_checkout" name="bid" readonly><br>
            Room ID: <input type="number" id="rid_checkout" name="rid" readonly><br>
            Customer ID: <input type="number" id="cid_checkout" name="cid" readonly><br>
            Giá phòng gốc: <input type="number" name="price" value="1000000"><br>
            <small>(Hệ thống sẽ tự check VIP để giảm 20% nếu có)</small>

            <h4>Mini-bar (Chọn các món khách dùng):</h4>
            <!-- Hiện danh sách items tĩnh hoặc dynamic (InventoryDAO) -->
            <table>
                <tr><th>Chọn</th><th>Tên</th><th>Giá</th><th>Tồn</th><th>Số lượng</th></tr>
                <tr>
                    <td><input type="checkbox" name="itemId" value="1" /></td>
                    <td>Bia Tiger</td>
                    <td>25000</td>
                    <td>100</td>
                    <td><input type="number" name="qty_1" value="1" min="1" /></td>
                </tr>
                <tr>
                    <td><input type="checkbox" name="itemId" value="2" /></td>
                    <td>Nước suối</td>
                    <td>10000</td>
                    <td>50</td>
                    <td><input type="number" name="qty_2" value="1" min="1" /></td>
                </tr>
            </table>

            <br>
            <button class="btn-out" type="submit">THANH TOÁN (-> Cleaning)</button>
        </form>
    </div>

</body>
</html>