<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head><title>Đăng ký</title></head>
<body>
    <h2>Đăng ký tài khoản</h2>
    <c:if test="${not empty err}">
      <div style="color:red">${err}</div>
    </c:if>

    <form action="${pageContext.request.contextPath}/register" method="post">
        Username: <input type="text" name="username" required><br>
        Password: <input type="password" name="password" required><br>
        Full name: <input type="text" name="fullName" required><br>
        CCCD/Passport: <input type="text" name="cccd"><br>
        Phone: <input type="text" name="phone"><br>
        Email: <input type="email" name="email"><br>
        Address: <input type="text" name="address"><br>
        Nationality: <input type="text" name="nationality"><br>
        Date of Birth: <input type="date" name="dob"><br>
        Gender:
        <select name="gender">
            <option value="">--</option>
            <option value="Male">Male</option>
            <option value="Female">Female</option>
        </select><br>
        Preferences: <textarea name="preferences"></textarea><br><br>
        <button type="submit">Đăng ký</button>
    </form>
</body>
</html>