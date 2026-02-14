<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Guest Home</title>
</head>
<body>
    <h1>Trang khách hàng (Guest)</h1>

    <a href="<%=request.getContextPath()%>/guest/register.jsp">
        <button>Đăng ký tài khoản</button>
    </a>

    <a href="<%=request.getContextPath()%>/guest/login.jsp">
        <button>Đăng nhập</button>
    </a>
</body>
</html>