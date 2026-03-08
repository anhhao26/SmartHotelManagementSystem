<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.model.Customer" %>
<%@ page import="java.util.List" %>
<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>CRM Khách Hàng - SmartHotel</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
    <style>
        body { background-color: #f4f7f6; font-family: 'Segoe UI', Tahoma, sans-serif; padding-bottom: 50px; color: #333;}
        .navbar-custom { background-color: #0d6efd; box-shadow: 0 2px 10px rgba(0,0,0,0.1); }
        
        .crm-card { border: none; border-radius: 12px; box-shadow: 0 5px 20px rgba(0,0,0,0.04); background: #fff; overflow: hidden;}
        
        .table th { background-color: #f8f9fa; color: #495057; font-weight: 700; text-transform: uppercase; font-size: 13px; border-bottom: 2px solid #dee2e6; padding: 15px 10px;}
        .table td { vertical-align: middle; font-weight: 500; border-bottom: 1px solid #f1f3f5; padding: 15px 10px;}
        
        .badge-vip { background-color: #ffc107; color: #000; font-weight: bold; padding: 5px 10px; border-radius: 6px;}
        .badge-guest { background-color: #e9ecef; color: #6c757d; font-weight: bold; padding: 5px 10px; border-radius: 6px;}
        
        .input-point { width: 70px; border-radius: 6px; border: 1px solid #ced4da; text-align: center; font-weight: bold;}
        .btn-point { border-radius: 6px; font-weight: bold; font-size: 13px;}
    </style>
</head>
<body>

<nav class="navbar navbar-dark navbar-custom mb-4 px-4 py-3">
    <a class="navbar-brand fw-bold" href="<%=request.getContextPath()%>/admin">⬅ VỀ BẢNG ĐIỀU KHIỂN</a>
</nav>

<div class="container">
    <div class="d-flex justify-content-between align-items-center mb-4 border-bottom pb-3">
        <h2 class="fw-bold m-0" style="color: #2c3e50;">👥 HỆ THỐNG CRM KHÁCH HÀNG</h2>
        <div class="text-muted fw-bold">Tổng số: <%= customers != null ? customers.size() : 0 %> khách</div>
    </div>

    <div class="card crm-card">
        <div class="table-responsive">
            <table class="table table-hover mb-0 text-center">
                <thead>
                    <tr>
                        <th width="5%">ID</th>
                        <th class="text-start" width="20%">Họ Tên</th>
                        <th class="text-start" width="20%">Email / Phone</th>
                        <th width="10%">Hạng Thẻ</th>
                        <th width="10%">Điểm (Pts)</th>
                        <th width="15%">Tổng Chi Tiêu</th>
                        <th width="20%">Cộng Điểm Thưởng</th>
                    </tr>
                </thead>
                <tbody>
                <% if (customers != null && !customers.isEmpty()) {
                    for (Customer c : customers) { 
                        boolean isVip = c.getMemberType() != null && c.getMemberType().toUpperCase().contains("VIP");
                %>
                        <tr>
                            <td class="text-muted fw-bold">#<%= c.getCustomerID() %></td>
                            <td class="text-start text-primary fw-bold"><%= c.getFullName() %></td>
                            <td class="text-start text-muted" style="font-size: 14px;">
                                <%= c.getEmail() != null ? c.getEmail() : "-" %><br>
                                <span class="text-dark"><%= c.getPhone() != null ? c.getPhone() : "-" %></span>
                            </td>
                            <td>
                                <span class="<%= isVip ? "badge-vip" : "badge-guest" %>">
                                    <%= c.getMemberType() != null ? c.getMemberType() : "Khách lẻ" %>
                                </span>
                            </td>
                            <td class="fs-5 text-success fw-bold"><%= c.getPoints() %></td>
                            <td class="text-danger fw-bold"><%= String.format("%,.0f", c.getTotalSpending()) %> đ</td>
                            <td>
                                <form style="display: flex; gap: 5px; justify-content: center;" action="${pageContext.request.contextPath}/admin/customers" method="post">
                                    <input type="hidden" name="action" value="addPoints"/>
                                    <input type="hidden" name="customerId" value="<%=c.getCustomerID()%>"/>
                                    <input type="number" name="points" class="form-control form-control-sm input-point" value="1" min="1"/>
                                    <button class="btn btn-sm btn-primary btn-point">Cộng điểm</button>
                                </form>
                            </td>
                        </tr>
                <%  }
                } else { %>
                        <tr>
                            <td colspan="7" class="py-5 text-muted fw-bold bg-light">Chưa có dữ liệu khách hàng trong hệ thống.</td>
                        </tr>
                <% } %>
                </tbody>
            </table>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>