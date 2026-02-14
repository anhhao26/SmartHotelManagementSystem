<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%
    model.Account acc = (model.Account) session.getAttribute("acc");
    if (acc == null || !acc.getRole().equals("MANAGER")) {
        response.sendRedirect("../login.jsp");
        return;
    }
%>
<!DOCTYPE html>
<html>
    <head>
        <title>Admin Dashboard</title>
    </head>
    <body>
        <h1>Trang quản trị (Module 1, 8, 9)</h1>
        <h3>Xin chào Manager: ${sessionScope.acc.username}</h3>
        <hr>
        <ul>
            <li><a href="#">Quản lý phòng (Module 1)</a></li>
            <li><a href="#">Quản lý kho (Module 2)</a></li>
            <li><a href="#">AI Forecast (Module 10)</a></li>
        </ul>
        <br>
        <a href="../logout" style="color:red">Đăng xuất</a>
    </body>
</html>