<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Login SmartHotel</title></head>
<body>
    <h2>Hệ thống Đăng nhập</h2>
    <h4 style="color:red">${err}</h4>

    <form action="${pageContext.request.contextPath}/login" method="post">
        Username: <input type="text" name="username" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        <button type="submit">Đăng nhập</button>
    </form>

    <p>Chưa có tài khoản? <a href="${pageContext.request.contextPath}/guest/register.jsp">Đăng ký</a></p>
</body>
</html>