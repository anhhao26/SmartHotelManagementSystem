<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Guest Login</title>
</head>
<body>
    <h2>Đăng nhập</h2>

    <form action="<%=request.getContextPath()%>/login" method="post">
        Username: <input type="text" name="username" required><br><br>
        Password: <input type="password" name="password" required><br><br>
        <button type="submit">Login</button>
    </form>

</body>
</html>