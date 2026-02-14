<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <title>Reception Home</title>
</head>
<body>
    <h1>Receptionist Dashboard (Module 7)</h1>

    <a href="<%=request.getContextPath()%>/reception/checkout.jsp">
        <button>Check-in / Check-out</button>
    </a>

    <a href="<%=request.getContextPath()%>/logout">
        <button style="color:red">Logout</button>
    </a>
</body>
</html>