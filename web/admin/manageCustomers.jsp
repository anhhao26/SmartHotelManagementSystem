<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="com.smarthotel.model.Customer" %>
<%@ page import="java.util.List" %>
<%
    List<Customer> customers = (List<Customer>) request.getAttribute("customers");
%>
<!doctype html>
<html>
<head><meta charset="utf-8"><title>Quản lý khách</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet"></head>
<body class="p-4">
<h3>Danh sách khách</h3>
<table class="table table-striped">
    <thead><tr><th>ID</th><th>Họ tên</th><th>Email</th><th>Phone</th><th>Member</th><th>Points</th><th>Tổng chi</th><th>Thao tác</th></tr></thead>
    <tbody>
    <% if (customers != null) {
        for (Customer c : customers) { %>
            <tr>
                <td><%= c.getCustomerID() %></td>
                <td><%= c.getFullName() %></td>
                <td><%= c.getEmail() %></td>
                <td><%= c.getPhone() %></td>
                <td><%= c.getMemberType() %></td>
                <td><%= c.getPoints() %></td>
                <td><%= String.format("%,.0f", c.getTotalSpending()) %></td>
                <td>
                    <form style="display:inline-block" action="${pageContext.request.contextPath}/admin/customers" method="post">
                        <input type="hidden" name="action" value="addPoints"/>
                        <input type="hidden" name="customerId" value="<%=c.getCustomerID()%>"/>
                        <input type="number" name="points" value="1" min="1" style="width:80px"/>
                        <button class="btn btn-sm btn-primary">Add points</button>
                    </form>
                </td>
            </tr>
    <%  }
    } %>
    </tbody>
</table>
<a class="btn btn-secondary" href="<%=request.getContextPath()%>/admin">Back</a>
</body>
</html>