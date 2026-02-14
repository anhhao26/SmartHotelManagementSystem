<%@page import="dao.CustomerDAO"%>
<%@page import="model.BookingHistory"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    model.Account acc = (model.Account) session.getAttribute("acc");
    if (acc == null || !"GUEST".equalsIgnoreCase(acc.getRole())) {
        response.sendRedirect("../login.jsp");
        return;
    }
    Integer cidObj = (Integer) session.getAttribute("CUST_ID");
    if (cidObj == null) { response.sendRedirect("../login.jsp"); return; }
    int cid = cidObj;
    CustomerDAO dao = new CustomerDAO();
    List<BookingHistory> hist = dao.getBookingHistory(cid);
%>
<!DOCTYPE html>
<html>
<head><title>Lịch sử lưu trú</title></head>
<body>
    <h2>Lịch sử lưu trú</h2>
    <table border="1" cellpadding="6">
        <tr><th>BookingID</th><th>Room</th><th>CheckIn</th><th>CheckOut</th><th>Status</th><th>Invoice</th><th>Total</th><th>Discount</th></tr>
        <%
            for (BookingHistory h : hist) {
        %>
        <tr>
            <td><%=h.getBookingID()%></td>
            <td><%=h.getRoomNumber()%></td>
            <td><%= (h.getCheckInDate()!=null)? new java.text.SimpleDateFormat("yyyy-MM-dd").format(h.getCheckInDate()):"-" %></td>
            <td><%= (h.getCheckOutDate()!=null)? new java.text.SimpleDateFormat("yyyy-MM-dd").format(h.getCheckOutDate()):"-" %></td>
            <td><%=h.getStatus()%></td>
            <td><%= (h.getInvoiceID()!=null)? h.getInvoiceID() : "-" %></td>
            <td><%= (h.getTotalAmount()!=null)? String.format("%,.0f", h.getTotalAmount()) + " VNĐ" : "-" %></td>
            <td><%= (h.getDiscount()!=null)? String.format("%,.0f", h.getDiscount()) + " VNĐ" : "-" %></td>
        </tr>
        <% } %>
    </table>
    <p><a href="<%=request.getContextPath()%>/guest/profile.jsp">Quay lại hồ sơ</a></p>
</body>
</html>